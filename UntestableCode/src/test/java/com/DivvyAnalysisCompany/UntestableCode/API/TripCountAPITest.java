package com.DivvyAnalysisCompany.UntestableCode.API;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.DivvyAnalysisCompany.UntestableCode.DataProvider.StationDataProvider;
import com.DivvyAnalysisCompany.UntestableCode.Models.TripsByStationPairDateRequest;
import com.DivvyAnalysisCompany.UntestableCode.Models.TripsByStationPairDateResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

@RunWith(SpringRunner.class)
@WebMvcTest(TripCountAPI.class)
public class TripCountAPITest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StationDataProvider sdp;

    private ObjectMapper objectMapper = new ObjectMapper();

    private TripsByStationPairDateRequest makeRequest(String source, String dest, Date date) {
        return  TripsByStationPairDateRequest.builder()
                .sourceStation(source)
                .destinationStation(dest)
                .date(date)
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

    @Test
    public void tripCountAPIReturnsValid() throws Exception {

        String source = "aaaaa";
        String destination = "bbbbb";
        Date date = new Date();

        Integer numTrips = 1;

        TripsByStationPairDateRequest request = makeRequest(source, destination, date);
        TripsByStationPairDateResponse response = makeResponse(request, numTrips);

        String jsonRequest = objectMapper.writeValueAsString(request);
        String jsonResponse = objectMapper.writeValueAsString(response);

        when(sdp.isValidStation(anyString())).thenReturn(true);
        when(sdp.getTripsByStationPairAndDate(anyString(), anyString(), any())).thenReturn(numTrips);

        mockMvc.perform(get("/TripsByStationPairDate").contentType(MediaType.APPLICATION_JSON).content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(jsonResponse));

    }

    @Test
    public void tripCountAPI_InvalidStation_Returns400() throws Exception {
        String source = "";
        String destination = "aaaaa";
        Date date = new Date();
        Integer numTrips = 0;

        TripsByStationPairDateRequest request = makeRequest(source, destination, date);

        String jsonRequest = objectMapper.writeValueAsString(request);

        mockMvc.perform(get("/TripsByStationPairDate").contentType(MediaType.APPLICATION_JSON).content(jsonRequest))
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

    }

    @Test
    public void tripCountAPI_StationNotFound_Returns404() throws Exception {
        String source = "aaaaa";
        String destination = "bbbbb";
        Date date = new Date();

        when(sdp.isValidStation(source)).thenReturn(false);

        TripsByStationPairDateRequest request = makeRequest(source, destination, date);

        String jsonRequest = objectMapper.writeValueAsString(request);

        mockMvc.perform(get("/TripsByStationPairDate").contentType(MediaType.APPLICATION_JSON).content(jsonRequest))
                .andExpect(status().is4xxClientError());

    }

    @Test
    public void tripCountAPI_InvalidDateInFuture_Returns400() throws Exception {
        String source = "bbbbb";
        String destination = "aaaaa";
        LocalDateTime date = LocalDateTime.now().plusDays(1);

        TripsByStationPairDateRequest request = makeRequest(source, destination, java.sql.Timestamp.valueOf(date));

        String jsonRequest = objectMapper.writeValueAsString(request);

        mockMvc.perform(get("/TripsByStationPairDate").contentType(MediaType.APPLICATION_JSON).content(jsonRequest))
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

    }

    @Test
    public void tripCountAPI_InvalidDateInPast_Returns400() throws Exception {
        String source = "bbbbb";
        String destination = "aaaaa";
        Date date = new GregorianCalendar(2012, 1, 1).getTime();

        TripsByStationPairDateRequest request = makeRequest(source, destination, date);

        String jsonRequest = objectMapper.writeValueAsString(request);

        mockMvc.perform(get("/TripsByStationPairDate").contentType(MediaType.APPLICATION_JSON).content(jsonRequest))
                .andExpect(status().is4xxClientError());

    }
}
