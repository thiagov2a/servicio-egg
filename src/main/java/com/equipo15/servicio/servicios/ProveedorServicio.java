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
import jakarta.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ProveedorServicio {

    @Autowired
    private ProveedorRepositorio proveedorRepositorio;
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;
    @Autowired
    private ServicioRepositorio servicioRepositorio;
    @Autowired
    private UsuarioServicio usuarioServicio;
    @Autowired
    private ImagenServicio imagenServicio;

    @Transactional
    public void registrar(String dni, String nombre, String email, String password, String password2,
            Barrio barrio,
            MultipartFile archivo, String contacto, String descripcion, Integer precioPorHora, Integer calificacion,
            String idServicio) throws MiException {

        usuarioServicio.validar(dni, nombre, email, password, password2, barrio);
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

        proveedor.setPrecioPorHora(precioPorHora);
        proveedor.setCalificacion(calificacion); // Inicializar la calificación a 0

        Servicio servicio = servicioRepositorio.findById(idServicio).get();
        proveedor.setServicio(servicio);
        proveedor.setDescripcion(servicio.getDescripcion());

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
    public void modificar(MultipartFile archivo, String id, String dni, String nombre, String email,
            String password, String password2,
            Barrio barrio, String contacto, String descripcion, Integer precioPorHora, Integer calificacion,
            String idServicio)
            throws MiException {

        usuarioServicio.validar(dni, nombre, email, password, password2, barrio);
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

            String idImagen = null;
            if (usuario.getImagen() != null) {
                idImagen = usuario.getImagen().getId();
            }

            Imagen imagen = imagenServicio.actualizar(archivo, idImagen);

            usuario.setImagen(imagen);

            usuarioRepositorio.save(usuario);

            proveedor.setContacto(contacto);
            proveedor.setCalificacion(calificacion);

            proveedor.setPrecioPorHora(precioPorHora);
            proveedor.setServicio(servicio);
            proveedor.setDescripcion(servicio.getDescripcion());

            proveedorRepositorio.save(proveedor);
        }
    }

    public void validar(String contacto, String descripcion, Integer precioPorHora, Integer calificacion,
            String idServicio) throws MiException {

        if (contacto == null || contacto.trim().isEmpty()) {
            throw new MiException("El contacto no puede ser nulo o estar vacío");
        }

        if (descripcion.trim().isEmpty() || descripcion == null) {
            throw new MiException("la Descripción no puede ser nula");
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
    
    
    
/*    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Proveedor proveedor = proveedorRepositorio.buscarPorEmail(email);

        if (proveedor != null) {

            List<GrantedAuthority> permisos = new ArrayList<>();
            GrantedAuthority p = new SimpleGrantedAuthority("ROLE_"
                    + proveedor.getUsuario().getRol().toString());

            permisos.add(p);

            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();

            HttpSession session = attr.getRequest().getSession(true);

            session.setAttribute("proveedorsession", proveedor);

            return new User(proveedor.getUsuario().getEmail(), proveedor.getUsuario().getPassword(), permisos);
        } else {
            return null;
        }
    }*/

}
