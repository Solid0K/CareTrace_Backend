package com.krishu.caretracev2.DTO;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SafeLocationStatus {
    private String safeZoneId;
    private String name;
    private double distance;
    private boolean inside;
}
