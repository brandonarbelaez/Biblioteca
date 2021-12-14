package com.ceiba.biblioteca.service;

import com.ceiba.biblioteca.commons.GenericResponseDTO;
import com.ceiba.biblioteca.entity.DAO.PrestamoDAO;
import org.springframework.http.ResponseEntity;

public interface IPrestamoService {
    ResponseEntity<GenericResponseDTO> prestamo(PrestamoDAO prestamoDAO);
    ResponseEntity<GenericResponseDTO> consultarPrestamo(Integer id);

}
