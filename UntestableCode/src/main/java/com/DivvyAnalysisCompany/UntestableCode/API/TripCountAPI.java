package com.DivvyAnalysisCompany.UntestableCode.API;

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
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        }
        //call db
        //Station Validation
        //Retrieve 
        //form response
        TripsByStationPairDateResponse response = TripsByStationPairDateResponse.builder()
                .sourceStation(request.sourceStation)
                .destinationStation(request.destinationStation)
                .numberOfTrips(999)
                .date(request.date)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
