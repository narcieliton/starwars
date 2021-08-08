package com.narcielitonlopes.starwars.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static com.mongodb.internal.connection.tlschannel.util.Util.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JsonUtilTest {

    @Test
    public void deserializeJsonStarWarsPlanetModel(){
        StringBuilder sb = new StringBuilder();
        sb.append("[{")
                .append("\"name\": \"Tatooine\",")
                .append("\"climate\": \"arid\", ")
                .append("\"terrain\": \"desert\",")
                .append("\"films\": [ \"https://swapi.dev/api/films/1/ \" ]")
                .append("}]");

       assertTrue(JsonUtil.deserializeJsonStarWarsPlanetModel(sb.toString()).size() > 0 );

    }


}
