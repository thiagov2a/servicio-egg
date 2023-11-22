
package com.equipo15.servicio.controladores;

import com.equipo15.servicio.entidades.Proveedor;
import com.equipo15.servicio.excepciones.MiException;
import com.equipo15.servicio.servicios.ProveedorServicio;
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
@RequestMapping("/proveedor")
public class ProveedorControlador {
    
    @Autowired
    private ProveedorServicio proveedorServicio;
    
    @GetMapping("/registrar")
    public String registrar(){
        return "proveedor_form.html";
    }
    
    @PostMapping("/registro")
    public String registro(@RequestParam String nombre, ModelMap modelo){
        
        try {
            proveedorServicio.crearProveedor(nombre);
            modelo.put("exito", "El Proveedor fué cargado correctamente!");
        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
            return "autor_form.html";
        }
        
        return "inicio.html";
    }
    
    @GetMapping("/lista")
    public String listar(ModelMap modelo) {
        List<Proveedor> proveedores = proveedorServicio.listarProveedores();
        modelo.addAttribute("proveedores", proveedores);
        
        return "proveedor_list.html";
    }
    
    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, ModelMap modelo){
        modelo.put("proveedor", proveedorServicio.getOne(id));
        return "proveedor_modificar.html";
    }
    
    @PostMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, String nombre, ModelMap modelo){
        try {
            proveedorServicio.modificarProveedor(nombre, id);
            
            return "redirect:../lista";
        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
            return "proveedor_modificar.html";
        }
    }
    
}
