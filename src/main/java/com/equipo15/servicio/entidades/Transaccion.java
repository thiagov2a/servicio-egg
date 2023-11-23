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
    private Integer calificacion;
    private String comentario;
    private Long presupuesto;

    @Enumerated(EnumType.STRING)
    private Estado estado;

    @ManyToOne
    private Proveedor proveedor;

    @ManyToOne
    private Residente residente;

}
