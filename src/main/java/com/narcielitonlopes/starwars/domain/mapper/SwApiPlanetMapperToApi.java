package com.narcielitonlopes.starwars.domain.mapper;

import com.narcielitonlopes.starwars.domain.dto.PlanetDto;
import com.narcielitonlopes.starwars.swapi.model.StarWarsPlanetModel;

public class SwApiPlanetMapperToApi extends StarWarsPlanetModelMapper<PlanetDto, StarWarsPlanetModel> {

    public static SwApiPlanetMapperToApi create(){
        return new SwApiPlanetMapperToApi();
    }

    @Override
    public PlanetDto swApiPlanetToDto(StarWarsPlanetModel swApiPlanet) {
        return PlanetDto.builder()
                .name(swApiPlanet.getName())
                .climate(swApiPlanet.getClimate())
                .terrain(swApiPlanet.getTerrain())
                .amountOfMovie(swApiPlanet.getFilms() == null ? 0 : swApiPlanet.getFilms().size())
                .build();
    }

}
