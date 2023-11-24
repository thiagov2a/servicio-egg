package com.equipo15.servicio.entidades;

import java.util.List;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class Servicio {

    @Id
    private String id;
    private String nombre;

    @OneToMany(mappedBy = "servicio")
    private List<Proveedor> proveedores;

}
