
package com.equipo15.servicio.repositorios;

import com.equipo15.servicio.entidades.Residente;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author alviz
 */
@Repository
public interface ResidenteRepositorio extends JpaRepository<Residente, String> {
   @Query("SELECT r FROM Residente r WHERE r.nombre = :nombre")
    public List<Residente> buscarPorNombre(@Param("nombre") String nombre);
    
    @Query("SELECT r FROM Residente r WHERE r.direccion = :direccion")
    public List<Residente> buscarPorDireccion(@Param ("direccion") String direccion );   
}
