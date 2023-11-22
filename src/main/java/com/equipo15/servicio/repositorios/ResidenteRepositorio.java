package com.equipo15.servicio.repositorios;

import com.equipo15.servicio.entidades.Residente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResidenteRepositorio extends JpaRepository<Residente, String> {
    
}
