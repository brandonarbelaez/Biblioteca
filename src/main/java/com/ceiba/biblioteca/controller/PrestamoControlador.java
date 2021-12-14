package com.ceiba.biblioteca.controller;


import com.ceiba.biblioteca.commons.GenericResponseDTO;
import com.ceiba.biblioteca.entity.DAO.PrestamoDAO;
import com.ceiba.biblioteca.service.IPrestamoService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("prestamo")
@CrossOrigin({"*"})
public class PrestamoControlador implements IPrestamoController {
    private final IPrestamoService iPrestamoService;

    public PrestamoControlador(IPrestamoService iPrestamoService) {
        this.iPrestamoService = iPrestamoService;
    }


    @Override
    @PostMapping("/")
    @ApiOperation(value = "Obtener prestamo", notes = "metodo para solicitar prestamo")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente", response = GenericResponseDTO.class),
            @ApiResponse(code = 400, message = "Bad Request.", response = String.class),
            @ApiResponse(code = 500, message = "Error inesperado del sistema")})
    public ResponseEntity<GenericResponseDTO> prestamo(@ApiParam(type = "json", value = "{\n" +
            "     \"isbn\":\"DASW154212\",\n" +
            "     \"identificacionUsuario\":154519485,\n" +
            "     \"tipoUsuario\":1\n" +
            " }", example = "{\n" +
            "\"isbn\":\"DASW154212\",\n" +
            "\"identificacionUsuario\":154519485,\n" +
            "\"tipoUsuario\":3\n" +
            "}") @RequestBody PrestamoDAO prestamoDAO) {
        return iPrestamoService.prestamo(prestamoDAO);
    }

    @Override
    @GetMapping("/{id-prestamo}")
    @ApiOperation(value = "Obtener prestamo", notes = "metodo para obtener prestamo por id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente", response = GenericResponseDTO.class),
            @ApiResponse(code = 400, message = "Bad Request.", response = String.class),
            @ApiResponse(code = 500, message = "Error inesperado del sistema")})
    public ResponseEntity<GenericResponseDTO> consultarPrestamo(@ApiParam(type = "Integer", value = "1", example = "1") @PathVariable("id-prestamo") Integer id) {
        return iPrestamoService.consultarPrestamo(id);
    }
}

