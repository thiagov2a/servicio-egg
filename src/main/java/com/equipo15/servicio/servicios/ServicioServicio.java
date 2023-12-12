package com.equipo15.servicio.servicios;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.equipo15.servicio.entidades.Servicio;
import com.equipo15.servicio.excepciones.MiException;
import com.equipo15.servicio.repositorios.ServicioRepositorio;

import jakarta.transaction.Transactional;

@Service
public class ServicioServicio {

    @Autowired
    private ServicioRepositorio servicioRepositorio;

    @Transactional
    public void registrar(String nombre, String descripcion) throws MiException {
        validar(nombre, descripcion);

        Servicio servicio = new Servicio();

        servicio.setNombre(nombre);
        servicio.setDescripcion(descripcion);
        servicio.setAlta(true);

        servicioRepositorio.save(servicio);
    }

    public List<Servicio> listarServicios() {
        List<Servicio> servicios = new ArrayList<>();
        servicios = servicioRepositorio.findAll();
        return servicios;
    }

    public List<Servicio> listarServicioPorAlta(Boolean alta) {
        List<Servicio> servicios = new ArrayList<>();
        servicios = servicioRepositorio.listarPorAlta(alta);
        return servicios;
    }

    public Servicio buscarServicioPorId(String id) {
        Optional<Servicio> respuesta = servicioRepositorio.findById(id);
        if (respuesta.isPresent()) {
            return respuesta.get();
        } else {
            return null;
        }
    }

    @Transactional

    public void modificar(String id, String nombre, String descripcion) throws MiException {

       validar(nombre, descripcion);

        Optional<Servicio> respuesta = servicioRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Servicio servicio = respuesta.get();

            servicio.setNombre(nombre);
            servicio.setDescripcion(descripcion);

            servicioRepositorio.save(servicio);
        }
    }

    @Transactional
    public void cambiarEstadoServicio(String id) {
        Optional<Servicio> respuesta = servicioRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Servicio servicio = respuesta.get();
            if (servicio.getAlta()) {
                servicio.setAlta(false);
            } else {
                servicio.setAlta(true);
            }
            servicioRepositorio.save(servicio);
        }
    }

    public void validar(String nombre, String descripcion) throws MiException {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new MiException("El nombre no puede ser nulo o estar vacío");
        }

        if (descripcion == null || descripcion.trim().isEmpty()) {
            throw new MiException("La descripción no puede ser nula o estar vacía");
        }
    }

}
