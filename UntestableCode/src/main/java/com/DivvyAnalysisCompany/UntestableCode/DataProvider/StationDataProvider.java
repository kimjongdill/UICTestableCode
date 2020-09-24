package com.DivvyAnalysisCompany.UntestableCode.DataProvider;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.security.InvalidParameterException;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

@Component("StationDataProvider")
public class StationDataProvider {
    private final Set<String> _stations = new HashSet<String>() {{
        add("AAAAA");
        add("BBBBB");
        add("CCCCC");
        add("DDDDD");
    }};

    private final Random _generator = new Random();

    public Boolean isValidStation(String station) throws SQLException {
        return _stations.contains(station.toUpperCase());
    }

    public Integer getTripsByStationPairAndDate(String sourceStation,
                                                String destinationStation,
                                                Date date) throws SQLException {
        if(!(isValidStation(sourceStation) && isValidStation(destinationStation))){
            throw new InvalidParameterException();
        }

        // Return a number of trips
        return _generator.nextInt(1000);
    }
}
