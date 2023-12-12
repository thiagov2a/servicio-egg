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
    public List<Transaccion> listarTransaccionesPorProveedor(@Param("id") String id);

    @Query("SELECT t FROM Transaccion t WHERE t.usuario.id = :id")
    public List<Transaccion> listarTransaccionesPorUsuario(@Param("id") String id);

    @Query("SELECT COUNT(t) FROM Transaccion t WHERE t.usuario.id = :id and t.estado = 'ACEPTADO'")
    public Integer contarTransaccionesPorUsuario(@Param("id") String id);

    @Query("SELECT COUNT(t) FROM Transaccion t WHERE t.proveedor.usuario.id = :id and t.estado = 'PENDIENTE'")
    public Integer contarTransaccionesPorProveedor(@Param("id") String id);

    @Query("SELECT SUM(t.calificacion) FROM Transaccion t WHERE t.proveedor.usuario.id = :id and t.estado = 'FINALIZADO'")
    public Integer sumaDeCalificacionesPorProveedor(@Param("id") String id);

    @Query("SELECT COUNT(t) FROM Transaccion t WHERE t.proveedor.usuario.id = :id and t.estado = 'FINALIZADO'")
    public Integer cantidadDeCalificacionesPorProveedor(@Param("id") String id);

}
