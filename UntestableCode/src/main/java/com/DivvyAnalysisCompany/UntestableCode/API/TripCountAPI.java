package com.DivvyAnalysisCompany.UntestableCode.API;

import com.DivvyAnalysisCompany.UntestableCode.DataProvider.StationDataProvider;
import com.DivvyAnalysisCompany.UntestableCode.Models.TripsByStationPairDateRequest;
import com.DivvyAnalysisCompany.UntestableCode.Models.TripsByStationPairDateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.util.Date;
import java.util.GregorianCalendar;

@RestController
public class TripCountAPI {

    @Autowired
    private StationDataProvider _stationDataProvider;

    public static final LocalDate launchDate = LocalDate.of(2013, Month.JUNE, 28);

    @GetMapping(path="/TripsByStationPairDate", consumes="application/json")
    ResponseEntity<Object> GetTripsByStationPairDate(
            @Valid @RequestBody TripsByStationPairDateRequest request, Errors errors) {

//        if (errors.hasErrors()){
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON).body(errors.getAllErrors());
//        }
//
//        if (request.date.before(Date.from(launchDate.atStartOfDay(ZoneId.systemDefault()).toInstant()))){
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON).body("error: Invalid date before service start date");
//        }
//
//        Integer tripCount = 0;
//
//        try {
//            if(!_stationDataProvider.isValidStation(request.sourceStation)) {
//                String responseMessage = String.format("Invalid station %s", request.sourceStation);
//                return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body(responseMessage);
//            }
//            if(!_stationDataProvider.isValidStation(request.destinationStation)) {
//                String responseMessage = String.format("Invalid station %s", request.destinationStation);
//                return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body(responseMessage);
//            }
//
//            // Get the Trip Count
//            tripCount = _stationDataProvider.getTripsByStationPairAndDate(request.sourceStation,
//                    request.destinationStation,
//                    request.date);
//        } catch (SQLException e) {
//            return ResponseEntity
//                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .body("error: the database is down");
//        }
//
//
//        TripsByStationPairDateResponse response = TripsByStationPairDateResponse.builder()
//                .sourceStation(request.sourceStation)
//                .destinationStation(request.destinationStation)
//                .numberOfTrips(tripCount)
//                .date(request.date)
//                .build();
//
//        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(response);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("not implemented");
    }
}
