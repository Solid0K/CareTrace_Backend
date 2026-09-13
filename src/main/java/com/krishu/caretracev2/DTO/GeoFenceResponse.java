package com.krishu.caretracev2.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class GeoFenceResponse {
    private String patientId;
    private Boolean insideAnySafeLocation;
    private List<SafeLocationStatus> statues;
}
