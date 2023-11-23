package com.equipo15.servicio.entidades;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import org.hibernate.annotations.GenericGenerator;

import lombok.Data;

@Entity
@Data
public class Imagen {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    private String mime;

    private String nombre;

    @Column(columnDefinition = "LONGBLOB")
    @Basic(fetch = FetchType.LAZY)
    private byte[] contenido;

}
