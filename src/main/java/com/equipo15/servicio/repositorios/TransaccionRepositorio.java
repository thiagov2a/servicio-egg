
package com.equipo15.servicio.repositorios;

import java.util.List;
import com.equipo15.servicio.entidades.Transaccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
/**
 *
 * @author alviz
 */
@Repository
public interface TransaccionRepositorio extends JpaRepository<Transaccion, String> {
  
  @Query("SELECT t FROM Transaccion t WHERE t.proveedor.puntaje = :puntaje")
    public List<Transaccion> buscarPorPuntaje(@Param ("puntaje") String puntaje); 
   
}
