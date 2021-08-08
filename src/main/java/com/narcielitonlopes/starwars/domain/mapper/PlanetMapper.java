package com.narcielitonlopes.starwars.domain.mapper;

import java.util.List;
import java.util.stream.Collectors;

public abstract class PlanetMapper<T, E> {

    public List<E> planetDocumentsToDtos(List<T> planetDocuments){
        return planetDocuments.stream()
                .map(this::planetDocumentToPlanetDto).collect(Collectors.toList());
    }

    public abstract T planetDtoToPlanetDocument(E swApiPlanet);

    public abstract E planetDocumentToPlanetDto(T planetDocument);
}
