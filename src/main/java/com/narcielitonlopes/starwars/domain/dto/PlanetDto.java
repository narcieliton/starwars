package com.narcielitonlopes.starwars.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class PlanetDto {

    private String id;
    private String name;
    private String climate;
    private String terrain;
    private int amountOfMovie;
}
