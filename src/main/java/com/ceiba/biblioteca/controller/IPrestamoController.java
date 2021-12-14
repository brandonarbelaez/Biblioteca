package com.ceiba.biblioteca.controller;

import com.ceiba.biblioteca.commons.GenericResponseDTO;
import com.ceiba.biblioteca.entity.DAO.PrestamoDAO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface IPrestamoController {
    ResponseEntity<GenericResponseDTO> prestamo(@RequestBody PrestamoDAO prestamoDAO);
    ResponseEntity<GenericResponseDTO> consultarPrestamo(@PathVariable Integer id);
}
