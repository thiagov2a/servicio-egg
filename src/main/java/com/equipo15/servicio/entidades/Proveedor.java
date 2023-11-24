package com.equipo15.servicio.entidades;

import java.util.List;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "proveedores")
public class Proveedor {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @OneToOne
    private Usuario usuario;

    private String contacto;
    private String descripcion;
    private Integer precioPorHora;
    private Integer calificacion;

    @ManyToOne
    @JoinColumn(name = "servicio_id")
    private Servicio servicio;

    @OneToMany(mappedBy = "proveedor")
    private List<Transaccion> transacciones;

}
