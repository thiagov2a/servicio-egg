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
import com.equipo15.servicio.entidades.Usuario;
import com.equipo15.servicio.enumeraciones.Barrio;
import com.equipo15.servicio.enumeraciones.Rol;
import com.equipo15.servicio.excepciones.MiException;
import com.equipo15.servicio.servicios.ProveedorServicio;
import com.equipo15.servicio.servicios.ServicioServicio;
import com.equipo15.servicio.servicios.UsuarioServicio;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/")
public class PortalControlador {

    @Autowired
    private UsuarioServicio usuarioServicio;
    @Autowired
    private ProveedorServicio proveedorServicio;
    @Autowired
    private ServicioServicio servicioServicio;

    @GetMapping("/")
    public String index(HttpSession session) {

        Usuario usuario = (Usuario) session.getAttribute("usuariosession");

        if (usuario != null && usuario.getRol().toString().equals("ADMIN")) {
            return "redirect:/admin/dashboard";
        }
        return "index.html";
    }

    @GetMapping("/registrar")
    public String registrar(ModelMap modelo) {

        List<Servicio> servicios = servicioServicio.listarServicios();

        modelo.addAttribute("servicios", servicios);
        modelo.addAttribute("barrios", Barrio.values());

        return "registro.html";
    }

    @PostMapping("/registro")
    public String registrar(@RequestParam(required = false) String dni, @RequestParam(required = false) String nombre,
            @RequestParam(required = false) String email, @RequestParam(required = false) String password,
            String password2, @RequestParam(required = false) Barrio barrio, MultipartFile archivo,
            @RequestParam(required = false) boolean esProveedor,
            @RequestParam(required = false) String contacto,
            @RequestParam(required = false) String descripcion,
            @RequestParam(required = false) Integer precioPorHora,
            @RequestParam(required = false) Integer calificacion,
            @RequestParam(required = false) String idServicio,
            ModelMap modelo) {
        try {
            if (esProveedor) {
                proveedorServicio.registrar(dni, nombre, email, password, password2, barrio, archivo, contacto,
                        descripcion, precioPorHora, calificacion, idServicio);
            } else {
                usuarioServicio.registrar(dni, nombre, email, password, password2, barrio, archivo);
            }

            modelo.put("exito", "Te has registrado correctamente");

            return "index.html";
        } catch (MiException e) {
            modelo.put("error", e.getMessage());

            modelo.put("dni", dni);
            modelo.put("nombre", nombre);
            modelo.put("email", email);

            modelo.put("esProveedor", esProveedor);

            List<Servicio> servicios = servicioServicio.listarServicios();

            modelo.addAttribute("servicios", servicios);
            modelo.addAttribute("barrios", Barrio.values());

            modelo.put("contacto", contacto);
            modelo.put("descripcion", descripcion);
            modelo.put("precioPorHora", precioPorHora);
            modelo.put("idServicio", idServicio);

            modelo.put("archivo", archivo);

            return "registro.html";
        }
    }

    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error, ModelMap modelo) {
        if (error != null) {
            modelo.put("error", "Usuario o contraseña incorrectos");
        }
        return "login.html";
    }

    @GetMapping("/perfil")
    public String perfil(ModelMap modelo, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuariosession");
        modelo.put("usuario", usuario);
        return "usuario_modificar.html";
    }

    @PostMapping("/perfil/{id}")
    public String modificar(@PathVariable String id, @RequestParam String dni, @RequestParam String nombre,
            @RequestParam String email, @RequestParam String password, @RequestParam String password2,
            MultipartFile archivo,
            @RequestParam(required = false) String contacto,
            @RequestParam(required = false) String descripcion,
            @RequestParam(required = false) Integer precioPorHora,
            @RequestParam(required = false) Integer calificacion,
            ModelMap modelo) {
        try {
            // Obtener el usuario actualizado
            Usuario usuarioActualizado = usuarioServicio.buscarUsuarioPorId(id);

            // Verificar si el usuario es también un proveedor
            if (usuarioActualizado.getRol() == Rol.PROVEEDOR) {
                // Modificar datos específicos del proveedor
                proveedorServicio.modificar(id, dni, nombre, email, password, password2, contacto, descripcion,
                        precioPorHora, calificacion, id);
            } else {
                usuarioServicio.modificar(id, dni, nombre, email, password, password2, archivo);
            }

            modelo.put("exito", "Usuario actualizado correctamente!");

            return "index.html";
        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
            modelo.put("nombre", nombre);
            modelo.put("email", email);

            return "usuario_modificar.html";
        }
    }

}