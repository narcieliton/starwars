package com.narcielitonlopes.starwars.domain.dto;

import io.swagger.annotations.ApiModelProperty;
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
    @ApiModelProperty(value = "Este campo será preenchido atraves do consumo da API publica https://swapi.dev/ ao salvar um planeta")
    private int amountOfMovie;
}
