package com.equipo15.servicio.controladores;

import com.equipo15.servicio.entidades.Proveedor;
import com.equipo15.servicio.entidades.Servicio;
import com.equipo15.servicio.entidades.Usuario;
import com.equipo15.servicio.excepciones.MiException;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
        List<Servicio> servicios = servicioServicio.listarServicioPorAlta(Boolean.TRUE);
        modelo.addAttribute("proveedores", proveedores);
        modelo.addAttribute("servicios", servicios);
        return "proveedor_list.html";
    }

    @GetMapping("/listaOrdenada")
    public String listarPorMenorPrecio(ModelMap modelo, HttpSession session) {
        List<Proveedor> proveedores = obtenerProveedoresPorRolOrdenadoPorMenorPrecio(session);
        List<Servicio> servicios = servicioServicio.listarServicioPorAlta(Boolean.TRUE);
        modelo.addAttribute("proveedores", proveedores);
        modelo.addAttribute("servicios", servicios);
        return "proveedor_list.html";
    }

    // TODO: Aplicar try-catch
    @PostMapping("/filtrar")
    public String filtrar(String idServicio, ModelMap modelo, HttpSession session) {
        List<Proveedor> proveedores = obtenerProveedoresPorServicio(idServicio, session);
        List<Servicio> servicios = servicioServicio.listarServicioPorAlta(Boolean.TRUE);
        modelo.addAttribute("proveedores", proveedores);
        modelo.addAttribute("servicios", servicios);
        modelo.addAttribute("idServicio", idServicio);
        return "proveedor_list.html";
    }

    @GetMapping("/filtrar2/{idServicio}")
    public String filtrar2(@PathVariable String idServicio, @RequestParam(required = false) boolean filtrado,
            ModelMap modelo, HttpSession session) {
        List<Proveedor> proveedores = obtenerProveedoresPorServicio(idServicio, session);
        List<Servicio> servicios = servicioServicio.listarServicios();
        modelo.addAttribute("idServicio", idServicio);
        modelo.addAttribute("proveedores", proveedores);
        modelo.addAttribute("servicios", servicios);
        modelo.addAttribute("filtrado", true);
        return "proveedor_list.html";
    }

    @GetMapping("/filtradaYOrdenada/{idServicio}")
    public String filtradaYOrdenada(@PathVariable String idServicio, ModelMap modelo, HttpSession session) {
        List<Proveedor> proveedores = obtenerProveedoresPorServicioOrdenadoPorMenorPrecio(session, idServicio);
        List<Servicio> servicios = servicioServicio.listarServicioPorAlta(Boolean.TRUE);
        modelo.addAttribute("proveedores", proveedores);
        modelo.addAttribute("servicios", servicios);
        modelo.addAttribute("filtrado", true);
        modelo.addAttribute("idServicio", idServicio);
        return "proveedor_list.html";
    }

    @GetMapping("/hacerAdmin/{id}")
    public String cambiarRol(@PathVariable String id, ModelMap modelo, HttpSession session) {
        try {
            usuarioServicio.hacerAdmin(id);

            List<Proveedor> proveedores = proveedorServicio.listarProveedores();
            List<Servicio> servicios = servicioServicio.listarServicioPorAlta(Boolean.TRUE);
            modelo.addAttribute("proveedores", proveedores);
            modelo.addAttribute("servicios", servicios);
            modelo.addAttribute("exito", "Usuario actualizado correctamente");
            return "redirect:/proveedor/lista";
        } catch (MiException e) {
            modelo.addAttribute("error", e.getMessage());

            List<Proveedor> proveedores = proveedorServicio.listarProveedores();
            List<Servicio> servicios = servicioServicio.listarServicioPorAlta(Boolean.TRUE);
            modelo.addAttribute("proveedores", proveedores);
            modelo.addAttribute("servicios", servicios);
            return "redirect:/proveedor/lista";
        }
    }

    @GetMapping("/cambiarEstado/{id}")
    public String cambiarEstado(@PathVariable String id, ModelMap modelo, HttpSession session) {
        try {
            usuarioServicio.cambiarEstado(id);

            List<Proveedor> proveedores = proveedorServicio.listarProveedores();
            List<Servicio> servicios = servicioServicio.listarServicioPorAlta(Boolean.TRUE);
            modelo.addAttribute("proveedores", proveedores);
            modelo.addAttribute("servicios", servicios);
            return "redirect:/proveedor/lista";
        } catch (MiException e) {
            modelo.addAttribute("error", e.getMessage());

            List<Proveedor> proveedores = proveedorServicio.listarProveedores();
            List<Servicio> servicios = servicioServicio.listarServicioPorAlta(Boolean.TRUE);
            modelo.addAttribute("proveedores", proveedores);
            modelo.addAttribute("servicios", servicios);
            return "redirect:/proveedor/lista";
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

    private List<Proveedor> obtenerProveedoresPorServicio(String idServicio, HttpSession session) {
        Usuario usuario = obtenerUsuarioDesdeSession(session);

        if (usuario != null) {
            String rolDescripcion = usuario.getRol().getDescripcion();

            if (rolDescripcion.equals("Admin")) {
                return proveedorServicio.listarProveedoresPorServicio(idServicio);
            } else {
                return proveedorServicio.listarProveedoresPorAltaPorServicio(idServicio, Boolean.TRUE);
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

    private List<Proveedor> obtenerProveedoresPorServicioOrdenadoPorMenorPrecio(HttpSession session,
            String idServicio) {
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
}
