package com.equipo15.servicio.entidades;

import com.equipo15.servicio.enumeraciones.Estado;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
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
