package com.ceiba.biblioteca.entity.DAO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "Prestamo")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "parent"})
public class PrestamoDAO implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", unique = true)
    private Integer id;

    @Column(name = "Isbn", unique = true)
    private String isbn;

    @Column(name = "Identificacion_Usuario", length = 100)
    @Basic(optional = false)
    private Integer identificacionUsuario;

    @Column(name = "Tipo_Usuario", length = 100)
    @Basic(optional = false)
    private Integer tipoUsuario;

    @Column(name = "FechaPrestamo")
    @Temporal(TemporalType.DATE)
    private Date fechaPrestamo;

    @Column(name = "FechaDevolucion")
    //@Temporal(TemporalType.DATE)
    private String fechaMaximaDevolucion;

}
