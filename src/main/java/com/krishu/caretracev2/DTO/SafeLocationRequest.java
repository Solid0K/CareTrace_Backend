package com.krishu.caretracev2.DTO;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SafeLocationRequest {
    private String name;
    private Double longitude;
    private Double latitude;
    private Double radius;
}
