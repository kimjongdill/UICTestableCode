package com.DivvyAnalysisCompany.UntestableCode.Models;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class TripsByStationPairDateResponse {
    public String sourceStation;
    public String destinationStation;
    public Date date;
    public Integer numberOfTrips;
}
