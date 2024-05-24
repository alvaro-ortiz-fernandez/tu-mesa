package com.uoc.tumesa.app.spring.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controlador principal de la aplicación, para devolver al frontend el HTML SPA (Single Page Application).
 * */
@Controller
public class MainController {

    // La app sólo tiene 1 controlador por GET (las rutas se gestionan en el front con Vue),
    // si se intenta acceder a otra ruta redirigimos a '/'
    @GetMapping("/*")
    public String anyother() {
        return "redirect:/";
    }

    @GetMapping("/")
    public String app() {
        return "index";
    }
}
