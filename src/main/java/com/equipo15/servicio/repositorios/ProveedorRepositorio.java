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

    @Query("SELECT p FROM Proveedor p WHERE p.servicio.id = :id")
    public List<Proveedor> listarPorServicio(@Param("id") String id);
    
    @Query("SELECT p FROM Proveedor p WHERE p.servicio.id = :id AND p.usuario.alta = TRUE")
    public List<Proveedor> listarPorServicioPorAlta(@Param("id") String id);

    @Query("SELECT p FROM Proveedor p WHERE p.usuario.alta = TRUE")
    public List<Proveedor> listarPorAlta(@Param("alta") Boolean alta);
    

}
