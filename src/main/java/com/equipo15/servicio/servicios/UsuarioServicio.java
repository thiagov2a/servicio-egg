package com.equipo15.servicio.servicios;

import com.equipo15.servicio.entidades.Imagen;
import com.equipo15.servicio.entidades.Proveedor;
import com.equipo15.servicio.entidades.Usuario;
import com.equipo15.servicio.enumeraciones.Barrio;
import com.equipo15.servicio.enumeraciones.Rol;
import com.equipo15.servicio.excepciones.MiException;
import com.equipo15.servicio.repositorios.ProveedorRepositorio;
import com.equipo15.servicio.repositorios.UsuarioRepositorio;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
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
    private ProveedorRepositorio proveedorRepositorio;
    @Autowired
    private ImagenServicio imagenServicio;

    @Transactional
    public void registrar(String dni, String nombre, String email, String password, String password2, Barrio barrio,
            MultipartFile archivo) throws MiException {

        validar(dni, nombre, email, password, password2, barrio);
        validarExistencia(email);

        Usuario usuario = new Usuario();

        usuario.setDni(dni);
        usuario.setNombre(nombre);
        usuario.setEmail(email);
        usuario.setPassword(new BCryptPasswordEncoder().encode(password));
        usuario.setBarrio(barrio);
        usuario.setAlta(true);

        // Si el repositorio de usuarios está vacío, se establece el rol como ADMIN
        if (usuarioRepositorio.count() == 0) {
            usuario.setRol(Rol.ADMIN);
        } else {
            usuario.setRol(Rol.USER);
        }

        Imagen imagen = imagenServicio.guardar(archivo, "/src/main/resources/static/img/user_default.png");
        usuario.setImagen(imagen);

        usuarioRepositorio.save(usuario);
    }

    public List<Usuario> listarUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        usuarios = usuarioRepositorio.listarUsuarios();
        return usuarios;
    }

    @Transactional
    public void modificar(String id, String dni, String nombre, String email, String password, String password2,
            Barrio barrio, MultipartFile archivo) throws MiException {

        validar(dni, nombre, email, password, password2, barrio);

        Optional<Usuario> respuestaUsuario = usuarioRepositorio.findById(id);

        if (respuestaUsuario.isPresent()) {
            Usuario usuario = respuestaUsuario.get();

            usuario.setDni(dni);
            usuario.setNombre(nombre);
            usuario.setEmail(email);
            usuario.setPassword(new BCryptPasswordEncoder().encode(password));
            // Cuando se modifica el usuario no se debe cambiar el rol
            // usuario.setRol(Rol.USER);
            usuario.setBarrio(barrio);

            String idImagen = null;
            if (usuario.getImagen() != null) {
                idImagen = usuario.getImagen().getId();
            }

            Imagen imagen = imagenServicio.actualizar(archivo, idImagen,
                    "/src/main/resources/static/img/user_default.png");
            usuario.setImagen(imagen);

            usuarioRepositorio.save(usuario);
        }
    }

    @Transactional
    public void cambiarEstado(String id) throws MiException {
        Optional<Usuario> respuesta = usuarioRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Usuario usuario = respuesta.get();
            if (usuario.getRol() == Rol.PROVEEDOR) {
                Proveedor proveedor = usuario.getProveedor();
                proveedor.setAlta(!proveedor.getAlta());
                proveedorRepositorio.save(proveedor);
                usuario.setProveedor(proveedor);
            }
            usuario.setAlta(!usuario.getAlta());
            usuarioRepositorio.save(usuario);
        }
    }

    @Transactional
    public void cambiarRol(String id) throws MiException {
        Optional<Usuario> respuesta = usuarioRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Usuario usuario = respuesta.get();
            if (usuario.getRol() == Rol.USER) {
                usuario.setRol(Rol.PROVEEDOR);
            } else if (usuario.getRol() == Rol.PROVEEDOR) {
                usuario.setRol(Rol.USER);
            }
        }
    }

    @Transactional
    public void hacerAdmin(String id) throws MiException {
        Optional<Usuario> respuesta = usuarioRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Usuario usuario = respuesta.get();
            if (usuario.getRol() == Rol.PROVEEDOR) {
                Proveedor proveedor = usuario.getProveedor();
                proveedor.setAlta(false);
                proveedorRepositorio.save(proveedor);
                usuario.setProveedor(proveedor);
            }
            usuario.setRol(Rol.ADMIN);
            usuarioRepositorio.save(usuario);
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

    public void validar(String dni, String nombre, String email, String password, String password2, Barrio barrio)
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
            if (!usuario.getAlta()) {
                throw new DisabledException("El usuario está dado de baja");
            }

            if (usuario.getRol() == Rol.PROVEEDOR) {
                Proveedor proveedor = usuario.getProveedor();
                if (!proveedor.getAlta()) {
                    throw new DisabledException("El proveedor está dado de baja");
                }
            }

            List<GrantedAuthority> permisos = new ArrayList<>();
            GrantedAuthority p = new SimpleGrantedAuthority("ROLE_" + usuario.getRol().toString());
            permisos.add(p);

            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            HttpSession session = attr.getRequest().getSession(true);
            session.setAttribute("usuariosession", usuario);

            return new User(usuario.getEmail(), usuario.getPassword(), permisos);
        } else {
            throw new UsernameNotFoundException("Usuario no encontrado");
        }
    }

}
