
package com.equipo15.servicio.controladores;

import com.equipo15.servicio.entidades.Residente;
import com.equipo15.servicio.excepciones.MiException;
import com.equipo15.servicio.servicios.ResidenteServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author alviz
 */
@Controller
@RequestMapping("/residente")
public class ResidenteControlador {
    
    @Autowired
    private ResidenteServicio residenteServicio;
    
    @GetMapping("/registrar")
    public String registrar(){
        return "residente_form.html";
    }
    
    @PostMapping("/registro")
    public String registro(@RequestParam String dni_cuil, String domicilio, String idUsuario, ModelMap modelo){
        
        try {
            residenteServicio.crearResidente(dni_cuil,domicilio, idUsuario);
            modelo.put("exito", "El Residente fué cargado correctamente!");
        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
            return "residente_form.html";
        }
        
        return "inicio.html";
    }
    
    @GetMapping("/lista")
    public String listar(ModelMap modelo) {
        List<Residente> residentes = residenteServicio.listarResidentes();
        modelo.addAttribute("residentes", residentes);
        
        return "residente_list.html";
    }
    
    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable String dni_cuil, ModelMap modelo){
        modelo.put("residente", residenteServicio.getOne(dni_cuil));
        return "residente_modificar.html";
    }
    
    @PostMapping("/modificar/{id}")
    public String modificar(@PathVariable String dni_cuil, String domicilio, String idUsuario, ModelMap modelo){
        try {
            residenteServicio.modificarResidente(dni_cuil,domicilio, idUsuario);
            
            return "redirect:../lista";
        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
            return "residente_modificar.html";
        }
    }
    
}
