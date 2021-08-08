package com.narcielitonlopes.starwars.controller;

import com.google.gson.Gson;
import com.narcielitonlopes.starwars.domain.document.PlanetDocument;
import com.narcielitonlopes.starwars.domain.dto.PlanetDto;
import com.narcielitonlopes.starwars.domain.mapper.PlanetDocumentGeneric;
import com.narcielitonlopes.starwars.domain.mapper.PlanetMapper;
import com.narcielitonlopes.starwars.domain.mapper.SwApiPlanetMapperToApi;
import com.narcielitonlopes.starwars.planet.IPlanetService;
import com.narcielitonlopes.starwars.swapi.model.StarWarsPlanetModel;
import com.narcielitonlopes.starwars.swapi.model.StarwarsModelPagened;
import com.narcielitonlopes.starwars.util.JsonUtil;
import com.narcielitonlopes.starwars.util.StarWarsApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RequestMapping(value = "/v1/planets")
@RestController
public class PlanetController extends ExceptionController {

    private final IPlanetService service;
    private static final SwApiPlanetMapperToApi STAR_WARS_MAPPER = SwApiPlanetMapperToApi.create();
    private static final PlanetMapper<PlanetDocument, PlanetDto> PLANET_MAPPER = PlanetDocumentGeneric.create();

    @Autowired
    public PlanetController(IPlanetService service){
        this.service = service;
    }

    @GetMapping
    public ResponseEntity planetDataBaseGet(@RequestParam(value = "sortColumn", required = false) String sortColumn,
    @RequestParam(value = "sortDirection", required = false) String sortDirection,
    @RequestParam(value = "page", required = false) String page,
    @RequestParam(value = "pageSize", required = false) String pageSize) {
        StarwarsModelPagened pagened = service.getAll(sortColumn, sortDirection, page, pageSize);
        List planets = PLANET_MAPPER.planetDocumentsToDtos(pagened.getResults());
        pagened.setResults(planets);

        if (pagened.hasMore()) {
            pagened.setNext(createUriWithQuery(pagened.getNext()));
        }
        if (pagened.hasPrevious()) {
            pagened.setPrevious(createUriWithQuery(pagened.getPrevious()));
        }
        return ResponseEntity.ok(pagened);
    }

    @GetMapping(value = "/starwarsapi")
    public ResponseEntity planetApiGet(@RequestParam(required = false) Integer page){
        try {
            StarwarsModelPagened pagened = service.findAllSwApi(page);
            List<StarWarsPlanetModel> planets = JsonUtil.deserializeJsonStarWarsPlanetModel(new Gson().toJson(pagened.getResults()));
            pagened.setResults(STAR_WARS_MAPPER.swApiPlanetsMapperToDtos(planets));

            if (pagened.hasMore()) {
                pagened.setNext(createUriWithQuery(pagened.getNext().split("=")[1]));
            }
            if (pagened.hasPrevious()) {
                pagened.setPrevious(createUriWithQuery(pagened.getPrevious().split("=")[1]));
            }
            return ResponseEntity.ok(pagened);
        }catch (HttpClientErrorException | StarWarsApiException e){
            return errorFlow(e);
        }
    }

    @GetMapping(value ="/{id}")
    public ResponseEntity planetIdGet(@PathVariable String id) {
        try {
            PlanetDocument document = service.findById(id);
            if (null == document) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            PlanetDto dto = PLANET_MAPPER.planetDocumentToPlanetDto(document);
            return ResponseEntity.ok().body(dto);
        }catch (Exception e){
            return errorFlow(e);
        }
    }

    @GetMapping(value = "/name/{name}")
    public ResponseEntity planetNameGet(@PathVariable String name) {
        try {
            PlanetDocument document = service.findByName(name);
            if (null == document) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            PlanetDto dto = PLANET_MAPPER.planetDocumentToPlanetDto(document);
            return ResponseEntity.ok().body(dto);
        }catch (Exception e){
            return errorFlow(e);
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Error> planetDelete(@PathVariable String id){
        try {
            service.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (StarWarsApiException e){
            return errorFlow(e);
        }
    }

    @PostMapping
    public ResponseEntity planetPost(@RequestBody PlanetDto planetDto) {
        try {
            PlanetDocument document = PLANET_MAPPER.planetDtoToPlanetDocument(planetDto);
            return ResponseEntity.ok().body(service.save(document));
        } catch (Exception e) {
            return errorFlow(e);
        }
    }

    private String createUriWithQuery(String page) {
        return ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .query("page={page}")
                .buildAndExpand(page)
                .toUriString();
    }

}
