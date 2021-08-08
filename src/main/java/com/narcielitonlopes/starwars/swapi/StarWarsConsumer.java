package com.narcielitonlopes.starwars.swapi;

import com.google.gson.Gson;
import com.narcielitonlopes.starwars.swapi.model.StarWarsPlanetModel;
import com.narcielitonlopes.starwars.swapi.model.StarwarsModelPagened;
import com.narcielitonlopes.starwars.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static com.narcielitonlopes.starwars.ApiConstantes.*;

@Component
public class StarWarsConsumer {

    private final RestTemplate restTemplate;

    @Autowired
    public StarWarsConsumer(RestTemplateBuilder builder){
        this.restTemplate = builder.build();
    }

    public StarwarsModelPagened getListPlanets(Integer page) throws HttpClientErrorException {
        if(null != page){
            return getStarWarsModel(SWAPI_URL_BASE.concat(SWAPI_URI_PLANET).concat("/?page=").concat(page.toString()));
        }
        return getStarWarsModel(SWAPI_URL_BASE + SWAPI_URI_PLANET);
    }

    public int getAmountAppearedInMovies(String namePlanet) throws HttpClientErrorException {
        if(null == namePlanet){
            return 0;
        }

        StarwarsModelPagened responsePagened = getStarWarsModel(SWAPI_URL_BASE.concat(SWAPI_URI_PLANET).concat(SWAPI_URI_SEARCH).concat(namePlanet));
        List<StarWarsPlanetModel> planets = JsonUtil.deserializeJsonStarWarsPlanetModel(new Gson().toJson(responsePagened.getResults()));

        return planets.stream()
                .filter(planet -> planet.getName().equalsIgnoreCase(namePlanet))
                .map(planet -> planet.getFilms().size())
                .findFirst().orElse(0);
    }

    private StarwarsModelPagened getStarWarsModel(String url) throws HttpClientErrorException {
        return restTemplate.getForObject(url, StarwarsModelPagened.class);
    }
}
