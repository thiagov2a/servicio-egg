package com.equipo15.servicio.controladores;

import com.equipo15.servicio.entidades.Proveedor;
import com.equipo15.servicio.entidades.Transaccion;
import com.equipo15.servicio.entidades.Usuario;
import com.equipo15.servicio.excepciones.MiException;
import com.equipo15.servicio.servicios.ProveedorServicio;
import com.equipo15.servicio.servicios.TransaccionServicio;
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
@RequestMapping("/transaccion")
public class TransaccionControlador {

    @Autowired
    private TransaccionServicio transaccionServicio;
    @Autowired
    private ProveedorServicio proveedorServicio;
    @Autowired
    private UsuarioServicio usuarioServicio;

    @GetMapping("/lista")
    public String listar(ModelMap modelo, HttpSession session) {
        List<Transaccion> transacciones = obtenerTransaccionesPorRol(session);
        modelo.addAttribute("transacciones", transacciones);
        return "transaccion_list.html";
    }

    @GetMapping("/presupuestar/{idProveedor}")
    public String presupuestar(@PathVariable String idProveedor, ModelMap modelo, HttpSession session) {

        Proveedor proveedor = proveedorServicio.buscarProveedorPorId(idProveedor);
        List<Transaccion> transacciones = transaccionServicio
                .listarTransaccionesPorProveedor(proveedor.getUsuario().getId());
        Usuario usuario = obtenerUsuarioDesdeSession(session);

        modelo.addAttribute("proveedor", proveedor);
        modelo.addAttribute("usuario", usuario);
        modelo.addAttribute("presupuesto", null);
        modelo.addAttribute("transacciones", transacciones);
        return "transaccion_form.html";
    }

    @PostMapping("/presupuesto/{idProveedor}")
    public String presupuesto(@PathVariable String idProveedor, Integer horas, ModelMap modelo, HttpSession session) {
        Proveedor proveedor = proveedorServicio.buscarProveedorPorId(idProveedor);
        Usuario usuario = obtenerUsuarioDesdeSession(session);
        // ! Validar que se ingrese un integer/double en el argumento horas
        Double presupuesto = horas * proveedor.getPrecioPorHora();

        modelo.addAttribute("proveedor", proveedor);
        modelo.addAttribute("usuario", usuario);
        modelo.addAttribute("presupuesto", presupuesto);
        return "transaccion_form.html";
    }

    @PostMapping("/registro/{idProveedor}")
    public String registro(@PathVariable String idProveedor, @RequestParam Double presupuesto,
            ModelMap modelo, HttpSession session) {
        try {
            // ! Pasar id del usuario desde "transaccion_form.html" con un input hidden
            Usuario usuario = obtenerUsuarioDesdeSession(session);
            String idUsuario = usuario.getId();

            transaccionServicio.iniciarTransaccion(idProveedor, idUsuario, presupuesto);

            modelo.addAttribute("exito", "Se ha iniciado una solicitud se servicio correctamente");
            return "index.html";
        } catch (MiException ex) {
            modelo.addAttribute("error", ex.getMessage());
            return "transaccion_form.html";
        }
    }

    @GetMapping("/aceptar/{idTransaccion}")
    public String aceptarTransaccion(@PathVariable String idTransaccion, ModelMap modelo, HttpSession session) {
        try {
            transaccionServicio.aceptarTransaccion(idTransaccion);

            // * Generar método privado para no repetir codigo
            List<Transaccion> transacciones = obtenerTransaccionesPorRol(session);
            Usuario usuario = obtenerUsuarioDesdeSession(session);

            modelo.addAttribute("transacciones", transacciones);
            modelo.addAttribute("usuario", usuario);
            // *
            return "redirect:/transaccion/lista";
        } catch (MiException e) {
            modelo.addAttribute("error", e.getMessage());

            List<Transaccion> transacciones = obtenerTransaccionesPorRol(session);
            Usuario usuario = obtenerUsuarioDesdeSession(session);

            modelo.addAttribute("transacciones", transacciones);
            modelo.addAttribute("usuario", usuario);
            return "redirect:/transaccion/lista";
        }
    }

