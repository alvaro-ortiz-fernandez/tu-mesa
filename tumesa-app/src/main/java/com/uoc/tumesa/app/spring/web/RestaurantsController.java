package com.uoc.tumesa.app.spring.web;

import com.uoc.tumesa.app.model.FiltroBusquedaRestaurantes;
import com.uoc.tumesa.app.model.Reserva;
import com.uoc.tumesa.app.model.NuevoComentario;
import com.uoc.tumesa.app.service.RestaurantsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador con los endpoints relativos a consulta, visualización y edición de restaurantes.
 * */
@RestController
@RequestMapping(value = "/restaurantes", method = { RequestMethod.POST })
public class RestaurantsController {

    @Autowired
    private RestaurantsService restaurantsService;


    /**
     * Endpoint para obtener los restaurantes con el filtro indicado.
     * */
    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<?> listado(@RequestBody FiltroBusquedaRestaurantes filtro) {
        return restaurantsService.buscarRestaurantes(filtro);
    }

    /**
     * Endpoint para obtener el restaurante con el nombre indicado.
     * */
    @PostMapping(value = "/restaurante", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<?> restaurante(@RequestBody FiltroBusquedaRestaurantes filtro) {
        return restaurantsService.obtenerRestaurante(filtro.nombre());
    }

    /**
     * Endpoint para crear el comentario indicado.
     * */
    @PostMapping(value = "/nuevo-comentario", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<?> nuevoComentario(@RequestBody NuevoComentario comentario) {
        return restaurantsService.crearComentario(comentario.restaurante(), comentario.usuario(), comentario.comentario(), comentario.puntuacion());
    }

    /**
     * Endpoint para crear la reserva indicada.
     * */
    @PostMapping(value = "/nueva-reserva", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<?> nuevaReserva(@RequestBody Reserva reserva) {
        return restaurantsService.crearReserva(reserva);
    }

    /**
     * Endpoint para eliminar la reserva indicada.
     * */
    @PostMapping(value = "/eliminar-reserva", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<?> eliminarReserva(@RequestBody Reserva reserva) {
        return restaurantsService.eliminarReserva(reserva);
    }
}
