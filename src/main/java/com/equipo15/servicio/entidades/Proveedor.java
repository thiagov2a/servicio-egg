
package com.equipo15.servicio.entidades;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;


/**
 *
 * @author alviz
 */
@Entity
public class Proveedor {
    
    @Id
    private String cuil;
    private String nombre;
    private String tipoServicio;
    private Boolean altaBaja;
    private Boolean cancelado;
    
    @OneToOne
    private Imagen imagen;

    public Proveedor() {
    }

    public String getCuil() {
        return cuil;
    }

    public void setCuil(String cuil) {
        this.cuil = cuil;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipoServicio() {
        return tipoServicio;
    }

    public void setTipoServicio(String tipoServicio) {
        this.tipoServicio = tipoServicio;
    }

    public Boolean getAltaBaja() {
        return altaBaja;
    }

    public void setAltaBaja(Boolean altaBaja) {
        this.altaBaja = altaBaja;
    }

    public Imagen getImagen() {
        return imagen;
    }

    public void setImagen(Imagen imagen) {
        this.imagen = imagen;
    }

    public Boolean getCancelado() {
        return cancelado;
    }

    public void setCancelado(Boolean cancelado) {
        this.cancelado = cancelado;
    }
    
    
}
