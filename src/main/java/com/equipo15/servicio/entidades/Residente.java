package com.equipo15.servicio.entidades;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Residente {

    @Id
    private String dni;
    private String nombre;
    private String direccion;
    private Boolean altaBaja;

}
