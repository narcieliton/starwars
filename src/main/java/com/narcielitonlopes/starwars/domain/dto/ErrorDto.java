package com.narcielitonlopes.starwars.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorDto {
    private String message;
    private String detail;
    private String code;
}
