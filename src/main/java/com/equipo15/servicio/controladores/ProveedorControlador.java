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
    public String registro(@RequestParam String cuil, @RequestParam String contacto, @RequestParam String calificacion, @RequestParam String descripcion,
            @RequestParam String idUsuario, @RequestParam String idServicio, @RequestParam(required=false) Integer precioh, ModelMap modelo){
        
        try {
            proveedorServicio.crearProveedor(cuil, contacto, calificacion, descripcion, idUsuario, idServicio,  precioh);
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
    
    @GetMapping("/modificar/{cuil}")
    public String modificar(@PathVariable String cuil, ModelMap modelo){
        modelo.put("proveedor", proveedorServicio.getOne(cuil));
        return "proveedor_modificar.html";
    }
    
    @PostMapping("/modificar/{cuil}")
    public String modificar(@PathVariable String cuil, String contacto, String calificacion, String descripcion, String idUsuario, String idServicio, Integer precioh, ModelMap modelo){
        try {
            proveedorServicio.modificarProveedor(cuil, contacto, calificacion, descripcion, idUsuario, idServicio, precioh);
            
            return "redirect:../lista";
        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
            return "proveedor_modificar.html";
        }
    }
    
}
