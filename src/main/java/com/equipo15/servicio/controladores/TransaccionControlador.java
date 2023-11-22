
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

/**
 *
 * @author alviz
 */
@Controller
@RequestMapping("/transaccion")
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
    public String registro(@RequestParam(required=false) Integer puntaje, @RequestParam String comentario,
            @RequestParam String presupuesto, @RequestParam String idProveedor,
            @RequestParam String idResidente, ModelMap modelo) {
        
        try {
            transaccionServicio.crearTransaccion(puntaje, comentario, presupuesto, idProveedor, idResidente);
            
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
    
    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, ModelMap modelo){
        modelo.put("transaccion", transaccionServicio.getOne(id));
        
        List<Proveedor> proveedores = proveedorServicio.listarProveedores();
        List<Residente> residentes = residenteServicio.listarResidentes();
        
        modelo.addAttribute("proveedores", proveedores);
        modelo.addAttribute("residentes", residentes);
        
        return "transaccion_modificar.html";
    }
    
    @PostMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, Integer puntaje, String comentario, String presupuesto, String idProveedor, String idResidente, ModelMap modelo){
        try {
            
            List<Proveedor> proveedores = proveedorServicio.listarProveedores();
            List<Residente> residentes = residenteServicio.listarResidentes();
            
            modelo.addAttribute("proveedores", proveedores);
            modelo.addAttribute("residentes", residentes);;
            
            transaccionServicio.modificarTransaccion(id, puntaje, comentario, presupuesto, idProveedor, idResidente);
            
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
