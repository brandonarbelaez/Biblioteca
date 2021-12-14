package com.ceiba.biblioteca.service;

import com.ceiba.biblioteca.commons.GenericResponseDTO;
import com.ceiba.biblioteca.entity.DAO.PrestamoDAO;
import com.ceiba.biblioteca.entity.DTO.PrestamoDTO;
import com.ceiba.biblioteca.repository.PrestamoLibroRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.function.IntFunction;

@Service
@Slf4j
public class PrestamoService implements IPrestamoService {
    private final PrestamoLibroRepository prestamoLibroRepository;
    private final ModelMapper modelMapper;

    public PrestamoService(PrestamoLibroRepository prestamoLibroRepository, ModelMapper modelMapper) {
        this.prestamoLibroRepository = prestamoLibroRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public ResponseEntity<GenericResponseDTO> prestamo(PrestamoDAO prestamoDAO) {
        try {
            String mensaje;
            String devolucion;
            int statusCode;
            PrestamoDAO respuesta = null;

            PrestamoDTO prestamoDTO = new PrestamoDTO();
            modelMapper.map(prestamoDAO, prestamoDTO);

            String timeStamp = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime());
            Date date = new Date(timeStamp);
            Calendar calendar = Calendar.getInstance();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
            prestamoDTO.setFechaPrestamo(date);

            Integer identificacion = prestamoDTO.getIdentificacionUsuario();
            Integer contadorPrestamos = contador(identificacion);
            Integer tipoUsuario = prestamoDAO.getTipoUsuario();
            String isbn=prestamoDAO.getIsbn();

            Integer countIsbn = contadorIsbn(isbn);
            if (countIsbn<=0) {
                modelMapper.map(prestamoDTO, prestamoDAO);
                switch (tipoUsuario) {
                    case 1:
                        LocalDate fechadev1 = LocalDate.of(calendar.get(Calendar.YEAR),
                                Integer.parseInt(String.valueOf(calendar.get(Calendar.MONTH) + 1)),
                                calendar.get(Calendar.DAY_OF_MONTH))
                                .with(addBusinessDays.apply(10));
                        devolucion = formatter.format(fechadev1);
                        prestamoDTO.setFechaMaximaDevolucion(devolucion);
                        modelMapper.map(prestamoDTO, prestamoDAO);
                        respuesta = prestamoLibroRepository.save(prestamoDAO);

                        mensaje = "Se realiza el prestamo del libro al usuario con identificacion: " + identificacion;
                        statusCode = 200;
                        break;

                    case 2:
                        LocalDate fechadev2 = LocalDate.of(calendar.get(Calendar.YEAR),
                                Integer.parseInt(String.valueOf(calendar.get(Calendar.MONTH) + 1)),
                                calendar.get(Calendar.DAY_OF_MONTH))
                                .with(addBusinessDays.apply(8));
                        devolucion = formatter.format(fechadev2);
                        prestamoDTO.setFechaMaximaDevolucion(devolucion);
                        modelMapper.map(prestamoDTO, prestamoDAO);
                        respuesta = prestamoLibroRepository.save(prestamoDAO);
                        mensaje = "Se realiza el prestamo del libro al usuario con identificacion: " + identificacion;
                        statusCode = 200;
                        break;

                    case 3:
                        if (contadorPrestamos <= 0) {
                            LocalDate fechadev3 = LocalDate.of(calendar.get(Calendar.YEAR),
                                    Integer.parseInt(String.valueOf(calendar.get(Calendar.MONTH) + 1)),
                                    calendar.get(Calendar.DAY_OF_MONTH))
                                    .with(addBusinessDays.apply(7));
                            devolucion = formatter.format(fechadev3);
                            prestamoDTO.setFechaMaximaDevolucion(devolucion);
                            modelMapper.map(prestamoDTO, prestamoDAO);
                            respuesta = prestamoLibroRepository.save(prestamoDAO);
                            mensaje = "Se realiza el prestamo del libro al usuario con identificacion: " + identificacion;
                            statusCode = 200;

                        } else {
                            mensaje = "El usuario con identificación " + identificacion + " ya tiene un libro prestado por lo cual no se le puede realizar otro préstamo";
                            statusCode = 400;
                        }
                        break;

                    default:
                        mensaje = "Tipo de usuario no permitido en la biblioteca";
                        statusCode = 400;
                        break;
                }
            }else {
                mensaje="El libro ya se ha prestado";
                statusCode=400;
            }

            return new ResponseEntity<>(GenericResponseDTO.builder().message(mensaje)
                    .objectResponse(respuesta).statusCode(statusCode).build(), HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(GenericResponseDTO.builder().message("Error:  " + e.getMessage())
                    .objectResponse(false).statusCode(HttpStatus.BAD_REQUEST.value()).build(), HttpStatus.BAD_REQUEST);
        }

    }

    @Override
    public ResponseEntity<GenericResponseDTO> consultarPrestamo(Integer id) {
        try {

            List<PrestamoDAO> respuestaTramite = prestamoLibroRepository.consultarPorId(id);
            return new ResponseEntity<>(GenericResponseDTO.builder().message("Se consulta por el id: " + id)
                    .objectResponse(respuestaTramite).statusCode(HttpStatus.OK.value()).build(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(GenericResponseDTO.builder().message("Error:  " + e.getMessage())
                    .objectResponse(false).statusCode(HttpStatus.BAD_REQUEST.value()).build(), HttpStatus.BAD_REQUEST);
        }
    }

    public Integer contador(Integer id) {

        try {

            return prestamoLibroRepository.contador(id);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    public Integer contadorIsbn(String isbn) {

        try {

            return prestamoLibroRepository.contadorIsbn(isbn);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }


    IntFunction<TemporalAdjuster> addBusinessDays = days -> TemporalAdjusters.ofDateAdjuster(
            date -> {
                LocalDate baseDate =
                        days > 0 ? date.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
                                : days < 0 ? date.with(TemporalAdjusters.nextOrSame(DayOfWeek.FRIDAY)) : date;
                int businessDays = days + Math.min(Math.max(baseDate.until(date).getDays(), -4), 4);
                return baseDate.plusWeeks(businessDays / 5).plusDays(businessDays % 5);
            });
}