    @GetMapping("/cancelar/{idTransaccion}")
    public String cancelarTransaccion(@PathVariable String idTransaccion, ModelMap modelo, HttpSession session) {
        try {
            transaccionServicio.cancelarTransaccion(idTransaccion);

            List<Transaccion> transacciones = obtenerTransaccionesPorRol(session);
            Usuario usuario = obtenerUsuarioDesdeSession(session);

            modelo.addAttribute("transacciones", transacciones);
            modelo.addAttribute("usuario", usuario);
            return "redirect:/transaccion/lista";
        } catch (MiException e) {
            modelo.addAttribute("error", e.getMessage());

            List<Transaccion> transacciones = obtenerTransaccionesPorRol(session);
            Usuario usuario = obtenerUsuarioDesdeSession(session);

            modelo.addAttribute("transacciones", transacciones);
            modelo.addAttribute("usuario", usuario);
            return "redirect:/transaccion/lista";
        }
    }

    @GetMapping("/finalizar/{idTransaccion}")
    public String finalizarTransaccion(@PathVariable String idTransaccion, ModelMap modelo, HttpSession session) {
        try {
            transaccionServicio.finalizarTransaccion(idTransaccion);

            List<Transaccion> transacciones = obtenerTransaccionesPorRol(session);
            Usuario usuario = obtenerUsuarioDesdeSession(session);

            modelo.addAttribute("transacciones", transacciones);
            modelo.addAttribute("usuario", usuario);
            return "redirect:/transaccion/lista";
        } catch (MiException e) {
            modelo.addAttribute("error", e.getMessage());

            List<Transaccion> transacciones = obtenerTransaccionesPorRol(session);
            Usuario usuario = obtenerUsuarioDesdeSession(session);

            modelo.addAttribute("transacciones", transacciones);
            modelo.addAttribute("usuario", usuario);
            return "redirect:/transaccion/lista";
        }
    }

    @GetMapping("/calificar/{idTransaccion}")
    public String calificarTransaccion(@PathVariable String idTransaccion, ModelMap modelo, HttpSession session) {
        Transaccion transaccion = transaccionServicio.buscarTransaccionPorId(idTransaccion);
        Usuario usuario = obtenerUsuarioDesdeSession(session);

        modelo.addAttribute("transaccion", transaccion);
        modelo.addAttribute("usuario", usuario);
        return "calificar.html";
    }

    @PostMapping("/calificar/{idTransaccion}")
    public String calificarTransaccion(@PathVariable String idTransaccion,
            @RequestParam(required = false) String comentario, @RequestParam(required = false) Double calificacion,
            ModelMap modelo, HttpSession session) {
        try {
            // ! Agregar validación de comentario y calificación en comentarTransaccion()
            transaccionServicio.calificarTransaccion(idTransaccion, comentario, calificacion);
            proveedorServicio.actualizarCalificacion(idTransaccion);

            // ! Manejar las alertas en todos los métodos
            modelo.addAttribute("exito", "Se ha calificado la transacción correctamente");
            return "redirect:/transaccion/lista";
        } catch (MiException e) {
            modelo.addAttribute("error", e.getMessage());

            modelo.addAttribute("comentario", comentario);
            modelo.addAttribute("calificacion", calificacion);
            return "calificar.html";
        }
    }

    @GetMapping("/censurar/{id}")
    public String censurarComentario(@PathVariable String idTransaccion, ModelMap modelo, HttpSession session) {
        try {
            transaccionServicio.censurarComentario(idTransaccion);

            List<Transaccion> transacciones = obtenerTransaccionesPorRol(session);
            Usuario usuario = obtenerUsuarioDesdeSession(session);

            modelo.addAttribute("transacciones", transacciones);
            modelo.addAttribute("usuario", usuario);
            return "redirect:/transaccion/lista";
        } catch (MiException e) {
            modelo.addAttribute("error", e.getMessage());

            List<Transaccion> transacciones = obtenerTransaccionesPorRol(session);
            Usuario usuario = obtenerUsuarioDesdeSession(session);

            modelo.addAttribute("transacciones", transacciones);
            modelo.addAttribute("usuario", usuario);
            return "redirect:/transaccion/lista";
        }
    }

    private List<Transaccion> obtenerTransaccionesPorRol(HttpSession session) {
        Usuario usuario = obtenerUsuarioDesdeSession(session);

        if (usuario != null) {
            String rolDescripcion = usuario.getRol().getDescripcion();

            if (rolDescripcion.equals("Usuario")) {
                return transaccionServicio.listarTransaccionesPorUsuario(usuario.getId());
            } else if (rolDescripcion.equals("Proveedor")) {
                return transaccionServicio.listarTransaccionesPorProveedor(usuario.getId());
            } else {
                return transaccionServicio.listarTransacciones();
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
