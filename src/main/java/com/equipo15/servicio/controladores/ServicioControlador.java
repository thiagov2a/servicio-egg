package com.equipo15.servicio.controladores;

import com.equipo15.servicio.entidades.Servicio;
import com.equipo15.servicio.entidades.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.equipo15.servicio.excepciones.MiException;
import com.equipo15.servicio.servicios.ServicioServicio;
import com.equipo15.servicio.servicios.UsuarioServicio;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequestMapping("/servicio")
public class ServicioControlador {

    @Autowired
    private ServicioServicio servicioServicio;

    @Autowired
    private UsuarioServicio usuarioServicio;

    @GetMapping("/registrar")
    public String registrar() {
        return "servicio_form.html";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam(required = false) String nombre,
            @RequestParam(required = false) String descripcion, ModelMap modelo) {
        try {
            servicioServicio.registrar(nombre, descripcion);
            modelo.addAttribute("exito", "El servicio fué cargado correctamente!");
            return "panel.html";
        } catch (MiException e) {
            modelo.addAttribute("error", e.getMessage());

            modelo.addAttribute("nombre", nombre);
            modelo.addAttribute("descripcion", descripcion);
            return "servicio_form.html";
        }
    }

    // TODO: Mapping lista de servicios, atributo de alta/baja, CRUD completo

    @GetMapping("/lista")
    public String listar(ModelMap modelo, HttpSession session) {
        List<Servicio> servicios = obtenerServiciosPorRol(session);
        modelo.addAttribute("servicios", servicios);
        return "servicio_list.html";
    }

    private List<Servicio> obtenerServiciosPorRol(HttpSession session) {
        Usuario usuario = obtenerUsuarioDesdeSession(session);

        if (usuario != null) {
            String rolDescripcion = usuario.getRol().getDescripcion();

            if (rolDescripcion.equals("Admin")) {
                return servicioServicio.listarServicios();
            } else {
                return servicioServicio.listarServicioPorAlta(Boolean.TRUE);
            }
        } else {
            return new ArrayList<>();
        }

    }
    
    @GetMapping("/modificarServicio/{id}")
    public String modificar(@PathVariable String id, ModelMap modelo) throws MiException {
        modelo.put("servicio", servicioServicio.buscarServicioPorId(id));
        
        List<Servicio> servicios = servicioServicio.listarServicios();
        modelo.addAttribute("servicios", servicios);
        
        return "servicio_modificar.html";
    }
    
    @PostMapping("/modificarServicio/{id}")
    public String modificar(@PathVariable String id, @RequestParam(required = false) String nombre,
            @RequestParam(required = false) String descripcion, ModelMap modelo){
        try {
            servicioServicio.modificar(id, nombre, descripcion);
            modelo.put("exito","Servicio actualizado correctamente!!");
            return "redirect:/admin/dashboard";
        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
            modelo.put("nombre", nombre);
            modelo.put("descripcion", descripcion);
            return "servicio_list.html";
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
