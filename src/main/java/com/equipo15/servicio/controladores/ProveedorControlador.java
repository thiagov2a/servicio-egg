package com.equipo15.servicio.controladores;

import com.equipo15.servicio.entidades.Proveedor;
import com.equipo15.servicio.entidades.Servicio;
import com.equipo15.servicio.entidades.Usuario;
import com.equipo15.servicio.servicios.ProveedorServicio;
import com.equipo15.servicio.servicios.ServicioServicio;
import com.equipo15.servicio.servicios.UsuarioServicio;

import jakarta.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/proveedor")
public class ProveedorControlador {

    @Autowired
    private ProveedorServicio proveedorServicio;
    @Autowired
    private UsuarioServicio usuarioServicio;
    @Autowired
    private ServicioServicio servicioServicio;

    @GetMapping("/lista")
    public String listar(ModelMap modelo, HttpSession session) {
        List<Proveedor> proveedores = obtenerProveedoresPorRol(session);

        List<Servicio> servicios = servicioServicio.listarServicios();

        modelo.addAttribute("proveedores", proveedores);
        modelo.addAttribute("servicios", servicios);

        return "proveedor_list.html";
    }

    @GetMapping("/listaOrdenada")
    public String listarPorMenorPrecio(ModelMap modelo, HttpSession session) {
        List<Proveedor> proveedores = obtenerProveedoresPorRolOrdenadoPorMenorPrecio(session);

        List<Servicio> servicios = servicioServicio.listarServicios();

        modelo.addAttribute("proveedores", proveedores);
        modelo.addAttribute("servicios", servicios);

        return "proveedor_list.html";
    }

    @PostMapping("/filtrar")
    public String filtrar(ModelMap modelo, HttpSession session, String idServicio) {

        List<Proveedor> proveedores = obtenerProveedoresPorServicio(session, idServicio);

        List<Servicio> servicios = servicioServicio.listarServicios();

        modelo.addAttribute("proveedores", proveedores);
        modelo.addAttribute("servicios", servicios);

        return "proveedor_list.html";
    }

    @PostMapping("/filtradaYOrdenada")
    public String faltradaYOrdenada(ModelMap modelo, HttpSession session, String idServicio) {

        List<Proveedor> proveedores = obtenerProveedoresPorServicioOrdenadoPorMenorPrecio(session, idServicio);

        List<Servicio> servicios = servicioServicio.listarServicios();

        modelo.addAttribute("proveedores", proveedores);
        modelo.addAttribute("servicios", servicios);

        return "proveedor_list.html";
    }

    private List<Proveedor> obtenerProveedoresPorRol(HttpSession session) {
        Usuario usuario = obtenerUsuarioDesdeSession(session);

        if (usuario != null) {
            String rolDescripcion = usuario.getRol().getDescripcion();

            if (rolDescripcion.equals("Admin")) {
                return proveedorServicio.listarProveedores();
            } else {
                return proveedorServicio.listarProveedoresPorAlta(Boolean.TRUE);
            }
        } else {
            return new ArrayList<>();
        }
    }

    private List<Proveedor> obtenerProveedoresPorRolOrdenadoPorMenorPrecio(HttpSession session) {
        Usuario usuario = obtenerUsuarioDesdeSession(session);

        if (usuario != null) {
            String rolDescripcion = usuario.getRol().getDescripcion();

            if (rolDescripcion.equals("Admin")) {
                return proveedorServicio.listarProveedoresPorMenorPrecio();
            } else {
                return proveedorServicio.listarProveedoresPorAltaOrdenadoPorMenorPrecio(true);
            }
        } else {
            return new ArrayList<>();
        }
    }

    private List<Proveedor> obtenerProveedoresPorServicio(HttpSession session, String idServicio) {
        Usuario usuario = obtenerUsuarioDesdeSession(session);

        if (usuario != null) {
            String rolDescripcion = usuario.getRol().getDescripcion();

            if (rolDescripcion.equals("Admin")) {
                return proveedorServicio.listarProveedoresPorServicio(idServicio);
            } else {
                return proveedorServicio.listarProveedoresPorAltaPorServicio(true, idServicio);
            }
        } else {
            return new ArrayList<>();
        }
    }

    private List<Proveedor> obtenerProveedoresPorServicioOrdenadoPorMenorPrecio(HttpSession session, String idServicio) {
        Usuario usuario = obtenerUsuarioDesdeSession(session);

        if (usuario != null) {
            String rolDescripcion = usuario.getRol().getDescripcion();

            if (rolDescripcion.equals("Admin")) {
                return proveedorServicio.listarProveedoresPorServicioOrdenadoPorMenorPrecio(idServicio);
            } else {
                return proveedorServicio.listarProveedoresPorAltaPorServicioOrdenadoPorMenorPrecio(true, idServicio);
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
