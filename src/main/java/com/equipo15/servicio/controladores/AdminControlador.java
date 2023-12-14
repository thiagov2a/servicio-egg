package com.equipo15.servicio.controladores;

import com.equipo15.servicio.entidades.Usuario;
import com.equipo15.servicio.enumeraciones.Rol;
import com.equipo15.servicio.repositorios.UsuarioRepositorio;
import com.equipo15.servicio.servicios.ServicioServicio;
import com.equipo15.servicio.servicios.UsuarioServicio;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminControlador {

    @Autowired
    private UsuarioServicio usuarioServicio;
    
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;
    
    @Autowired
    private ServicioServicio servicioServicio;

    @GetMapping("/dashboard")
    public String panelAdministrativo() {
        return "panel.html";
    }

    @GetMapping("/usuarios")
    public String listar(ModelMap modelo) {
        List<Usuario> usuarios = usuarioServicio.listarUsuarios();
        modelo.addAttribute("usuarios", usuarios);
        return "usuario_list.html";
    }

    @GetMapping("/hacerAdmin/{id}")
    public String cambiarRol(@PathVariable String id) {
        Rol rolU = null;
        Rol rolP = null;
        Optional<Usuario> respuesta = usuarioRepositorio.findById(id);
        Usuario usuario = respuesta.get();
        if (usuario.getRol() == Rol.USER) {
            rolU = usuario.getRol();
        } else {
            rolP = usuario.getRol();
        }

        usuarioServicio.hacerAdmin(id);

        if (rolU == Rol.USER) {
            return "redirect:/admin/usuarios";
        } else if (rolP == Rol.PROVEEDOR) {
            return "redirect:/proveedor/lista";
        }
        return null;
    }

    @GetMapping("/cambiarEstado/{id}")
    public String cambiarEstado(@PathVariable String id) {
        usuarioServicio.cambiarEstado(id);
        Optional<Usuario> respuesta = usuarioRepositorio.findById(id);
        Usuario usuario = respuesta.get();
            if (usuario.getRol() == Rol.USER) {
                return "redirect:/admin/usuarios";
            } else {
                return "redirect:/proveedor/lista";
            }    
    }
    
        @GetMapping("/cambiarEstadoServicio/{id}")
    public String cambiarEstadoServicio(@PathVariable String id) {
        servicioServicio.cambiarEstadoServicio(id);
        return "redirect:/servicio/lista";
    }

}
