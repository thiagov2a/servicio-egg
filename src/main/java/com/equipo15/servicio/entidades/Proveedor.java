package com.equipo15.servicio.entidades;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import lombok.Data;

@Entity
@Data
public class Proveedor {

    @Id
    private String cuil;
    private String nombre;
    private String tipoServicio;
    private Boolean altaBaja;
    private Boolean cancelado;

    @OneToOne
    private Imagen imagen;

}
