package com.equipo15.servicio.servicios;

import com.equipo15.servicio.entidades.Imagen;
import com.equipo15.servicio.entidades.Proveedor;
import com.equipo15.servicio.entidades.Servicio;
import com.equipo15.servicio.entidades.Usuario;
import com.equipo15.servicio.enumeraciones.Barrio;
import com.equipo15.servicio.enumeraciones.Rol;
import com.equipo15.servicio.excepciones.MiException;
import com.equipo15.servicio.repositorios.ProveedorRepositorio;
import com.equipo15.servicio.repositorios.ServicioRepositorio;
import com.equipo15.servicio.repositorios.UsuarioRepositorio;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ProveedorServicio {

    @Autowired
    private ProveedorRepositorio proveedorRepositorio;
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;
    @Autowired
    private UsuarioServicio usuarioServicio;
    @Autowired
    private ImagenServicio imagenServicio;
    @Autowired
    private ServicioRepositorio servicioRepositorio;

    @Transactional
    public void registrar(String dni, String nombre, String email, String password, String password2, Barrio barrio,
            MultipartFile archivo, String contacto, String descripcion, Integer precioPorHora, Integer calificacion,
            String idServicio) throws MiException {

        usuarioServicio.validar(dni, nombre, email, password, password2);
        usuarioServicio.validarExistencia(email);

        validar(contacto, descripcion, precioPorHora, calificacion, idServicio);
        validarExistencia(contacto);

        Usuario usuario = new Usuario();

        usuario.setDni(dni);
        usuario.setNombre(nombre);
        usuario.setEmail(email);
        usuario.setPassword(new BCryptPasswordEncoder().encode(password));
        usuario.setRol(Rol.PROVEEDOR); // Establecer el rol como PROVEEDOR directamente
        usuario.setBarrio(barrio);
        usuario.setAlta(true);

        Imagen imagen = imagenServicio.guardar(archivo);
        usuario.setImagen(imagen);

        usuarioRepositorio.save(usuario);

        // Crear el proveedor asociado al usuario
        Proveedor proveedor = new Proveedor();
        proveedor.setUsuario(usuario);
        proveedor.setContacto(contacto);
        proveedor.setDescripcion(descripcion);
        proveedor.setPrecioPorHora(precioPorHora);
        proveedor.setCalificacion(calificacion); // Inicializar la calificación a 0

        Servicio servicio = servicioRepositorio.findById(idServicio).get();
        proveedor.setServicio(servicio);

        proveedorRepositorio.save(proveedor);
    }

    public List<Proveedor> listarProveedores() {
        List<Proveedor> proveedores = new ArrayList<>();
        proveedores = proveedorRepositorio.findAll();
        return proveedores;
    }

    public Proveedor buscarProveedorPorId(String id) {
        Optional<Proveedor> respuesta = proveedorRepositorio.findById(id);
        if (respuesta.isPresent()) {
            return respuesta.get();
        } else {
            return null;
        }
    }

    @Transactional
    public void modificar(String id, String dni, String nombre, String email, String password, String password2,
            String contacto, String descripcion, Integer precioPorHora, Integer calificacion, String idServicio)
            throws MiException {

        usuarioServicio.validar(dni, nombre, email, password, password2);
        validar(contacto, descripcion, precioPorHora, calificacion, idServicio);

        Optional<Proveedor> respuestaProveedor = proveedorRepositorio.findById(id);
        Optional<Servicio> respuestaServicio = servicioRepositorio.findById(idServicio);

        if (respuestaProveedor.isPresent() && respuestaServicio.isPresent()) {
            Proveedor proveedor = respuestaProveedor.get();
            Usuario usuario = proveedor.getUsuario();
            Servicio servicio = respuestaServicio.get();

            usuario.setDni(dni);
            usuario.setNombre(nombre);
            usuario.setEmail(email);
            usuario.setPassword(new BCryptPasswordEncoder().encode(password));

            usuarioRepositorio.save(usuario);

            proveedor.setContacto(contacto);
            proveedor.setCalificacion(calificacion);
            proveedor.setDescripcion(descripcion);
            proveedor.setPrecioPorHora(precioPorHora);
            proveedor.setServicio(servicio);

            proveedorRepositorio.save(proveedor);
        }
    }

    public void validar(String contacto, String descripcion, Integer precioPorHora, Integer calificacion,
            String idServicio) throws MiException {

        if (contacto == null || contacto.trim().isEmpty()) {
            throw new MiException("El contacto no puede ser nulo o estar vacío");
        }

        if (descripcion == null || descripcion.trim().isEmpty()) {
            throw new MiException("la Descripción no puede ser nula o estar vacía");
        }

        if (precioPorHora == null || precioPorHora < 0) {
            throw new MiException("el Precio por Hora no puede ser nulo o menor que cero");
        }

        if (calificacion == null || calificacion < 0 || calificacion > 5) {
            throw new MiException("la Calificación no puede ser nula o menor que cero o mayor que 5");
        }

        if (idServicio == null || idServicio.trim().isEmpty()) {
            throw new MiException("el Id Servicio no puede ser nulo o estar vacío");
        }

    }

    public void validarExistencia(String contacto) throws MiException {
        Proveedor respuestaProveedor = proveedorRepositorio.buscarPorContacto(contacto.trim());
        if (respuestaProveedor != null) {
            throw new MiException("Ya existe un proveedor con ese contacto");
        }
    }

}
