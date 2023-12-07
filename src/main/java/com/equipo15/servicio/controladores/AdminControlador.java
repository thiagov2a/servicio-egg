package com.equipo15.servicio.controladores;

import com.equipo15.servicio.entidades.Usuario;
import com.equipo15.servicio.servicios.ServicioServicio;
import com.equipo15.servicio.servicios.UsuarioServicio;
import java.util.List;
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
        usuarioServicio.hacerAdmin(id);
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/cambiarEstado/{id}")
    public String cambiarEstado(@PathVariable String id) {
        usuarioServicio.cambiarEstado(id);
        return "redirect:/admin/dashboard";
    }
    
        @GetMapping("/cambiarEstadoServicio/{id}")
    public String cambiarEstadoServicio(@PathVariable String id) {
        servicioServicio.cambiarEstadoServicio(id);
        return "redirect:/admin/dashboard";
    }

}
