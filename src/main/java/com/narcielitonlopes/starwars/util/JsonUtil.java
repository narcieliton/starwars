package com.narcielitonlopes.starwars.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.narcielitonlopes.starwars.swapi.model.StarWarsPlanetModel;

import java.lang.reflect.Type;
import java.util.List;

public class JsonUtil {

    public static List<StarWarsPlanetModel> deserializeJsonStarWarsPlanetModel(String json){
        Type listType = new TypeToken<List<StarWarsPlanetModel>>(){}.getType();
        return new Gson().fromJson(json, listType);
    }
}
