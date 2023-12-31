package com.equipo15.servicio.controladores;

import com.equipo15.servicio.entidades.Usuario;
import com.equipo15.servicio.excepciones.MiException;
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
    public String cambiarRol(@PathVariable String id, ModelMap modelo) {
        try {
            usuarioServicio.hacerAdmin(id);

            List<Usuario> usuarios = usuarioServicio.listarUsuarios();
            modelo.addAttribute("usuarios", usuarios);

            modelo.addAttribute("exito", "Usuario actualizado correctamente");
            return "redirect:/admin/usuarios";
        } catch (MiException e) {
            modelo.addAttribute("error", e.getMessage());

            List<Usuario> usuarios = usuarioServicio.listarUsuarios();
            modelo.addAttribute("usuarios", usuarios);
            return "redirect:/admin/usuarios";
        }
    }

    @GetMapping("/cambiarEstado/{id}")
    public String cambiarEstado(@PathVariable String id, ModelMap modelo) {
        try {
            usuarioServicio.cambiarEstado(id);

            List<Usuario> usuarios = usuarioServicio.listarUsuarios();
            modelo.addAttribute("usuarios", usuarios);

            modelo.addAttribute("exito", "Usuario actualizado correctamente");
            return "redirect:/admin/usuarios";
        } catch (MiException e) {
            modelo.addAttribute("error", e.getMessage());

            List<Usuario> usuarios = usuarioServicio.listarUsuarios();
            modelo.addAttribute("usuarios", usuarios);
            return "redirect:/admin/usuarios";
        }
    }
}
