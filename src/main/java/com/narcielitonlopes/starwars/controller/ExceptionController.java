package com.narcielitonlopes.starwars.controller;

import com.narcielitonlopes.starwars.domain.dto.ErrorDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

@Slf4j
public class ExceptionController {

    protected ResponseEntity<Error> errorFlow(HttpClientErrorException e) {
        log.error("Erro ao processar requisição!", e);
        ErrorDto error = new ErrorDto();
        error.setMessage("Erro ao consumir Api StarWars ");
        error.setCode(String.valueOf(e.getRawStatusCode()));
        error.setDetail(e.getStatusCode().getReasonPhrase());
        return new ResponseEntity(error, e.getStatusCode());
    }

    protected ResponseEntity<Error> errorFlow(Exception e) {
        final String message = e.getMessage();
        log.error("Erro ao processar requisição!", e);
        ErrorDto error = new ErrorDto();
        error.setCode(HttpStatus.INTERNAL_SERVER_ERROR.toString());
        error.setMessage(message);
        return new ResponseEntity(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
