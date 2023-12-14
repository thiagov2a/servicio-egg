package com.equipo15.servicio.controladores;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import com.equipo15.servicio.entidades.Servicio;
import com.equipo15.servicio.entidades.Transaccion;
import com.equipo15.servicio.entidades.Usuario;
import com.equipo15.servicio.enumeraciones.Barrio;
import com.equipo15.servicio.enumeraciones.Rol;
import com.equipo15.servicio.excepciones.MiException;
import com.equipo15.servicio.servicios.ProveedorServicio;
import com.equipo15.servicio.servicios.ServicioServicio;
import com.equipo15.servicio.servicios.TransaccionServicio;
import com.equipo15.servicio.servicios.UsuarioServicio;

import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;

@Controller
@RequestMapping("/")
public class PortalControlador {

    @Autowired
    private UsuarioServicio usuarioServicio;
    @Autowired
    private ProveedorServicio proveedorServicio;
    @Autowired
    private ServicioServicio servicioServicio;
    @Autowired
    private TransaccionServicio transaccionServicio;

    @GetMapping("/")
    public String index(HttpSession session, ModelMap modelo) {
        Usuario usuario = obtenerUsuarioDesdeSession(session);

        if (usuario != null && usuario.getRol() == Rol.ADMIN) {
            return "redirect:/admin/dashboard";
        }

        List<Transaccion> transacciones = transaccionServicio.listarTransacciones();
        Integer notificaciones = obtenerNotificaciones(session);
        
        List<Transaccion> transaccionUsuario = new ArrayList();
        
        if (usuario != null && usuario.getRol() == Rol.USER) {
            transaccionUsuario = transaccionServicio.listarTransaccionesPorUsuario(usuario.getId());
            
        }
        
        if (usuario != null && usuario.getRol() == Rol.PROVEEDOR) {
            transaccionUsuario = transaccionServicio.listarTransaccionesPorProveedor(usuario.getId());
            
        }
        
        List<Servicio> servicios = servicioServicio.listarServicioPorAlta(Boolean.TRUE);
        
                        
        modelo.addAttribute("transacciones", transacciones);
        modelo.addAttribute("servicios", servicios);
        modelo.addAttribute("notificaciones", notificaciones);
        modelo.addAttribute("transaccionUsuario", transaccionUsuario);
        return "index.html";
    }

    @GetMapping("/registrar")
    public String registrar(ModelMap modelo) {
        List<Servicio> servicios = servicioServicio.listarServicioPorAlta(Boolean.TRUE);
        modelo.addAttribute("servicios", servicios);
        modelo.addAttribute("barrios", Barrio.values());
        return "registro.html";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam(required = false) String dni, @RequestParam(required = false) String nombre,
            @RequestParam(required = false) String email, @RequestParam(required = false) String password,
            @RequestParam String password2, @RequestParam(required = false) Barrio barrio,
            @RequestParam(required = false) MultipartFile archivo,
            @RequestParam(required = false) boolean esProveedor,
            @RequestParam(required = false) String contacto,
            @RequestParam(required = false) String descripcion,
            @RequestParam(required = false) Double precioPorHora,
            @RequestParam(required = false) Double calificacion,
            @RequestParam(required = false) String idServicio,
            ModelMap modelo) {
        try {
            if (esProveedor) {
                proveedorServicio.registrar(dni, nombre, email, password, password2, barrio, archivo, contacto,
                        descripcion, precioPorHora, calificacion, idServicio);
            } else {
                usuarioServicio.registrar(dni, nombre, email, password, password2, barrio, archivo);
            }

            modelo.addAttribute("exito", "Te has registrado correctamente");
            return "index.html";
        } catch (MiException e) {
            modelo.addAttribute("error", e.getMessage());

            List<Servicio> servicios = servicioServicio.listarServicios();
            modelo.addAttribute("dni", dni);
            modelo.addAttribute("nombre", nombre);
            modelo.addAttribute("email", email);
            modelo.addAttribute("servicios", servicios);
            modelo.addAttribute("barrios", Barrio.values());
            modelo.addAttribute("contacto", contacto);
            modelo.addAttribute("precioPorHora", precioPorHora);
            modelo.addAttribute("idServicio", idServicio);
            modelo.addAttribute("descripcion", descripcion);
            modelo.addAttribute("archivo", archivo);
            return "registro.html";
        }
    }

    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error, ModelMap modelo) {
        if (error != null) {
            modelo.addAttribute("error", "Usuario o contraseña incorrectos");
        }
        return "login.html";
    }

    @GetMapping("/perfil")
    public String perfil(ModelMap modelo, HttpSession session) {
        Usuario usuario = obtenerUsuarioDesdeSession(session);
        List<Servicio> servicios = servicioServicio.listarServicios();
        modelo.addAttribute("usuario", usuario);
        modelo.addAttribute("servicios", servicios);
        modelo.addAttribute("barrios", Barrio.values());
        return "usuario_modificar.html";
    }

    @PostMapping("/perfil/{id}")
    public String modificar(@PathVariable String id, @RequestParam(required = false) String dni,
            @RequestParam(required = false) String nombre, @RequestParam(required = false) String email,
            @RequestParam(required = false) String password, @RequestParam String password2,
            @RequestParam(required = false) Barrio barrio, @RequestParam(required = false) MultipartFile archivo,
            @RequestParam(required = false) String contacto,
            @RequestParam(required = false) String descripcion,
            @RequestParam(required = false) Double precioPorHora,
            @RequestParam(required = false) Double calificacion,
            @RequestParam(required = false) String idServicio,
            ModelMap modelo, HttpSession session) throws MiException {
        try {
            Usuario usuario = obtenerUsuarioDesdeSession(session);

            if (usuario.getRol() == Rol.PROVEEDOR) {
                proveedorServicio.modificar(id, dni, nombre, email, password, password2, barrio, archivo, contacto,
                        descripcion, precioPorHora, calificacion, idServicio);
            } else {
                usuarioServicio.modificar(id, dni, nombre, email, password, password2, barrio, archivo);
            }

            modelo.addAttribute("exito", "Usuario actualizado correctamente!");
            return "index.html";
        } catch (MiException ex) {
            modelo.addAttribute("error", ex.getMessage());

            Usuario usuario = obtenerUsuarioDesdeSession(session);
            List<Servicio> servicios = servicioServicio.listarServicios();
            modelo.addAttribute("usuario", usuario);
            modelo.addAttribute("servicios", servicios);
            modelo.addAttribute("barrios", Barrio.values());
            return "usuario_modificar.html";
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

    // ! Agregar a notificación atributos como por ejemplo si tiene pendientes
    private Integer obtenerNotificaciones(HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuariosession");

        if (usuario != null) {
            String id = usuario.getId();
            Usuario usuarioActualizado = usuarioServicio.buscarUsuarioPorId(id);
            if (usuarioActualizado.getProveedor() != null) {
                return transaccionServicio.contarTransaccionesPorProveedor(usuarioActualizado.getId());
            } else {
                return transaccionServicio.contarTransaccionesPorUsuario(usuarioActualizado.getId());
            }
        } else {
            return null;
        }
    }

    @GetMapping("/barrios")
    public String barrios() {

        return "barrios.html";
    }

}
