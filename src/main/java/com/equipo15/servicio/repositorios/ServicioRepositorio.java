package com.equipo15.servicio.repositorios;

import com.equipo15.servicio.entidades.Proveedor;
import com.equipo15.servicio.entidades.Servicio;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ServicioRepositorio extends JpaRepository<Servicio, String> {
    
    @Query("SELECT s FROM Servicio s WHERE s.alta = TRUE")
    public List<Servicio> listarPorAlta(@Param("alta") Boolean alta);
    
}
