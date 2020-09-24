package com.DivvyAnalysisCompany.UntestableCode.API;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.DivvyAnalysisCompany.UntestableCode.Models.TripsByStationPairDateRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
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

    private ObjectMapper objectMapper = new ObjectMapper();

    private TripsByStationPairDateRequest makeRequest(String source, String dest, Date date) {
        return  TripsByStationPairDateRequest.builder()
                .sourceStation(source)
                .destinationStation(dest)
                .date(date)
                .build();
    }

    @Test
    public void tripCountAPIReturnsValid() throws Exception {

        String source = "aaaaa";
        String destination = "bbbbb";
        Date date = new Date();

        TripsByStationPairDateRequest request = makeRequest(source, destination, date);

        String jsonRequest = objectMapper.writeValueAsString(request);

        mockMvc.perform(get("/TripsByStationPairDate").contentType(MediaType.APPLICATION_JSON).content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

    }

    @Test
    public void tripCountAPI_InvalidStation_Returns400() throws Exception {
        String source = "";
        String destination = "aaaaa";
        Date date = new Date();

        TripsByStationPairDateRequest request = makeRequest(source, destination, date);

        String jsonRequest = objectMapper.writeValueAsString(request);

        mockMvc.perform(get("/TripsByStationPairDate").contentType(MediaType.APPLICATION_JSON).content(jsonRequest))
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

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
