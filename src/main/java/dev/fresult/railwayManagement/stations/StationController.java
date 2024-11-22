package dev.fresult.railwayManagement.stations;

import dev.fresult.railwayManagement.stations.dtos.StationCreationRequest;
import dev.fresult.railwayManagement.stations.dtos.StationResponse;
import dev.fresult.railwayManagement.stations.dtos.StationUpdateRequest;
import dev.fresult.railwayManagement.stations.services.StationService;
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
@RequestMapping("/api/stations")
public class StationController {
  private final Logger logger = LoggerFactory.getLogger(StationController.class);

  private final StationService stationService;

  public StationController(StationService stationService) {
    this.stationService = stationService;
  }

  @GetMapping
  public ResponseEntity<List<StationResponse>> getStations() {
    logger.debug("[getStations] Getting all {}s", Station.class.getSimpleName());
    return ResponseEntity.ok(stationService.getStations());
  }

  @GetMapping("/{id}")
  public ResponseEntity<StationResponse> getStationById(@Min(1) @PathVariable int id) {
    logger.debug("[getStationById] Getting {} by id [{}]", Station.class.getSimpleName(), id);
    return ResponseEntity.ok(stationService.getStationById(id));
  }

  @PostMapping
  public ResponseEntity<StationResponse> createStation(
      @Validated @RequestBody StationCreationRequest body) {
    logger.debug("[createStation] Creating new {}", Station.class.getSimpleName());
    var createdStation = stationService.createStation(body);
    var uri = URI.create(String.format("/api/stations/%d", createdStation.id()));

    return ResponseEntity.created(uri).body(createdStation);
  }

  @PatchMapping("/{id}")
  public ResponseEntity<StationResponse> updateById(
      @Min(1) @PathVariable int id, @Validated @RequestBody StationUpdateRequest body) {
    logger.debug("[updateById] Updating {} by id [{}]", Station.class.getSimpleName(), id);
    var updatedStation = stationService.updateStationById(id, body);

    return ResponseEntity.ok(updatedStation);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<String> deleteById(@Min(1) @PathVariable int id) {
    logger.debug("[deleteById] Deleting {} by id: {}", Station.class.getSimpleName(), id);
    stationService.deleteStationById(id);

    return ResponseEntity.ok(
        String.format("Delete %s by id [%d] successfully", Station.class.getSimpleName(), id));
  }
}
