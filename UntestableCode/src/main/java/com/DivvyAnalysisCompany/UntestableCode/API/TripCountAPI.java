package com.DivvyAnalysisCompany.UntestableCode.API;

import com.DivvyAnalysisCompany.UntestableCode.Models.TripsByStationPairDateRequest;
import com.DivvyAnalysisCompany.UntestableCode.Models.TripsByStationPairDateResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Date;

@RestController
public class TripCountAPI {

    @GetMapping(path="/TripsByStationPairDate", consumes="application/json")
    ResponseEntity<TripsByStationPairDateResponse> GetTripsByStationPairDate(@RequestBody TripsByStationPairDateRequest request) {
        TripsByStationPairDateResponse response = TripsByStationPairDateResponse.builder()
                .sourceStation(request.sourceStation)
                .destinationStation(request.destinationStation)
                .numberOfTrips(999)
                .date(request.date)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
