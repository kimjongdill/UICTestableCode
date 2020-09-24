package com.DivvyAnalysisCompany.UntestableCode.Models;

import lombok.Builder;
import lombok.Data;
import java.util.Date;
import javax.validation.constraints.*;

@Data
@Builder
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
