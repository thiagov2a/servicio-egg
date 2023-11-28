package com.equipo15.servicio.servicios;

import com.equipo15.servicio.entidades.Imagen;
import com.equipo15.servicio.entidades.Usuario;
import com.equipo15.servicio.enumeraciones.Barrio;
import com.equipo15.servicio.enumeraciones.Rol;
import com.equipo15.servicio.excepciones.MiException;
import com.equipo15.servicio.repositorios.ProveedorRepositorio;
import com.equipo15.servicio.repositorios.UsuarioRepositorio;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UsuarioServicio implements UserDetailsService {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;
    @Autowired
    private ImagenServicio imagenServicio;

    // Create
    @Transactional
    public void registrar(String dni, String nombre, String email, String password, String password2,
            Barrio barrio, MultipartFile archivo)
            throws MiException {

        validar(dni, nombre, email, password, password2, barrio);
        validarExistencia(email);

        Usuario usuario = new Usuario();

        usuario.setDni(dni);
        usuario.setNombre(nombre);
        usuario.setEmail(email);
        usuario.setPassword(new BCryptPasswordEncoder().encode(password));
        usuario.setRol(Rol.USER);
        usuario.setBarrio(barrio);
        usuario.setAlta(true);

        Imagen imagen = imagenServicio.guardar(archivo);
        usuario.setImagen(imagen);

        usuarioRepositorio.save(usuario);
    }

    // Read
    public List<Usuario> listarUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        usuarios = usuarioRepositorio.listarUsuarios();
        return usuarios;
    }

    // Update
    @Transactional
    public void modificar(MultipartFile archivo, String id, String dni, String nombre, String email,
            String password, String password2,
            Barrio barrio)
            throws MiException {

        validar(dni, nombre, email, password, password2, barrio);

        Optional<Usuario> respuestaUsuario = usuarioRepositorio.findById(id);

        if (respuestaUsuario.isPresent()) {
            Usuario usuario = respuestaUsuario.get();

            usuario.setDni(dni);
            usuario.setNombre(nombre);
            usuario.setEmail(email);
            usuario.setPassword(new BCryptPasswordEncoder().encode(password));
            usuario.setRol(Rol.USER);
            usuario.setBarrio(barrio);

            String idImagen = null;
            if (usuario.getImagen() != null) {
                idImagen = usuario.getImagen().getId();
            }

            Imagen imagen = imagenServicio.actualizar(archivo, idImagen);

            usuario.setImagen(imagen);

            usuarioRepositorio.save(usuario);

        }
    }

    // Delete
    @Transactional
    public void darDeBaja(String id) {
        Optional<Usuario> respuesta = usuarioRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Usuario usuario = respuesta.get();
            usuario.setAlta(false);
        }
    }

    public Usuario buscarUsuarioPorId(String id) {
        Optional<Usuario> respuesta = usuarioRepositorio.findById(id);
        if (respuesta.isPresent()) {
            return respuesta.get();
        } else {
            return null;
        }
    }

    @Transactional
    public void cambiarRol(String id) {
        Optional<Usuario> respuesta = usuarioRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Usuario usuario = respuesta.get();
            if (usuario.getRol().equals(Rol.USER)) {
                usuario.setRol(Rol.PROVEEDOR);
            } else if (usuario.getRol().equals(Rol.PROVEEDOR)) {
                usuario.setRol(Rol.USER);
            }
        }
    }

    public void validar(String dni, String nombre, String email, String password, String password2,
            Barrio barrio)
            throws MiException {

        if (dni == null || dni.trim().isEmpty()) {
            throw new MiException("El Dni no puede ser nulo o estar vacío");
        }

        if (nombre == null || nombre.trim().isEmpty()) {
            throw new MiException("El Nombre no puede ser nulo o estar vacío");
        }

        if (email == null || email.trim().isEmpty()) {
            throw new MiException("El Email no puede ser nulo o estar vacío");
        }

        if (password == null || password.trim().isEmpty() || password.trim().length() < 8) {
            throw new MiException("La Contraseña no puede estar vacía o tener menos de 8 caracteres");
        }

        if (!password2.equals(password)) {
            throw new MiException("Las Contraseñas deben coincidir");
        }

        if (barrio == null) {
            throw new MiException("El Barrio no puede ser nulo");
        }

    }

    // Query personalizada
    public void validarExistencia(String email) throws MiException {
        Usuario respuestaUsuario = usuarioRepositorio.buscarPorEmail(email.trim());
        if (respuestaUsuario != null) {
            throw new MiException("Ya existe un usuario con ese email");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepositorio.buscarPorEmail(email);

        if (usuario != null) {

            List<GrantedAuthority> permisos = new ArrayList<>();
            GrantedAuthority p = new SimpleGrantedAuthority("ROLE_" +
                    usuario.getRol().toString());

            permisos.add(p);

            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();

            HttpSession session = attr.getRequest().getSession(true);

            session.setAttribute("usuariosession", usuario);

            return new User(usuario.getEmail(), usuario.getPassword(), permisos);
        } else {
            return null;
        }
    }

}
