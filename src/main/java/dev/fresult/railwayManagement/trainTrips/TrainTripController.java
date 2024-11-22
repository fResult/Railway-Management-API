package dev.fresult.railwayManagement.trainTrips;

import dev.fresult.railwayManagement.trainTrips.dtos.TrainTripCreationRequest;
import dev.fresult.railwayManagement.trainTrips.dtos.TrainTripResponse;
import dev.fresult.railwayManagement.trainTrips.dtos.TrainTripUpdateRequest;
import dev.fresult.railwayManagement.trainTrips.services.TrainTripService;
import jakarta.validation.constraints.Min;
import java.net.URI;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/api/train-trips")
public class TrainTripController {

  private final Logger logger = LoggerFactory.getLogger(TrainTripController.class);

  private final TrainTripService trainTripService;

  public TrainTripController(TrainTripService trainTripService) {
    this.trainTripService = trainTripService;
  }

  @GetMapping
  public ResponseEntity<List<TrainTripResponse>> getTrainTrips(
      @Min(1) @RequestParam(name = "from", required = false) Integer originStationId,
      @Min(1) @RequestParam(name = "to", required = false) Integer destinationStationId) {
    logger.debug("[getTrainTrips] Getting all {}s", TrainTrip.class.getSimpleName());

    return ResponseEntity.ok(trainTripService.getTrainTrips(originStationId, destinationStationId));
  }

  @GetMapping("/{id}")
  public ResponseEntity<TrainTripResponse> getTrainTripById(@Min(1) @PathVariable int id) {
    logger.debug("[getTrainTripById] Getting {} by id [{}]", TrainTrip.class.getSimpleName(), id);

    return ResponseEntity.ok(trainTripService.getTrainTripById(id));
  }

  @PostMapping
  public ResponseEntity<TrainTripResponse> createTrainTrip(
      @Validated @RequestBody TrainTripCreationRequest body) {
    logger.debug("[createTrainTrip] Creating new {}", TrainTrip.class.getSimpleName());
    var createdTrainTrip = trainTripService.createTrainTrip(body);
    var uri = URI.create(String.format("/api/train-trips/%d", createdTrainTrip.id()));

    return ResponseEntity.created(uri).body(createdTrainTrip);
  }

  @PatchMapping("/{id}")
  public ResponseEntity<TrainTripResponse> updateTrainTripById(
      @Min(1) @PathVariable int id, @Validated @RequestBody TrainTripUpdateRequest body) {
    logger.debug(
        "[updateTrainTripById] Updating {} by id [{}]", TrainTrip.class.getSimpleName(), id);

    return ResponseEntity.ok(trainTripService.updateTrainTripById(id, body));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<String> deleteTrainTripById(@Min(1) @PathVariable int id) {
    logger.debug(
        "[deleteTrainTripById] Deleting {} by id [{}]", TrainTrip.class.getSimpleName(), id);
    trainTripService.deleteTrainTripById(id);

    return ResponseEntity.ok(
        String.format("Delete %s by id [%d] successfully", TrainTrip.class.getSimpleName(), id));
  }
}
