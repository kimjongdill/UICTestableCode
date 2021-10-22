package com.DivvyAnalysisCompany.UntestableCode.API;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.DivvyAnalysisCompany.UntestableCode.DataProvider.StationDataProvider;
import com.DivvyAnalysisCompany.UntestableCode.Models.TripsByStationPairDateRequest;
import com.DivvyAnalysisCompany.UntestableCode.Models.TripsByStationPairDateResponse;
import com.DivvyAnalysisCompany.UntestableCode.API.TripCountAPI;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;


import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

/*
Divvy Analysis API Contract

Objective: Implement an API that takes a source and destination Divvy Stations
and a Date and returns the number of trips between those two stations on that date.

Endpoint: <base-url>/TripsByStationPairDate

Input Object
{
  “sourceStation”: ”5 length string”,
  “destinationStation”: “5 length string”,
  “Date”: “UTC DATE”
}

Output Object
{
  “sourceStation”: ”5 length string”,
  “destinationStation”: “5 length string”,
  “Date”: “UTC DATE”,
  “numberOfTrips: “uint”
}
 */

@RunWith(SpringRunner.class)
@WebMvcTest(TripCountAPI.class)
public class TripCountAPITest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StationDataProvider sdp;



    private ObjectMapper objectMapper = new ObjectMapper()
            .setDateFormat(new StdDateFormat().withColonInTimeZone(true));

    private TripsByStationPairDateRequest makeRequest(String source, String dest, LocalDate date) {
        return  TripsByStationPairDateRequest.builder()
                .sourceStation(source)
                .destinationStation(dest)
                .date(Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant()))
                .build();
    }

    private TripsByStationPairDateResponse makeResponse(TripsByStationPairDateRequest req, int numTrips) {
        return TripsByStationPairDateResponse.builder()
                .sourceStation(req.sourceStation)
                .destinationStation(req.destinationStation)
                .date(req.date)
                .numberOfTrips(numTrips)
                .build();
    }
// Format of the Source & Destination Station
    // When Format is Wrong -> 401

// User Enters a Station ID that isn't in the system
    // When a station doesn't exist -> return 404

// Format of the Date
    // When a date format is incorrect -> 406

// Date - Not a date within the Divvy Service Range
    // When a date is out of range -> 400

// If Database Connection Errors Out Somehow -> Return 500

// If everything goes right we return well formed object with Code 200


































//    @Test
//    public void tripCountAPI_BackendDown_Returns500() throws Exception {
//
//        String source = "aaaaa";
//        String destination = "bbbbb";
//        LocalDate date = LocalDate.now();
//
//        Integer numTrips = 1;
//
//        TripsByStationPairDateRequest request = makeRequest(source, destination, date);
//        TripsByStationPairDateResponse response = makeResponse(request, numTrips);
//
//        String jsonRequest = objectMapper.writeValueAsString(request);
//        String jsonResponse = objectMapper.writeValueAsString(response);
//
//        when(sdp.isValidStation(anyString())).thenThrow(new SQLException());
//        when(sdp.getTripsByStationPairAndDate(anyString(), anyString(), any())).thenReturn(numTrips);
//
//        mockMvc.perform(get("/TripsByStationPairDate").contentType(MediaType.APPLICATION_JSON).content(jsonRequest))
//                .andExpect(status().isInternalServerError())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
//
//    }
//
//    @Test
//    public void tripCountAPIReturnsValid() throws Exception {
//
//        String source = "aaaaa";
//        String destination = "bbbbb";
//        LocalDate date = LocalDate.now();
//
//        Integer numTrips = 1;
//
//        TripsByStationPairDateRequest request = makeRequest(source, destination, date);
//        TripsByStationPairDateResponse response = makeResponse(request, numTrips);
//
//        String jsonRequest = objectMapper.writeValueAsString(request);
//        String jsonResponse = objectMapper.writeValueAsString(response);
//
//        when(sdp.isValidStation(anyString())).thenReturn(true);
//        when(sdp.getTripsByStationPairAndDate(anyString(), anyString(), any())).thenReturn(numTrips);
//
//        mockMvc.perform(get("/TripsByStationPairDate").contentType(MediaType.APPLICATION_JSON).content(jsonRequest))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(content().json(jsonResponse));
//
//    }
//
//    @Test
//    public void tripCountAPI_InvalidStation_Returns400() throws Exception {
//        String source = "";
//        String destination = "aaaaa";
//        LocalDate date = LocalDate.now();
//        Integer numTrips = 0;
//
//        TripsByStationPairDateRequest request = makeRequest(source, destination, date);
//
//        String jsonRequest = objectMapper.writeValueAsString(request);
//
//        mockMvc.perform(get("/TripsByStationPairDate").contentType(MediaType.APPLICATION_JSON).content(jsonRequest))
//                .andExpect(status().isBadRequest())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
//    }
//
//    @Test
//    public void tripCountAPI_StationNotFound_Returns404() throws Exception {
//        String source = "aaaaa";
//        String destination = "bbbbb";
//        LocalDate date = LocalDate.now();
//
//        when(sdp.isValidStation(source)).thenReturn(false);
//
//        TripsByStationPairDateRequest request = makeRequest(source, destination, date);
//
//        String jsonRequest = objectMapper.writeValueAsString(request);
//
//        mockMvc.perform(get("/TripsByStationPairDate").contentType(MediaType.APPLICATION_JSON).content(jsonRequest))
//                .andExpect(status().isNotFound())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
//
//    }
//
//    @Test
//    public void tripCountAPI_InvalidDateInFuture_Returns400() throws Exception {
//        String source = "bbbbb";
//        String destination = "aaaaa";
//        LocalDate date = LocalDate.now().plusDays(1);
//
//        TripsByStationPairDateRequest request = makeRequest(source, destination, date);
//
//        String jsonRequest = objectMapper.writeValueAsString(request);
//
//        mockMvc.perform(get("/TripsByStationPairDate").contentType(MediaType.APPLICATION_JSON).content(jsonRequest))
//                .andExpect(status().isBadRequest())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
//
//    }
//
//    @Test
//    public void tripCountAPI_InvalidDateInPast_Returns400() throws Exception {
//        String source = "bbbbb";
//        String destination = "aaaaa";
//        LocalDate date = TripCountAPI.launchDate.minusDays(1);
//
//        TripsByStationPairDateRequest request = makeRequest(source, destination, date);
//
//        String jsonRequest = objectMapper.writeValueAsString(request);
//
//        mockMvc.perform(get("/TripsByStationPairDate").contentType(MediaType.APPLICATION_JSON).content(jsonRequest))
//                .andExpect(status().isBadRequest())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
//
//   }
//}
