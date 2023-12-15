package com.equipo15.servicio.controladores;

import com.equipo15.servicio.entidades.Servicio;
import com.equipo15.servicio.excepciones.MiException;
import com.equipo15.servicio.servicios.ServicioServicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequestMapping("/servicio")
public class ServicioControlador {

    @Autowired
    private ServicioServicio servicioServicio;

    @GetMapping("/lista")
    public String listar(ModelMap modelo) {
        List<Servicio> servicios = servicioServicio.listarServicios();
        modelo.addAttribute("servicios", servicios);
        return "servicio_list.html";
    }

    @GetMapping("/registrar")
    public String registrar() {
        return "servicio_form.html";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam(required = false) String nombre,
            @RequestParam(required = false) String descripcion, @RequestParam(required = false) MultipartFile archivo,
            ModelMap modelo) {
        try {
            servicioServicio.registrar(nombre, descripcion, archivo);

            List<Servicio> servicios = servicioServicio.listarServicios();
            modelo.addAttribute("servicios", servicios);
            modelo.addAttribute("exito", "El servicio fue cargado correctamente");
            return "redirect:/servicio/lista";
        } catch (MiException e) {
            modelo.addAttribute("error", e.getMessage());

            modelo.addAttribute("nombre", nombre);
            modelo.addAttribute("descripcion", descripcion);
            return "servicio_form.html";
        }
    }

    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, ModelMap modelo) throws MiException {
        Servicio servicio = servicioServicio.buscarServicioPorId(id);
        List<Servicio> servicios = servicioServicio.listarServicios();
        modelo.addAttribute("servicio", servicio);
        modelo.addAttribute("servicios", servicios);
        return "servicio_modificar.html";
    }

    @PostMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, @RequestParam(required = false) String nombre,
            @RequestParam(required = false) String descripcion, @RequestParam(required = false) MultipartFile archivo,
            ModelMap modelo) {
        try {
            servicioServicio.modificar(id, nombre, descripcion, archivo);

            List<Servicio> servicios = servicioServicio.listarServicios();
            modelo.addAttribute("servicios", servicios);
            modelo.addAttribute("exito", "Servicio actualizado correctamente");
            return "redirect:/servicio/lista";
        } catch (MiException e) {
            modelo.addAttribute("error", e.getMessage());

            modelo.addAttribute("nombre", nombre);
            modelo.addAttribute("descripcion", descripcion);

            Servicio servicio = servicioServicio.buscarServicioPorId(id);
            List<Servicio> servicios = servicioServicio.listarServicios();
            modelo.addAttribute("servicio", servicio);
            modelo.addAttribute("servicios", servicios);
            return "servicio_list.html";
        }
    }

    @GetMapping("/cambiarEstado/{id}")
    public String cambiarEstado(@PathVariable String id, ModelMap modelo) {
        try {
            servicioServicio.cambiarEstado(id);

            List<Servicio> servicios = servicioServicio.listarServicios();
            modelo.addAttribute("servicios", servicios);
            modelo.addAttribute("exito", "El estado del servicio fue cambiado correctamente");
            return "redirect:/servicio/lista";
        } catch (MiException e) {
            modelo.addAttribute("error", e.getMessage());

            List<Servicio> servicios = servicioServicio.listarServicios();
            modelo.addAttribute("servicios", servicios);
            return "redirect:/servicio/lista";
        }
    }
}
