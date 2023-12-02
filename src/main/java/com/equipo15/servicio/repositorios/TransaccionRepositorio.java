package com.equipo15.servicio.repositorios;

import com.equipo15.servicio.entidades.Transaccion;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TransaccionRepositorio extends JpaRepository<Transaccion, String> {

    @Query("SELECT t FROM Transaccion t WHERE t.proveedor.usuario.id = :id")
    public List <Transaccion> listarTransaccionesPorProveedor(@Param ("id") String id);
    
    @Query("SELECT t FROM Transaccion t WHERE t.usuario.id = :id")
    public List <Transaccion> listarTransaccionesPorUsuario(@Param ("id") String id);
    
    
}
