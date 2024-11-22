package dev.fresult.railwayManagement.trainTrips;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/train-trips")
public class TrainTripController {
    @GetMapping
    public ResponseEntity<?> getTrainTrips() {
        return ResponseEntity.ok("Train Trips");
    }
}
