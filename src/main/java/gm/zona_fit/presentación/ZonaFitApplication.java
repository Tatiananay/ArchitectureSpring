package gm.zona_fit.presentación;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * No es necesario almacenarla en un paquete, lo realizé para mayor compreensión de la arquitectura de Spring
 */
@SpringBootApplication //<-- Importante ver como Spring ya la maneja como aplicación
public class ZonaFitApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZonaFitApplication.class, args);
		//System.out.println("Zona Fit");

	}

}
