package com.narcielitonlopes.starwars.mapper;

import com.narcielitonlopes.starwars.domain.document.PlanetDocument;
import com.narcielitonlopes.starwars.domain.dto.PlanetDto;
import com.narcielitonlopes.starwars.domain.mapper.PlanetDocumentGeneric;
import com.narcielitonlopes.starwars.domain.mapper.PlanetMapper;
import com.narcielitonlopes.starwars.planet.IPlanetService;
import com.narcielitonlopes.starwars.util.StarWarsApiException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.UUID;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PlanetDocumentoGenericTest {

    private static final PlanetMapper<PlanetDocument, PlanetDto> MAPPER = PlanetDocumentGeneric.create();

    @Autowired
    private IPlanetService service;

    @Test
    public void planetDocumentToPlanetDto() throws StarWarsApiException {
        PlanetDocument document = new PlanetDocument();
        document.setName("planet Name");
        document.setClimate("arid");
        document.setTerrain("desert");
        document.setId(UUID.randomUUID().toString());
        document.setAmountOfMovie(0);
        PlanetDto dto = MAPPER.planetDocumentToPlanetDto(document);

        assertEquals(document.getId(), dto.getId());
        assertEquals(document.getName(), dto.getName());
        assertEquals(document.getTerrain(), dto.getTerrain());
        assertEquals(document.getClimate(), dto.getClimate());
        assertEquals(document.getAmountOfMovie(), dto.getAmountOfMovie());
    }

    @Test
    public void planetDocumentToPlanetDtoEmptyValues() throws StarWarsApiException {
        PlanetDto dto = MAPPER.planetDocumentToPlanetDto(new PlanetDocument());
        assertNotNull(dto);
    }

    @Test
    public void planetDtoToPlanetDocument(){
        PlanetDto dto = PlanetDto.builder()
                .id(UUID.randomUUID().toString())
                .name("planet Name")
                .climate("arid")
                .terrain("desert")
                .amountOfMovie(0).build();

        PlanetDocument document = MAPPER.planetDtoToPlanetDocument(dto);
        assertEquals(document.getId(), dto.getId());
        assertEquals(document.getName(), dto.getName());
        assertEquals(document.getTerrain(), dto.getTerrain());
        assertEquals(document.getClimate(), dto.getClimate());
        assertEquals(document.getAmountOfMovie(), dto.getAmountOfMovie());
    }

    @Test
    public void planetDtoToPlanetDocumentEmptyValues(){
        PlanetDto dto = PlanetDto.builder()
                .id(null)
                .name(null)
                .climate(null)
                .terrain(null)
                .amountOfMovie(0).build();

        PlanetDocument document = MAPPER.planetDtoToPlanetDocument(dto);
        assertNotNull(document);
    }

    @Test
    public void planetDocumentsToDtos(){
        PlanetDocument document1 = new PlanetDocument();
        document1.setName("planet Name");
        document1.setClimate("arid");
        document1.setTerrain("desert");
        document1.setId(UUID.randomUUID().toString());
        document1.setAmountOfMovie(0);

        PlanetDocument document2 = new PlanetDocument();
        document2.setName("planet Name");
        document2.setClimate("arid");
        document2.setTerrain("desert");
        document2.setId(UUID.randomUUID().toString());
        document2.setAmountOfMovie(0);
        assertTrue(MAPPER.planetDocumentsToDtos(Arrays.asList(document1, document2)).size() > 0);
    }

    @Test
    public void planetDocumentsToDtosEmptValues(){
        assertTrue(MAPPER.planetDocumentsToDtos(
                Arrays.asList(new PlanetDocument(), new PlanetDocument())).size() > 0);
    }
}
