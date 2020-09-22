package com.DivvyAnalysisCompany.UntestableCode.Models;

import lombok.Data;
import java.util.Date;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.validation.constraints.Past;

@Data
public class TripsByStationPairDateRequest {
    @NotNull
    @Pattern(regexp="(\\d|[a-z]|[A-Z]){5}", message="Station is a 5-length alphanumeric code")
    public String sourceStation;
    @NotNull
    @Pattern(regexp="(\\d|[a-z]|[A-Z]){5}", message="Station is a 5-length alphanumeric code")
    public String destinationStation;
    @NotNull
    @Past
    public Date date;
}
