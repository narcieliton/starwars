package com.narcielitonlopes.starwars.planet;

import org.springframework.dao.DuplicateKeyException;
import com.narcielitonlopes.starwars.domain.document.PlanetDocument;
import com.narcielitonlopes.starwars.repository.PlanetRepository;
import com.narcielitonlopes.starwars.swapi.StarWarsConsumer;
import com.narcielitonlopes.starwars.swapi.model.StarwarsModelPagened;
import com.narcielitonlopes.starwars.util.StarWarsApiException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Optional;
import java.util.UUID;

@Service
public class PlanetServiceImpl implements IPlanetService {

    private final PlanetRepository repository;
    private final StarWarsConsumer swConsumer;

    @Autowired
    public PlanetServiceImpl(StarWarsConsumer swConsumer,
                             PlanetRepository repository){
        this.swConsumer = swConsumer;
        this.repository = repository;
    }

    @Override
    public StarwarsModelPagened getAll(String sortColumn, String sortDirection, String pageIndex, String pageSize){
        String pageSelected =  pageIndex != null ? String.valueOf(Integer.parseInt(pageIndex) - 1) : null;
        Pageable pageable = createPageable(sortColumn, sortDirection, pageSelected, pageSize);
        Page<PlanetDocument> page = repository.findAll(pageable);
        Pageable pagePageable = page.getPageable();
        return createModelPagened(page, pagePageable);
    }

    @Override
    public StarwarsModelPagened findAllSwApi(Integer page) throws StarWarsApiException {
        try {
            return swConsumer.getListPlanets(page);
        }catch (HttpClientErrorException e){
            throw new StarWarsApiException("Erro ao consumir API Star Wars");
        }
    }

    @Override
    public PlanetDocument findById(String id) throws StarWarsApiException {
        try {
            Optional<PlanetDocument> optional = this.repository.findById(id);
            if (optional.isEmpty()) {
                return null;
            }
            return optional.get();
        }catch (IllegalArgumentException e){
            throw new StarWarsApiException("Id do Planeta não pode ser nulo!");
        }
    }

    @Override
    public PlanetDocument findByName(String name) throws StarWarsApiException {
        try {
            PlanetDocument document = this.repository.findByNameIgnoreCase(name);
            return document;
        }catch (IllegalArgumentException e){
            throw new StarWarsApiException("Nome do Planeta não pode ser nulo!");
        }
    }

    @Override
    public PlanetDocument save(PlanetDocument document) throws StarWarsApiException {
        try{
            if(null == document.getName()){
                throw new StarWarsApiException("Nome do planeta não pode ser nulo!");
            }
            document.setId(UUID.randomUUID().toString());
            document.setAmountOfMovie(swConsumer.getAmountAppearedInMovies(document.getName()));
            return this.repository.save(document);
        }catch (DuplicateKeyException e){
            throw new StarWarsApiException("Erro ao persistir Planeta, nome já existente");
        }
    }

    @Override
    public void delete(String id) throws StarWarsApiException {
       PlanetDocument document = this.findById(id);
       if(null == document){
           throw new StarWarsApiException("Planeta não encontrado!");
       }
       this.repository.delete(this.findById(id));
    }

    private StarwarsModelPagened createModelPagened(Page<PlanetDocument> page, Pageable pagePageable) {
        StarwarsModelPagened pagened = new StarwarsModelPagened();
        pagened.setCount(page.getTotalElements());
        pagened.setResults(page.getContent());
        createNumberNextPage(page, pagePageable, pagened);
        createNumberPreviousPage(pagePageable, pagened);
        return pagened;
    }

    private void createNumberPreviousPage(Pageable pagePageable, StarwarsModelPagened pagened) {
        if(pagePageable.hasPrevious() && pagePageable.previousOrFirst().getPageNumber() == 0){
            pagened.setPrevious(String.valueOf(pagePageable.previousOrFirst().getPageNumber()+1));
        }else{
            String numberPagePrevious = pagePageable.previousOrFirst().getPageNumber() == 0 ? null : String.valueOf(pagePageable.previousOrFirst().getPageNumber()+1);
            pagened.setPrevious(numberPagePrevious);
        }
    }

    private void createNumberNextPage(Page<PlanetDocument> page, Pageable pagePageable, StarwarsModelPagened pagened) {
        if(page.getTotalPages() == page.getNumber() + 1){
            pagened.setNext(null);
        }else {
            pagened.setNext(String.valueOf(pagePageable.next().getPageNumber() + 1));
        }
    }

    private Pageable createPageable(String sortColumn, String sortDirection, String page, String pageSize) {
        Sort.Order order;
        if (StringUtils.isBlank(sortColumn)) { sortColumn = "id"; }
        if (StringUtils.isBlank(sortDirection)) { sortDirection = "asc"; }
        if (sortDirection.equals("asc")) {
            order = Sort.Order.asc(sortColumn);
        } else {
            order = Sort.Order.desc(sortColumn);
        }
        if (StringUtils.isBlank(page)) { page = "0"; }
        if (StringUtils.isBlank(pageSize)) { pageSize = "10"; }
        return PageRequest.of(Integer.parseInt(page), Integer.parseInt(pageSize), Sort.by(order));
    }
}
