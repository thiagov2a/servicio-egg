package com.equipo15.servicio.repositorios;

import com.equipo15.servicio.entidades.Proveedor;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProveedorRepositorio extends JpaRepository<Proveedor, String> {

    @Query("SELECT p FROM Proveedor p WHERE p.usuario.dni = :dni")
    public Proveedor buscarPorDni(@Param("dni") String dni);

    @Query("SELECT p FROM Proveedor p WHERE p.usuario.email = :email")
    public Proveedor buscarPorEmail(@Param("email") String email);

    @Query("SELECT p FROM Proveedor p WHERE p.contacto = :contacto")
    public Proveedor buscarPorContacto(@Param("contacto") String contacto);

    @Query("SELECT p FROM Proveedor p WHERE p.servicio.id = :id ORDER BY p.calificacion DESC")
    public List<Proveedor> listarPorServicio(@Param("id") String id);
    
    @Query("SELECT p FROM Proveedor p WHERE p.servicio.id = :id ORDER BY p.calificacion")
    public List<Proveedor> listarPorServicio2(@Param("id") String id);

    @Query("SELECT p FROM Proveedor p ORDER BY p.calificacion DESC")
    public List<Proveedor> listar();
    
    @Query("SELECT p FROM Proveedor p ORDER BY p.calificacion")
    public List<Proveedor> listar2();

    @Query("SELECT p FROM Proveedor p WHERE p.servicio.id = :id AND p.usuario.alta = :alta ORDER BY p.calificacion DESC")
    public List<Proveedor> listarPorServicioPorAlta(@Param("id") String id, @Param("alta") Boolean alta);
    
    @Query("SELECT p FROM Proveedor p WHERE p.servicio.id = :id AND p.usuario.alta = :alta ORDER BY p.calificacion")
    public List<Proveedor> listarPorServicioPorAlta2(@Param("id") String id, @Param("alta") Boolean alta);

    @Query("SELECT p FROM Proveedor p WHERE p.usuario.alta = TRUE ORDER BY p.calificacion DESC")
    public List<Proveedor> listarPorAlta(@Param("alta") Boolean alta);
    
    @Query("SELECT p FROM Proveedor p WHERE p.usuario.alta = TRUE ORDER BY p.calificacion")
    public List<Proveedor> listarPorAlta2(@Param("alta") Boolean alta);

    @Query("SELECT p FROM Proveedor p WHERE p.servicio.id = :id ORDER BY p.precioPorHora")
    public List<Proveedor> listarPorServicioOrdenadoPorMenorPrecio(@Param("id") String id);
    
    @Query("SELECT p FROM Proveedor p WHERE p.servicio.id = :id ORDER BY p.precioPorHora DESC")
    public List<Proveedor> listarPorServicioOrdenadoPorMayorPrecio(@Param("id") String id);

    @Query("SELECT p FROM Proveedor p WHERE p.servicio.id = :id AND p.usuario.alta = TRUE ORDER BY p.precioPorHora")
    public List<Proveedor> listarPorServicioPorAltaOrdenadoPorMenorPrecio(@Param("id") String id);
    
    @Query("SELECT p FROM Proveedor p WHERE p.servicio.id = :id AND p.usuario.alta = TRUE ORDER BY p.precioPorHora DESC")
    public List<Proveedor> listarPorServicioPorAltaOrdenadoPorMayorPrecio(@Param("id") String id);

    @Query("SELECT p FROM Proveedor p WHERE p.usuario.alta = TRUE ORDER BY p.precioPorHora")
    public List<Proveedor> listarPorAltaOrdenadoPorMenorPrecio(@Param("alta") Boolean alta);
    
    @Query("SELECT p FROM Proveedor p WHERE p.usuario.alta = TRUE ORDER BY p.precioPorHora DESC")
    public List<Proveedor> listarPorAltaOrdenadoPorMayorPrecio(@Param("alta") Boolean alta);

    @Query("SELECT p FROM Proveedor p ORDER BY p.precioPorHora")
    public List<Proveedor> listarOrdenadoPorMenorPrecio();
    
    @Query("SELECT p FROM Proveedor p ORDER BY p.precioPorHora DESC")
    public List<Proveedor> listarOrdenadoPorMayorPrecio();

}
