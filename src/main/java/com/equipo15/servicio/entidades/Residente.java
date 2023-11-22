package com.equipo15.servicio.entidades;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import lombok.Data;
import org.springframework.security.core.userdetails.User;


import lombok.Data;

@Entity
@Data
public class Residente {

    @Id
<<<<<<< HEAD
    private String dni_cuil;
    private String domicilio;
    
    @OneToOne
    private Usuario usuario;
    
    @OneToMany(mappedBy = "residente")
    private List<Transaccion> transacciones;
    
=======
    private String dni;
    private String nombre;
    private String direccion;
    private Boolean altaBaja;
>>>>>>> 4f54ebfc3e565656f8de5016fdd819d469ccc630

}
    
