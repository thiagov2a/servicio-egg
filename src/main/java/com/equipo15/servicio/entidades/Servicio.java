
package com.equipo15.servicio.entidades;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.Data;

/**
 *
 * @author alviz
 */
@Entity
@Data
public class Servicio {
    
    @Id
    private String id;
    private String nombre;
    
   @OneToMany(mappedBy = "servicio")
    private List<Proveedor> proveedores;

}
