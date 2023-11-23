package com.equipo15.servicio.repositorios;

import com.equipo15.servicio.entidades.Transaccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransaccionRepositorio extends JpaRepository<Transaccion, String> {

}
