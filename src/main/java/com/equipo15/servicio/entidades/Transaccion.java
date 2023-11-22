
package com.equipo15.servicio.entidades;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;


/**
 *
 * @author alviz
 */
@Entity
@Data
public class Transaccion {
    
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    private String puntaje;
    private Integer comentario;
    private String presupuesto;
    
    @Temporal(TemporalType.DATE)
    private Date inicio;
    
    @Temporal(TemporalType.DATE)
    private Date termino;
    
    @OneToOne
    private Proveedor proveedor;
    
    @OneToOne
    private Residente residente;

    public Transaccion() {
    }
    
    
}
