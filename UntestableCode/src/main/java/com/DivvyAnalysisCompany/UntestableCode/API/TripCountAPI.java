package com.DivvyAnalysisCompany.UntestableCode.API;

import com.DivvyAnalysisCompany.UntestableCode.DataProvider.StationDataProvider;
import com.DivvyAnalysisCompany.UntestableCode.Models.TripsByStationPairDateRequest;
import com.DivvyAnalysisCompany.UntestableCode.Models.TripsByStationPairDateResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.Date;

@RestController
public class TripCountAPI {

    @GetMapping(path="/TripsByStationPairDate", consumes="application/json")
    ResponseEntity<Object> GetTripsByStationPairDate(
            @Valid @RequestBody TripsByStationPairDateRequest request, Errors errors) {
        if (errors.hasErrors()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors.getAllErrors());
        }

        // Validate Station Identifiers
        StationDataProvider sdp = new StationDataProvider();

        if(!sdp.isValidStation(request.sourceStation)) {
            String responseMessage = String.format("Invalid station %s", request.sourceStation);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseMessage);
        }
        if(!sdp.isValidStation(request.destinationStation)) {
            String responseMessage = String.format("Invalid station %s", request.destinationStation);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseMessage);
        }

        // Get the Trip Count
        Integer tripCount = sdp.getTripsByStationPairAndDate(request.sourceStation,
                request.destinationStation,
                request.date);

        TripsByStationPairDateResponse response = TripsByStationPairDateResponse.builder()
                .sourceStation(request.sourceStation)
                .destinationStation(request.destinationStation)
                .numberOfTrips(tripCount)
                .date(request.date)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
