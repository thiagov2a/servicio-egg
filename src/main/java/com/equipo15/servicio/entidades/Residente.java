package com.equipo15.servicio.entidades;

import java.util.List;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Entity
@Data
public class Residente {

    @Id
    private String dni_cuil;
    private String domicilio;

    @OneToOne
    private Usuario usuario;

    @OneToMany(mappedBy = "residente")
    private List<Transaccion> transacciones;

}
