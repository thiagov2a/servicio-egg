
package com.equipo15.servicio.repositorios;

import com.equipo15.servicio.entidades.Servicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author alviz
 */
@Repository
public interface ServicioRepositorio extends JpaRepository<Servicio, String> {
    
}
