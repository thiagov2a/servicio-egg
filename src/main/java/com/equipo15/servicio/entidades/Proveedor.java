package com.equipo15.servicio.entidades;

import java.util.List;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Entity
@Data
public class Proveedor {

    @Id
    private String cuil;
    private String contacto;
    private Integer calificacion;
    private String descripcion;
    private Integer precioh;

    @ManyToOne
    private Servicio servicio;

    @OneToOne
    private Usuario usuario;

    @OneToMany(mappedBy = "proveedor")
    private List<Transaccion> transacciones;

}
