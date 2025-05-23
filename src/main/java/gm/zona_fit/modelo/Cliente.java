package gm.zona_fit.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Data
@ToString
@NoArgsConstructor //Constructor Vacio
@AllArgsConstructor //Constructor con todos los parametros
@EqualsAndHashCode
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//Autorincrementable
    private Integer id;
    private String nombre;
    private String apellido;
    private Integer membresia;
}
