package gm.zona_fit.repositorio;

import gm.zona_fit.modelo.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * La interfaz permite comprender la interaccion con la base de datos sin preocuparse por la implementación
 * Se presenta la tabla y el tipo de la llave primaria
 */

public interface ClienteRepositorio extends JpaRepository<Cliente, Integer> {

}
