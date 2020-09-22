package com.DivvyAnalysisCompany.UntestableCode.DataProvider;

import java.security.InvalidParameterException;
import java.util.Date;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class StationDataProvider {
    private final Set<String> _stations = new HashSet<String>() {{
        add("AAAAA");
        add("BBBBB");
        add("CCCCC");
        add("DDDDD");
    }};

    private final Random _generator = new Random();

    public Boolean isValidStation(String station) {
        return _stations.contains(station.toUpperCase());
    }

    public Integer getTripsByStationPairAndDate(String sourceStation,
                                                String destinationStation,
                                                Date date) {
        if(!(isValidStation(sourceStation) && isValidStation(destinationStation))){
            throw new InvalidParameterException();
        }

        // Return a number of trips
        return _generator.nextInt(1000);
    }
}
