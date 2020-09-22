package com.DivvyAnalysisCompany.UntestableCode.Models;

import lombok.Data;

import java.util.Date;

@Data
public class TripsByStationPairDateRequest {
    public String sourceStation;
    public String destinationStation;
    public Date date;
}
