package com.equipo15.servicio.servicios;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.equipo15.servicio.entidades.Imagen;
import com.equipo15.servicio.entidades.Servicio;
import com.equipo15.servicio.excepciones.MiException;
import com.equipo15.servicio.repositorios.ServicioRepositorio;

import jakarta.transaction.Transactional;

@Service
public class ServicioServicio {

    @Autowired
    private ServicioRepositorio servicioRepositorio;
    @Autowired
    private ImagenServicio imagenServicio;

    @Transactional
    public void registrar(String nombre, String descripcion, MultipartFile archivo) throws MiException {
        validar(nombre, descripcion);

        Servicio servicio = new Servicio();

        servicio.setNombre(nombre);
        servicio.setDescripcion(descripcion);
        servicio.setAlta(true);

        Imagen imagen = imagenServicio.guardar(archivo, "/src/main/resources/static/img/service_default.png");
        servicio.setImagen(imagen);

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
    public void modificar(String id, String nombre, String descripcion, MultipartFile archivo) throws MiException {
        validar(nombre, descripcion);

        Optional<Servicio> respuesta = servicioRepositorio.findById(id);

        if (respuesta.isPresent()) {
            Servicio servicio = respuesta.get();

            servicio.setNombre(nombre);
            servicio.setDescripcion(descripcion);

            String idImagen = null;
            if (servicio.getImagen() != null) {
                idImagen = servicio.getImagen().getId();
            }

            Imagen imagen = imagenServicio.actualizar(archivo, idImagen,
                    "/src/main/resources/static/img/service_default.png");
            servicio.setImagen(imagen);

            servicioRepositorio.save(servicio);
        }
    }

    @Transactional
    public void cambiarEstado(String id) throws MiException {
        Optional<Servicio> respuesta = servicioRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Servicio servicio = respuesta.get();
            servicio.setAlta(!servicio.getAlta());
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
