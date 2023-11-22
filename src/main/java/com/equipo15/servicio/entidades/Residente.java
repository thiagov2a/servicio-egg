
package com.equipo15.servicio.entidades;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import lombok.Data;
import org.springframework.security.core.userdetails.User;



/**
 *
 * @author alviz
 */
@Entity
@Data
public class Residente {
    
    @Id
    private String dni_cuil;
    private String domicilio;
    
    @OneToOne
    private Usuario usuario;
    
    @OneToMany(mappedBy = "residente")
    private List<Transaccion> transacciones;
    

}
    
