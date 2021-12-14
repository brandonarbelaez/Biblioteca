package com.ceiba.biblioteca.entity.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PrestamoDTO {
    private Integer identificacionUsuario;
    private String fechaMaximaDevolucion;
    private Date fechaPrestamo;
    private Integer tipoUsuario;
    private String isbn;

}
