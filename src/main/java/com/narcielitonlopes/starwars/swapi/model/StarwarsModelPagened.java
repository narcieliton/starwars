package com.narcielitonlopes.starwars.swapi.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class StarwarsModelPagened {

    public long count;
    public String next;
    public String previous;
    public List results;

    public boolean hasMore() {
        return (next != null && !next.isEmpty());
    }

    public boolean hasPrevious(){
        return (previous != null && !previous.isEmpty());
    }

}
