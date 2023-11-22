package com.equipo15.servicio.controladores;

import com.equipo15.servicio.entidades.Proveedor;
import com.equipo15.servicio.entidades.Residente;
import com.equipo15.servicio.entidades.Transaccion;
import com.equipo15.servicio.excepciones.MiException;
import com.equipo15.servicio.servicios.ProveedorServicio;
import com.equipo15.servicio.servicios.ResidenteServicio;
import com.equipo15.servicio.servicios.TransaccionServicio;
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
@RequestMapping("/libro")
public class TransaccionControlador {
    
    @Autowired
    private TransaccionServicio transaccionServicio;
    @Autowired
    private ProveedorServicio proveedorServicio;
    @Autowired
    private ResidenteServicio residenteServicio;
    
    @GetMapping("/registrar")
    public String registrar(ModelMap modelo) {
        
        List<Proveedor> proveedores = proveedorServicio.listarProveedores();
        List<Residente> residentes = residenteServicio.listarResidentes();
        
        modelo.addAttribute("proveedores", proveedores);
        modelo.addAttribute("residentes", residentes);
        
        return "transaccion_form.html";
    }   
    
    @PostMapping("/registro")
    public String registro(@RequestParam(required=false) String id, @RequestParam String titulo,
            @RequestParam(required=false) Integer ejemplares, @RequestParam String idAutor,
            @RequestParam String idEditorial, ModelMap modelo) {
        
        try {
            transaccionServicio.crearTransaccion(id, titulo, ejemplares, idAutor, idEditorial);
            
            modelo.put("exito", "La Transacción fué cargada correctamente!");
            
        } catch (MiException ex) {
            List<Proveedor> proveedores = proveedorServicio.listarProveedores();
            List<Residente> residentes = residenteServicio.listarResidentes();

            modelo.addAttribute("proveedores", proveedores);
            modelo.addAttribute("residentes", residentes);
            modelo.put("error", ex.getMessage());
            return "transaccion_form.html";
        }
        return "inicio.html";
    }
    
    @GetMapping("/lista")
    public String listar(ModelMap modelo) {
    
        List<Transaccion> transacciones = transaccionServicio.listarTransacciones();
        
        modelo.addAttribute("transacciones", transacciones);
        
        return "transaccion_list.html";
    
    }
    
    @GetMapping("/modificar/{isbn}")
    public String modificar(@PathVariable String id, ModelMap modelo){
        modelo.put("transaccion", transaccionServicio.getOne(id));
        
        List<Proveedor> proveedores = proveedorServicio.listarProveedores();
        List<Residente> residentes = residenteServicio.listarResidentes();
        
        modelo.addAttribute("proveedores", proveedores);
        modelo.addAttribute("residentes", residentes);
        
        return "transaccion_modificar.html";
    }
    
    @PostMapping("/modificar/{isbn}")
    public String modificar(@PathVariable String id, String titulo, Integer ejemplares, String idAutor, String idEditorial, ModelMap modelo){
        try {
            
            List<Proveedor> proveedores = proveedorServicio.listarProveedores();
            List<Residente> residentes = residenteServicio.listarResidentes();
            
            modelo.addAttribute("proveedores", proveedores);
            modelo.addAttribute("residentes", residentes);;
            
            transaccionServicio.modificarTransaccion(id, titulo, idAutor, idEditorial, ejemplares);
            
            return "redirect:../lista";
        } catch (MiException ex) {
            List<Proveedor> proveedores = proveedorServicio.listarProveedores();
            List<Residente> residentes = residenteServicio.listarResidentes();;
            modelo.put("error", ex.getMessage());
            modelo.addAttribute("proveedores", proveedores);
            modelo.addAttribute("residentes", residentes);
            return "transaccion_modificar.html";
        }
    }
    
}
