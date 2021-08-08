package com.narcielitonlopes.starwars.planet;

import com.narcielitonlopes.starwars.domain.document.PlanetDocument;
import com.narcielitonlopes.starwars.repository.PlanetRepository;
import com.narcielitonlopes.starwars.swapi.model.StarwarsModelPagened;
import com.narcielitonlopes.starwars.util.StarWarsApiException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest
public class PlanetTest {

    @Autowired
    private IPlanetService service;

    @Autowired
    private PlanetRepository repository;

    @Test
    public void save() throws StarWarsApiException {
      PlanetDocument document = new PlanetDocument();
      document.setName("planet Name");
      document.setClimate("arid");
      document.setTerrain("desert");
      PlanetDocument documentSaved = service.save(document);
      assertNotNull(documentSaved.getId());
      assertEquals(documentSaved.getName(), document.getName());
      assertEquals(documentSaved.getTerrain(), document.getTerrain());
      assertEquals(documentSaved.getAmountOfMovie(), document.getAmountOfMovie());
    }

    @Test(expected = StarWarsApiException.class)
    public void saveNull() throws StarWarsApiException {
        service.save(new PlanetDocument());
    }

    @Test(expected = StarWarsApiException.class)
    public void saveDuplicateName() throws StarWarsApiException {
        PlanetDocument document = new PlanetDocument();
        String name = "planet Name";
        document.setName(name);
        service.save(document);
        PlanetDocument documentInvalidName = new PlanetDocument();
        documentInvalidName.setName(name);
        service.save(documentInvalidName);
    }

    @Test
    public void saveWithAccountMovieStarWarsApi() throws StarWarsApiException {
        PlanetDocument document = new PlanetDocument();
        document.setName("Tatooine");
        service.save(document);
        assertTrue(document.getAmountOfMovie() > 0);
    }

    @Test
    public void delete() throws StarWarsApiException {
        PlanetDocument document = new PlanetDocument();
        document.setName("planet Name");
        document.setClimate("arid");
        document.setTerrain("desert");
        service.save(document);
        service.delete(document.getId());
        assertNull(service.findById(document.getId()));
    }

    @Test(expected = StarWarsApiException.class)
    public void deletePlanetInexistent() throws StarWarsApiException {
        service.delete(UUID.randomUUID().toString());
    }

    @Test
    public void findByName() throws StarWarsApiException {
        PlanetDocument document = new PlanetDocument();
        document.setName("teste find by name");
        service.save(document);
        PlanetDocument documentSaved = service.findByName(document.getName());
        assertNotNull(documentSaved);
    }

    @Test
    public void findByNameInexistent() throws StarWarsApiException {
        assertNull(service.findByName("aa"));
    }

    @Test(expected = StarWarsApiException.class)
    public void findByNameNull() throws StarWarsApiException {
        assertNull(service.findByName(null));
    }

    @Test
    public void findById() throws StarWarsApiException {
        PlanetDocument document = new PlanetDocument();
        document.setName("teste by id");
        service.save(document);
        PlanetDocument documentSaved = service.findById(document.getId());
        assertNotNull(documentSaved);
    }

    @Test
    public void findByIDInexistent() throws StarWarsApiException {
        assertNull(service.findById(UUID.randomUUID().toString()));
    }

    @Test(expected = StarWarsApiException.class)
    public void findByIdNull() throws StarWarsApiException {
        assertNull(service.findById(null));
    }

    @Test
    public void findAllSwApi() throws StarWarsApiException {
        StarwarsModelPagened pagened = service.findAllSwApi(null);
        assertNotNull(pagened);
    }

    @Test
    public void findAllSwApiPage2() throws StarWarsApiException {
        StarwarsModelPagened pagened = service.findAllSwApi(2);
        assertNotNull(pagened);
    }

    @Test(expected = StarWarsApiException.class)
    public void findAllSwApiPageInexistent() throws StarWarsApiException {
        service.findAllSwApi(999999);
    }

    @Test
    public void getAll() throws StarWarsApiException {
        PlanetDocument document = new PlanetDocument();
        document.setName("teste");
        service.save(document);

        StarwarsModelPagened pagened = service.
                getAll(null, null, null, null);

        assertNotNull(pagened);
    }

    @Test
    public void getAllPageNext() throws StarWarsApiException {
        for (int i = 0; i < 15; i++) {
            PlanetDocument document = new PlanetDocument();
            document.setName("teste" + i);
            service.save(document);
        }
        StarwarsModelPagened pagened1 = service.
                getAll(null, null, null, null);

        StarwarsModelPagened pagened2 = service.
                getAll(null, null, "2", null);

        assertNotEquals(pagened1.getResults().size(), pagened2.getResults().size());

    }

    @Test
    public void getAllZero() {
        repository.deleteAll();
        StarwarsModelPagened pagened = service.getAll(null,null,null,null);
        assertEquals(pagened.getResults().size(), 0);
    }


}
