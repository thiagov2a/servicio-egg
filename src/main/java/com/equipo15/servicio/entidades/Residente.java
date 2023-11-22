
package com.equipo15.servicio.entidades;

import javax.persistence.Entity;
import javax.persistence.Id;


/**
 *
 * @author alviz
 */
@Entity
public class Residente {
    
    @Id
    private String dni;
    private String nombre;
    private String direccion;
    private Boolean altaBaja;


    public Residente() {
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Boolean getAltaBaja() {
        return altaBaja;
    }

    public void setAltaBaja(Boolean altaBaja) {
        this.altaBaja = altaBaja;
    }
    
    
    
}
