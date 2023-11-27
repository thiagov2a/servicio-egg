package com.equipo15.servicio.controladores;

import com.equipo15.servicio.entidades.Imagen;
import com.equipo15.servicio.entidades.Proveedor;
import com.equipo15.servicio.entidades.Usuario;
import com.equipo15.servicio.enumeraciones.Barrio;
import com.equipo15.servicio.enumeraciones.Rol;
import com.equipo15.servicio.excepciones.MiException;
import com.equipo15.servicio.servicios.ProveedorServicio;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/proveedor")
public class ProveedorControlador {

    @Autowired
    private ProveedorServicio proveedorServicio;

    @GetMapping("/lista")
    public String listar(ModelMap modelo) {
        List<Proveedor> proveedores = proveedorServicio.listarProveedores();
        modelo.addAttribute("proveedores", proveedores);
        return "proveedor_list.html";
    }

}

Transactional
    public void modificar(MultipartFile archivo,String id, String dni, String nombre, String email, String rol, String password, String password2,
            Barrio barrio)
            throws MiException {

        validar(dni, nombre, email, rol, password, password2, barrio);
        
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
            if (usuario.getImagen() != null){
                idImagen = usuario.getImagen().getId();
            }
        
            Imagen imagen = imagenServicio.actualizar(archivo, idImagen);
       
            usuario.setImagen(imagen);

            usuarioRepositorio.save(usuario);

        }
    }