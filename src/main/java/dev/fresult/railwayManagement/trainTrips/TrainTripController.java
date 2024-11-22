package dev.fresult.railwayManagement.trainTrips;

import dev.fresult.railwayManagement.trainTrips.services.TrainTripService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/train-trips")
public class TrainTripController {

  private final Logger logger = LoggerFactory.getLogger(TrainTripController.class);

  private final TrainTripService trainTripService;

  public TrainTripController(TrainTripService trainTripService) {
    this.trainTripService = trainTripService;
  }

  @GetMapping
  public ResponseEntity<List<TrainTrip>> getTrainTrips(
      @RequestParam(name = "from", required = false) Integer originStationId,
      @RequestParam(name = "to", required = false) Integer destinationStationId) {
    logger.debug("[getTrainTrips] Getting all train trips");

    return ResponseEntity.ok(trainTripService.getTrainTrips(originStationId, destinationStationId));
  }
  @DeleteMapping("/{id}")
  public ResponseEntity<String> deleteTrainTripById(@PathVariable int id) {
    logger.debug("[deleteTrainTripById] Deleting train trip by id [{}]", id);
    trainTripService.deleteTrainTripById(id);

    return ResponseEntity.ok("Train trip deleted successfully");
  }
}
