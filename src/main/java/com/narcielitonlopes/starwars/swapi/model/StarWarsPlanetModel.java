package com.narcielitonlopes.starwars.swapi.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class StarWarsPlanetModel {

    private long id;
    private String name;
    private String climate;
    private String terrain;
    private int amountOfMovie;
    public List<String> films;

    public List<String> getFilms() {
        if (null == this.films && this.films.isEmpty()) {
            return new ArrayList<>();
        }
        return films;
    }
}