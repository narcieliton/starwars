package com.narcielitonlopes.starwars.repository;

import com.narcielitonlopes.starwars.domain.document.PlanetDocument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PlanetRepository extends MongoRepository<PlanetDocument, String> {

    PlanetDocument findByNameIgnoreCase(String name);

    Page<PlanetDocument> findAll(Pageable pageable);
}
