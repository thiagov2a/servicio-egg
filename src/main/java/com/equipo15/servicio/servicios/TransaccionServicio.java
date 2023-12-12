package com.equipo15.servicio.servicios;

import com.equipo15.servicio.entidades.Proveedor;
import com.equipo15.servicio.entidades.Transaccion;
import com.equipo15.servicio.entidades.Usuario;
import com.equipo15.servicio.enumeraciones.Estado;
import com.equipo15.servicio.excepciones.MiException;
import com.equipo15.servicio.repositorios.ProveedorRepositorio;
import com.equipo15.servicio.repositorios.TransaccionRepositorio;
import com.equipo15.servicio.repositorios.UsuarioRepositorio;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransaccionServicio {

    @Autowired
    private TransaccionRepositorio transaccionRepositorio;
    @Autowired
    private ProveedorRepositorio proveedorRepositorio;
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Transactional
    public void registrar(String comentario, Double calificacion, Double presupuesto, String idProveedor,
            String idUsuario) throws MiException {

        validar(comentario, calificacion, presupuesto, idProveedor, idUsuario);

        Proveedor proveedor = proveedorRepositorio.findById(idProveedor).get();
        Usuario usuario = usuarioRepositorio.findById(idUsuario).get();

        Transaccion transaccion = new Transaccion();

        transaccion.setCalificacion(calificacion);
        transaccion.setComentario(comentario);
        transaccion.setPresupuesto(presupuesto);
        transaccion.setEstado(Estado.ACEPTADO);
        transaccion.setProveedor(proveedor);
        transaccion.setUsuario(usuario);

        transaccionRepositorio.save(transaccion);
    }

    public List<Transaccion> listarTransacciones() {
        List<Transaccion> transacciones = new ArrayList<>();
        transacciones = transaccionRepositorio.findAll();
        return transacciones;
    }

    public Transaccion buscarTransaccionPorId(String id) {
        Optional<Transaccion> respuesta = transaccionRepositorio.findById(id);
        if (respuesta.isPresent()) {
            return respuesta.get();
        } else {
            return null;
        }
    }

    public Integer contarTransaccionesPorUsuario(String id) {
        return transaccionRepositorio.contarTransaccionesPorUsuario(id);
    }

    public Integer contarTransaccionesPorProveedor(String id) {
        return transaccionRepositorio.contarTransaccionesPorProveedor(id);
    }

    public void modificar(String id, String comentario, Double calificacion, Double presupuesto, String idProveedor,
            String idUsuario) throws MiException {

        validar(comentario, calificacion, presupuesto, idProveedor, idUsuario);

        Optional<Transaccion> respuestaTransaccion = transaccionRepositorio.findById(id);
        Optional<Proveedor> respuestaProveedor = proveedorRepositorio.findById(idProveedor);
        Optional<Usuario> respuestaUsuario = usuarioRepositorio.findById(idUsuario);

        Proveedor proveedor = new Proveedor();
        Usuario usuario = new Usuario();

        if (respuestaProveedor.isPresent()) {
            proveedor = respuestaProveedor.get();
        }

        if (respuestaUsuario.isPresent()) {
            usuario = respuestaUsuario.get();
        }

        if (respuestaTransaccion.isPresent()) {
            Transaccion transaccion = respuestaTransaccion.get();

            transaccion.setCalificacion(calificacion);
            transaccion.setComentario(comentario);
            transaccion.setPresupuesto(presupuesto);
            // transaccion.setEstado(Estado.ACEPTADO);
            transaccion.setProveedor(proveedor);
            transaccion.setUsuario(usuario);

            transaccionRepositorio.save(transaccion);
        }
    }

    @Transactional
    public void iniciarTransaccion(String idProveedor, String idUsuario, Double presupuesto) throws MiException {
        Optional<Proveedor> respuestaProveedor = proveedorRepositorio.findById(idProveedor);
        Optional<Usuario> respuestaUsuario = usuarioRepositorio.findById(idUsuario);

        if (respuestaProveedor.isPresent() && respuestaUsuario.isPresent()) {
            Proveedor proveedor = respuestaProveedor.get();
            Usuario usuario = respuestaUsuario.get();

            Transaccion transaccion = new Transaccion();

            transaccion.setPresupuesto(presupuesto);
            transaccion.setEstado(Estado.PENDIENTE);
            transaccion.setProveedor(proveedor);
            transaccion.setUsuario(usuario);

            transaccionRepositorio.save(transaccion);
        }
    }

    @Transactional
    public void aceptarTransaccion(String idTransaccion) throws MiException {
        Optional<Transaccion> respuestaTransaccion = transaccionRepositorio.findById(idTransaccion);

        if (respuestaTransaccion.isPresent()) {
            Transaccion transaccion = respuestaTransaccion.get();

            transaccion.setEstado(Estado.ACEPTADO);

            transaccionRepositorio.save(transaccion);
        }
    }

    @Transactional
    public void cancelarTransaccion(String idTransaccion) throws MiException {
        Optional<Transaccion> respuestaTransaccion = transaccionRepositorio.findById(idTransaccion);

        if (respuestaTransaccion.isPresent()) {
            Transaccion transaccion = respuestaTransaccion.get();

            transaccion.setEstado(Estado.CANCELADO);

            transaccionRepositorio.save(transaccion);
        }
    }

    @Transactional
    public void finalizarTransaccion(String idTransaccion) throws MiException {
        Optional<Transaccion> respuestaTransaccion = transaccionRepositorio.findById(idTransaccion);

        if (respuestaTransaccion.isPresent()) {
            Transaccion transaccion = respuestaTransaccion.get();

            transaccion.setEstado(Estado.FINALIZADO);

            transaccionRepositorio.save(transaccion);
        }
    }

    @Transactional
    public void comentarTransaccion(String idTransaccion, String comentario, Double calificacion) throws MiException {
        Optional<Transaccion> respuestaTransaccion = transaccionRepositorio.findById(idTransaccion);

        if (respuestaTransaccion.isPresent()) {
            Transaccion transaccion = respuestaTransaccion.get();

            transaccion.setComentario(comentario);
            transaccion.setCalificacion(calificacion);

            transaccionRepositorio.save(transaccion);
        }
    }

    public List<Transaccion> listarTransaccionesPorProveedor(String idProveedor) {
        List<Transaccion> transacciones = new ArrayList<>();
        transacciones = transaccionRepositorio.listarTransaccionesPorProveedor(idProveedor);
        return transacciones;
    }

    public List<Transaccion> listarTransaccionesPorUsuario(String idUsuario) {
        List<Transaccion> transacciones = new ArrayList<>();
        transacciones = transaccionRepositorio.listarTransaccionesPorUsuario(idUsuario);
        return transacciones;
    }

    public void validar(String comentario, Double calificacion, Double presupuesto, String idProveedor,
            String idUsuario) throws MiException {

        if (comentario == null || comentario.trim().isEmpty()) {
            throw new MiException("El comentario no puede ser nulo o estar vacío");
        }

        if (calificacion == null || calificacion < 0 || calificacion > 5) {
            throw new MiException("La calificación no puede ser nula o menor a cero o mayor a 5");
        }

        if (presupuesto == null || presupuesto < 0) {
            throw new MiException("El Presupuesto no puede ser menor a cero o nulo");
        }

        if (idProveedor == null || idProveedor.trim().isEmpty()) {
            throw new MiException("El proveedor no puede ser nulo o estar vació");
        }

        if (idUsuario == null || idUsuario.trim().isEmpty()) {
            throw new MiException("El residente no puede ser nulo o estar vació");
        }
    }

}
