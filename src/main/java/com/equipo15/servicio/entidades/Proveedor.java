
package com.equipo15.servicio.entidades;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.Data;


/**
 *
 * @author alviz
 */
@Entity
@Data
public class Proveedor {
    
    @Id
    private String cuil;
    private String contacto;
    private Integer calificacion;
    private String descripcion;
    private Integer precioh;
    
    @ManyToOne
    private Servicio servicio;
    
    private Usuario usuario;
    
    @OneToMany(mappedBy = "proveedor")
    private List<Transaccion> transacciones;
        
}
