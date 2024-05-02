package com.uoc.tumesa.app.spring;

import com.uoc.tumesa.repo.Repository;
import jakarta.annotation.PreDestroy;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TuMesaApp {

	public static void main(String[] args) {
		SpringApplication.run(TuMesaApp.class, args);
	}

	@PreDestroy
	public void onExit() {
		// Cerramos la conexión con la BBDD al finalizar la aplicación
		Repository.shutdown();
	}
}