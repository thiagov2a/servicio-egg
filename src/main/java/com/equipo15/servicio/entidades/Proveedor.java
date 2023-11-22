package com.equipo15.servicio.entidades;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.Data;

import lombok.Data;

@Entity
@Data
public class Proveedor {

    @Id
    private String cuil;
<<<<<<< HEAD
    private String contacto;
    private Integer calificacion;
    private String descripcion;
    private Integer precioh;
    
    @ManyToOne
    private Servicio servicio;
    
    private Usuario usuario;
    
    @OneToMany(mappedBy = "proveedor")
    private List<Transaccion> transacciones;
        
=======
    private String nombre;
    private String tipoServicio;
    private Boolean altaBaja;
    private Boolean cancelado;

    @OneToOne
    private Imagen imagen;

>>>>>>> 4f54ebfc3e565656f8de5016fdd819d469ccc630
}
