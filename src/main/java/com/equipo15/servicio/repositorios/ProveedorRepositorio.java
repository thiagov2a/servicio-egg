
package com.equipo15.servicio.repositorios;

import com.equipo15.servicio.entidades.Proveedor;
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
public interface ProveedorRepositorio extends JpaRepository<Proveedor, String> {
    
    @Query("SELECT l FROM Proveedor l WHERE l.usuario.nombre = :nombre")
    public List<Proveedor> buscarPorNombre(@Param("nombre") String nombre);
    
    @Query("SELECT l FROM Proveedor l WHERE l.servicio.nombre = :nombre")
    public List<Proveedor> buscarPorServicio(@Param ("nombre") String nombre);
    
}
