
package com.equipo15.servicio.entidades;

import com.equipo15.servicio.enumeraciones.Barrio;
import com.equipo15.servicio.enumeraciones.Rol;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Data
public class Usuario {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    private String nombre;
    private String email;
    private String password;
    private Boolean alta;

    @Enumerated(EnumType.STRING)
    private Rol rol;

    @Enumerated(EnumType.STRING)
    private Barrio barrio;

    @OneToOne
    private Imagen imagen;

}
