package com.DivvyAnalysisCompany.UntestableCode.API;

import com.DivvyAnalysisCompany.UntestableCode.DataProvider.StationDataProvider;
import com.DivvyAnalysisCompany.UntestableCode.Models.TripsByStationPairDateRequest;
import com.DivvyAnalysisCompany.UntestableCode.Models.TripsByStationPairDateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.Date;
import java.util.GregorianCalendar;

@RestController
public class TripCountAPI {

    @Autowired
    private StationDataProvider _stationDataProvider;

    @GetMapping(path="/TripsByStationPairDate", consumes="application/json")
    ResponseEntity<Object> GetTripsByStationPairDate(
            @Valid @RequestBody TripsByStationPairDateRequest request, Errors errors) {
        if (errors.hasErrors()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors.getAllErrors());
        }

        if (request.date.before(new GregorianCalendar(2013, 06, 28).getTime())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{ error: Invalid date before service start date");
        }


        if(!_stationDataProvider.isValidStation(request.sourceStation)) {
            String responseMessage = String.format("Invalid station %s", request.sourceStation);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseMessage);
        }
        if(!_stationDataProvider.isValidStation(request.destinationStation)) {
            String responseMessage = String.format("Invalid station %s", request.destinationStation);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseMessage);
        }

        // Get the Trip Count
        Integer tripCount = _stationDataProvider.getTripsByStationPairAndDate(request.sourceStation,
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
