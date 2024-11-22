package dev.fresult.railwayManagement.tickets.services;

import dev.fresult.railwayManagement.common.helpers.ErrorHelper;
import dev.fresult.railwayManagement.tickets.Ticket;
import dev.fresult.railwayManagement.tickets.TicketRepository;
import dev.fresult.railwayManagement.tickets.dtos.TicketCreationRequest;
import dev.fresult.railwayManagement.tickets.dtos.TicketResponse;
import dev.fresult.railwayManagement.tickets.dtos.TicketUpdateRequest;
import dev.fresult.railwayManagement.trainTrips.dtos.TrainTripResponse;
import dev.fresult.railwayManagement.trainTrips.services.TrainTripService;
import dev.fresult.railwayManagement.users.dtos.UserInfoResponse;
import dev.fresult.railwayManagement.users.services.UserService;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class TicketServiceImpl implements TicketService {
  private final Logger logger = LoggerFactory.getLogger(TicketServiceImpl.class);
  private final ErrorHelper errorHelper = new ErrorHelper(TicketServiceImpl.class);

  private final TicketRepository ticketRepository;
  private final UserService userService;
  private final TrainTripService trainTripService;

  public TicketServiceImpl(
      TicketRepository ticketRepository,
      UserService userService,
      TrainTripService trainTripService) {
    this.ticketRepository = ticketRepository;
    this.userService = userService;
    this.trainTripService = trainTripService;
  }

  @Override
  public List<TicketResponse> getTickets(Integer passengerId) {
    logger.debug("[getTickets] Getting all {}s", Ticket.class.getSimpleName());
    var tickets = ticketRepository.findAllWithFilters(passengerId);
    // TODO: Make them query concurrently
    var passengerIdToPassengerMap = buildPassengerIdToPassengerMap(tickets);
    var trainTripIdToTrainTripMap = buildTrainTripIdToTrainTripMap(tickets);
    var toResponse =
        TicketResponse.fromTicketDaoWithTrainTripAndPassengerInfoMaps(
            trainTripIdToTrainTripMap, passengerIdToPassengerMap);

    return tickets.stream().map(toResponse).toList();
  }

  @Override
  public TicketResponse getTicketById(int ticketId) {
    logger.debug("[getTicketById] Getting {} id [{}]", Ticket.class.getSimpleName(), ticketId);

    return ticketRepository
        .findById(ticketId)
        .map(this::toResponseWithTrainTripAndPassengerInfo)
        .orElseThrow(errorHelper.entityNotFound("getTicketById", Ticket.class, ticketId));
  }

  @Override
  public TicketResponse createTicket(TicketCreationRequest body) {
    logger.debug("[createTicket] Creating new {}", Ticket.class.getSimpleName());

    var ticketToCreate = TicketCreationRequest.dtoToTicketCreate(body);
    var createdTicket = ticketRepository.save(ticketToCreate);
    logger.info(
        "[createTicket] New {} is created: {}", Ticket.class.getSimpleName(), createdTicket);

    return toResponseWithTrainTripAndPassengerInfo(createdTicket);
  }

  @Override
  public TicketResponse updateTicketById(int id, TicketUpdateRequest body) {
    logger.debug("[updateTicketById] Updating {} id [{}]", Ticket.class.getSimpleName(), id);

    var toTicketUpdate = TicketUpdateRequest.dtoToTicketUpdate(body);
    var ticketToUpdate =
        ticketRepository
            .findById(id)
            .orElseThrow(errorHelper.entityNotFound("updateTicketById", Ticket.class, id));

    var updatedTicket = ticketRepository.save(toTicketUpdate.apply(ticketToUpdate));
    logger.info(
        "[updateTicketById] {} is updated: {}", Ticket.class.getSimpleName(), updatedTicket);

    return toResponseWithTrainTripAndPassengerInfo(updatedTicket);
  }

  @Override
  public boolean deleteTicketById(int id) {
    logger.debug("[deleteTicketById] Deleting {} id [{}]", Ticket.class.getSimpleName(), id);

    var ticketToDelete =
        ticketRepository
            .findById(id)
            .orElseThrow(errorHelper.entityNotFound("deleteTicketById", Ticket.class, id));

    ticketRepository.deleteById(ticketToDelete.id());
    logger.info("[deleteTicketById] {} id [{}] is deleted", Ticket.class.getSimpleName(), id);

    return true;
  }

  private Set<Integer> buildPassengerIds(List<Ticket> tickets) {
    return tickets.stream().map(ticket -> ticket.passengerId().getId()).collect(Collectors.toSet());
  }

  private Set<Integer> buildTrainTripIds(List<Ticket> tickets) {
    return tickets.stream().map(ticket -> ticket.trainTripId().getId()).collect(Collectors.toSet());
  }

  private Map<Integer, UserInfoResponse> buildPassengerIdToPassengerMap(List<Ticket> tickets) {
    var passengerIds = buildPassengerIds(tickets);
    /* NOTE: Use `getUsersByIds` and hash table to prevent 1+N issue. */
    var ticketPassengers = userService.getUsersByIds(passengerIds);

    return ticketPassengers.stream()
        .collect(Collectors.toMap(UserInfoResponse::id, Function.identity()));
  }

  private Map<Integer, TrainTripResponse> buildTrainTripIdToTrainTripMap(List<Ticket> tickets) {
    var trainTripIds = buildTrainTripIds(tickets);

    /* NOTE: Use `getTrainTripsByIds` and hash table to prevent 1+N issue. */
    var ticketTrainTrips = trainTripService.getTrainTripsByIds(trainTripIds);

    return ticketTrainTrips.stream()
        .collect(Collectors.toMap(TrainTripResponse::id, Function.identity()));
  }

  private TicketResponse toResponseWithTrainTripAndPassengerInfo(Ticket ticket) {
    var trainTripId =
        Optional.ofNullable(ticket.trainTripId().getId())
            .orElseThrow(
                // TODO: make it available for dynamic method name
                errorHelper.runtimeError("getTicketById", "trainTripId"));
    var passengerId =
        Optional.ofNullable(ticket.passengerId().getId())
            // TODO: make it available for dynamic method name
            .orElseThrow(errorHelper.runtimeError("getTicketById", "passengerId"));
    var trip = trainTripService.getTrainTripById(trainTripId);
    var passenger = userService.getUserById(passengerId);

    return TicketResponse.fromTicketDao(ticket, trip, passenger);
  }
}
