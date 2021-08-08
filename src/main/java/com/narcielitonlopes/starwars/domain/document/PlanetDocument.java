package com.narcielitonlopes.starwars.domain.document;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Document(value = "planets")
public class PlanetDocument {

    @Id
    private String id;
    @Indexed(unique = true, sparse=true)
    private String name;
    private String climate;
    private String terrain;
    private int amountOfMovie;
}

