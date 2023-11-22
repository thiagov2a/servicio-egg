
package com.equipo15.servicio.servicios;

import com.equipo15.servicio.entidades.Imagen;
import com.equipo15.servicio.entidades.Usuario;
import com.equipo15.servicio.enumeraciones.Rol;
import com.equipo15.servicio.excepciones.MiException;
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

/**
 *
 * @author alviz
 */
@Service
public class UsuarioServicio implements UserDetailsService {
    
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;
    
    @Autowired
    private ImagenServicio imagenServicio;
    
    @Transactional
    public void registrar(MultipartFile archivo, String nombre, String email, String password, String password2) throws MiException {
    
        validar(nombre, email, password, password2);
        Usuario usuario = new Usuario();
        
        usuario.setNombre(nombre);
        usuario.setEmail(email);
        usuario.setPassword(new BCryptPasswordEncoder().encode(password) );
        usuario.setRol(Rol.USUARIO);
        
        Imagen imagen = imagenServicio.guardar(archivo);
        
        usuario.setImagen(imagen);
        
        usuarioRepositorio.save(usuario);
    }

        @Transactional(readOnly=true)
        public List<Usuario> listarUsuarios(){
    
        List<Usuario> usuarios = new ArrayList();
        
        usuarios = usuarioRepositorio.findAll();
        
        return usuarios;
    }
    
    public Usuario getOne(String id){
        return usuarioRepositorio.getOne(id);
    }
    
    @Transactional
    public void actualizar(MultipartFile archivo, String idUsuario, String nombre, String email, String rol, String password, String password2) throws MiException {
    
        validar(nombre, email, password, password2);
        Optional<Usuario> respuesta = usuarioRepositorio.findById(idUsuario);
        if (respuesta.isPresent()){
        
        Usuario usuario = respuesta.get();
        usuario.setNombre(nombre);
        usuario.setEmail(email);
        usuario.setPassword(new BCryptPasswordEncoder().encode(password) );
        //usuario.setRol(respuesta.get().getRol());
        
        String idImagen = null;
        if (usuario.getImagen() != null){
            idImagen = usuario.getImagen().getId();
        }
        
        
        Imagen imagen = imagenServicio.actualizar(archivo, idImagen);
       
        usuario.setImagen(imagen);
        
        usuarioRepositorio.save(usuario);
        }
    }
    
    @Transactional
    public void cambiarRol(String id) {
        Optional<Usuario> respuesta = usuarioRepositorio.findById(id);
        
        if(respuesta.isPresent()) {
    		
    		Usuario usuario = respuesta.get();
    		
    		if(usuario.getRol().equals(Rol.USUARIO)) {
    			
    		usuario.setRol(Rol.ADMIN);
    		
    		}else if(usuario.getRol().equals(Rol.ADMIN)) {
    			usuario.setRol(Rol.USUARIO);
    		}
    	}    
    }
    
    private void validar(String nombre, String email, String password, String password2) throws MiException {
        
        if (nombre.isEmpty() || nombre == null) {
            throw new MiException("el nombre no puede ser nulo o estar vacio");
        }
        
        if(email.isEmpty() || email == null) {
            throw new MiException("el Email no puede ser nulo o estar vacio");
        }
        
        if(password.isEmpty() || password == null || password.length()<= 5) {
            throw new MiException("la Contraseña no puede ser nula o estar vacia, y debe tener más de 5 caracteres");
        }
        
        if(!password.equals(password2)) {
            throw new MiException("las Contraseñas deben ser iguales");
        }
    
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        
        Usuario usuario = usuarioRepositorio.buscarPorEmail(email);
        if (usuario != null) {
            List<GrantedAuthority> permisos = new ArrayList();
            GrantedAuthority p = new SimpleGrantedAuthority("ROLE_"+ usuario.getRol().toString());
            permisos.add(p);
            
            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            HttpSession session = attr.getRequest().getSession(true);
            session.setAttribute("usuariosession", usuario);
            
            return new User(usuario.getEmail(), usuario.getPassword(),permisos);
        }else {
            return null;
        }
        
    }
    
        
}
