package com.narcielitonlopes.starwars.planet;

import com.narcielitonlopes.starwars.domain.document.PlanetDocument;
import com.narcielitonlopes.starwars.swapi.model.StarwarsModelPagened;
import com.narcielitonlopes.starwars.util.StarWarsApiException;

public interface IPlanetService {

    StarwarsModelPagened getAll(String sortColumn, String sortDirection, String pageIndex, String pageSize);

    StarwarsModelPagened findAllSwApi(Integer page) throws StarWarsApiException;

    PlanetDocument findById(String id) throws StarWarsApiException;

    PlanetDocument findByName(String name) throws StarWarsApiException;

    PlanetDocument save(PlanetDocument document) throws StarWarsApiException;

    void delete(String id) throws StarWarsApiException;
}
