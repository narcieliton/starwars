package com.narcielitonlopes.starwars.domain.mapper;

import com.narcielitonlopes.starwars.domain.document.PlanetDocument;
import com.narcielitonlopes.starwars.domain.dto.PlanetDto;

public class PlanetDocumentGeneric extends PlanetMapper<PlanetDocument, PlanetDto> {

    public static PlanetDocumentGeneric create(){
        return new PlanetDocumentGeneric();
    }

    @Override
    public PlanetDocument planetDtoToPlanetDocument(PlanetDto swApiPlanet) {
        PlanetDocument document = new PlanetDocument();
        document.setId(swApiPlanet.getId());
        document.setClimate(swApiPlanet.getClimate());
        document.setName(swApiPlanet.getName());
        document.setTerrain(swApiPlanet.getTerrain());
        document.setAmountOfMovie(swApiPlanet.getAmountOfMovie());
        return document;
    }

    @Override
    public PlanetDto planetDocumentToPlanetDto(PlanetDocument document) {
        return PlanetDto.builder()
                .id(document.getId())
                .name(document.getName())
                .climate(document.getClimate())
                .terrain(document.getTerrain())
                .amountOfMovie(document.getAmountOfMovie()).build();
    }

}
