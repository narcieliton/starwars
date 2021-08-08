package com.narcielitonlopes.starwars.domain.mapper;

import java.util.List;
import java.util.stream.Collectors;

public abstract class StarWarsPlanetModelMapper<T, E> {

    public List<T> swApiPlanetsMapperToDtos(List<E> listSwApiPlanet) {
        return listSwApiPlanet.stream().map(this::swApiPlanetToDto).collect(Collectors.toList());
    }

    public abstract T swApiPlanetToDto(E swApiPlanet);
}
