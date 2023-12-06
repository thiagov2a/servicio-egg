package com.equipo15.servicio.controladores;

import com.equipo15.servicio.entidades.Proveedor;
import com.equipo15.servicio.entidades.Usuario;
import com.equipo15.servicio.servicios.ProveedorServicio;
import com.equipo15.servicio.servicios.UsuarioServicio;

import jakarta.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/proveedor")
public class ProveedorControlador {

    @Autowired
    private ProveedorServicio proveedorServicio;
    @Autowired
    private UsuarioServicio usuarioServicio;

    @GetMapping("/lista")
    public String listar(ModelMap modelo, HttpSession session) {
        List<Proveedor> proveedores = obtenerProveedoresPorRol(session);
        modelo.addAttribute("proveedores", proveedores);
        return "proveedor_list.html";
    }

    private List<Proveedor> obtenerProveedoresPorRol(HttpSession session) {
        Usuario usuario = obtenerUsuarioDesdeSession(session);

        if (usuario != null) {
            String rolDescripcion = usuario.getRol().getDescripcion();

            if (rolDescripcion.equals("Admin")) {
                return proveedorServicio.listarProveedores();
            } else {
                return proveedorServicio.listarProveedoresPorAlta(true);
            }
        } else {
            return new ArrayList<>();
        }
    }

    private Usuario obtenerUsuarioDesdeSession(HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuariosession");

        if (usuario != null) {
            String id = usuario.getId();
            return usuarioServicio.buscarUsuarioPorId(id);
        } else {
            return null;
        }
    }

}
