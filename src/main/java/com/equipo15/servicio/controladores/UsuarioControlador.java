package com.equipo15.servicio.controladores;

import com.equipo15.servicio.entidades.Usuario;
import com.equipo15.servicio.enumeraciones.Barrio;
import com.equipo15.servicio.enumeraciones.Rol;
import com.equipo15.servicio.excepciones.MiException;
import com.equipo15.servicio.servicios.UsuarioServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/usuario")
public class UsuarioControlador {

    @Autowired
    private UsuarioServicio usuarioServicio;
    
    

    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, ModelMap modelo) {
        modelo.put("usuario", usuarioServicio.getOne(id));

        List<Usuario> usuarios = usuarioServicio.listarUsuarios();

        modelo.addAttribute("usuarios", usuarios);

        return "usuario_modificar.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @PostMapping("/modificar/{id}")
    public String modificar(MultipartFile archivo, @PathVariable String id, @RequestParam String dni, @RequestParam String nombre,
            @RequestParam String email, @RequestParam String rol, @RequestParam String password, @RequestParam String password2,
            @RequestParam Barrio barrio,
            @RequestParam(required = false) String contacto,
            @RequestParam(required = false) String descripcion,
            @RequestParam(required = false) Integer precioPorHora,
            @RequestParam(required = false) Integer calificacion,
            ModelMap modelo) throws MiException {
        try {
            // Obtener el usuario actualizado
            Usuario usuarioActualizado = usuarioServicio.buscarUsuarioPorId(id);

            // Verificar si el usuario es también un proveedor
            if (usuarioActualizado.getRol() == Rol.USER) {
                usuarioServicio.modificar(archivo, id, dni, nombre, email, rol, password, password2, barrio); 
            } 

            modelo.put("exito", "Usuario actualizado correctamente!");
            return "index.html";
        } catch (MiException ex) {
            
            modelo.put("error", ex.getMessage());
            modelo.put("nombre", nombre);
            modelo.put("email", email);
            modelo.put("rol", rol);

            return "usuario_modificar.html";
        }
    }
    
    
    
    
    
    
    
    
    
    
    
//    public String modificar(MultipartFile archivo, @PathVariable String id, @RequestParam String dni, @RequestParam String nombre,
//            @RequestParam String email,  @RequestParam String rol, @RequestParam String password, @RequestParam String password2,
//            @RequestParam Barrio barrio, ModelMap modelo) throws MiException{      
//        
//        try {
//          
//            usuarioServicio.modificar(archivo, id, dni, nombre, email, rol, password, password2, barrio);
//            modelo.put("exito", "Usuario actualizado correctamente!");
//
//            return "panel.html";
//        } catch (MiException ex) {
//            modelo.put("error", ex.getMessage());
//            modelo.put("dni", dni);
//            modelo.put("nombre", nombre);
//            modelo.put("email", email);
//            modelo.put("rol", rol);
//            modelo.put("barrio", barrio);
//
//            return "usuario_modificar.html";
//        }
//    }

}
