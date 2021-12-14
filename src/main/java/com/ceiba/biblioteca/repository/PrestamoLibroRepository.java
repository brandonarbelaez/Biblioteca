package com.ceiba.biblioteca.repository;


import com.ceiba.biblioteca.entity.DAO.PrestamoDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrestamoLibroRepository extends JpaRepository<PrestamoDAO,Long> {
    @Query("select count(prestamo) from PrestamoDAO prestamo where prestamo.identificacionUsuario=:identificacion")
    Integer contador(@Param("identificacion") Integer identificacion);

    @Query("select count(prestamo) from PrestamoDAO prestamo where prestamo.isbn=:isbn")
    Integer contadorIsbn(@Param("isbn") String isbn);

    @Query("select prestamo.tipoUsuario from PrestamoDAO prestamo where prestamo.identificacionUsuario=:identificacion")
    Integer tipoUsuario(@Param("identificacion") Integer identificacion);

    @Query("select prestamo from PrestamoDAO prestamo where prestamo.id=:id")
    List<PrestamoDAO> consultarPorId(@Param("id") Integer id);
}
