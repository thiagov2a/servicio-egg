package com.equipo15.servicio.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.equipo15.servicio.excepciones.MiException;
import com.equipo15.servicio.servicios.ServicioServicio;

@Controller
@RequestMapping("/servicio")
public class ServicioControlador {

    @Autowired
    private ServicioServicio servicioServicio;

    @GetMapping("/registrar")
    public String registrar() {
        return "servicio_form.html";
    }

    @PostMapping("/registro")
    public String registrar(@RequestParam(required = false) String nombre,
            @RequestParam(required = false) String descripcion, ModelMap modelo) {
        try {
            servicioServicio.registrar(nombre, descripcion);
            modelo.put("exito", "El servicio fué cargado correctamente!");
            return "index.html";
        } catch (MiException e) {

            modelo.put("error", e.getMessage());
            
            modelo.put("nombre", nombre);
            modelo.put("descripcion", descripcion);

            return "servicio_form.html";
        }
    }

}
