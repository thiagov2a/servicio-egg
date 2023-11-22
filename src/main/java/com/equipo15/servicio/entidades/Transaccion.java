package com.equipo15.servicio.entidades;

import com.equipo15.servicio.enumeraciones.Estado;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Data
public class Transaccion {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
<<<<<<< HEAD
    private Integer calificacion;
    private String comentario;
    private Long presupuesto;
    
    @Enumerated(EnumType.STRING)
    private Estado estado;
    
    @ManyToOne
    private Proveedor proveedor;
    
    @ManyToOne
    private Residente residente;
  
    
=======
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

>>>>>>> 4f54ebfc3e565656f8de5016fdd819d469ccc630
}
