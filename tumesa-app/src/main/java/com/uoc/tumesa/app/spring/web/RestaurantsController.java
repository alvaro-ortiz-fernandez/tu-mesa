package com.uoc.tumesa.app.spring.web;

import com.uoc.tumesa.app.model.FiltroBusquedaRestaurantes;
import com.uoc.tumesa.app.model.Restaurant;
import com.uoc.tumesa.repo.Repository;
import com.uoc.tumesa.repo.dao.RestaurantsDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/restaurantes", method = { RequestMethod.POST })
public class RestaurantsController {

    private static final Logger logger = LoggerFactory.getLogger(RestaurantsController.class);

    @Autowired
    private Repository repository;


    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<?> listado(@RequestBody FiltroBusquedaRestaurantes filtro) {
        try {
            logger.info("Obteniendo restaurantes con filtro [{}]...", filtro);
            List<Restaurant> restaurants = repository.getDAO(RestaurantsDAO.class).findByTerm(filtro.termino()).stream()
                    .map(Restaurant::new)
                    .toList();

            logger.info("Nº de restaurantes obtenidos: {}", restaurants.size());
            return new ResponseEntity<>(restaurants, HttpStatus.OK);

        } catch (Exception e) {
            logger.error("Se produjo un error al obtener los restaurantes: ", e);
            return new ResponseEntity<>(new Error(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/restaurante", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<?> restaurante(@RequestBody FiltroBusquedaRestaurantes filtro) {
        try {
            logger.info("Obteniendo restaurante con nombre [{}]...", filtro.nombre());
            Optional<Restaurant> restaurant = repository.getDAO(RestaurantsDAO.class).findByName(filtro.nombre())
                    .map(Restaurant::new);

            logger.info("Restaurante encontrado -> {}", restaurant.isPresent());

            if (restaurant.isEmpty())
                return new ResponseEntity<>(new Error(String.format(
                        "No se encontró el restaurante con nombre %s", filtro.nombre())), HttpStatus.BAD_REQUEST);
            else
                return new ResponseEntity<>(restaurant.get(), HttpStatus.OK);

        } catch (Exception e) {
            logger.error("Se produjo un error al obtene el restaurante: ", e);
            return new ResponseEntity<>(new Error(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
