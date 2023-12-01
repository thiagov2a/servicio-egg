package com.equipo15.servicio.controladores;

import com.equipo15.servicio.entidades.Proveedor;
import com.equipo15.servicio.entidades.Transaccion;
import com.equipo15.servicio.entidades.Usuario;
import com.equipo15.servicio.enumeraciones.Estado;
import com.equipo15.servicio.repositorios.TransaccionRepositorio;
import com.equipo15.servicio.servicios.ProveedorServicio;
import com.equipo15.servicio.servicios.TransaccionServicio;
import jakarta.servlet.http.HttpSession;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/proveedor")
public class ProveedorControlador {

    @Autowired
    private ProveedorServicio proveedorServicio;

    @Autowired
    private TransaccionServicio transaccionServicio;
    
    @Autowired
    private TransaccionRepositorio transaccionRepositorio;
    

    @GetMapping("/lista")
    public String listar(ModelMap modelo) {
        List<Proveedor> proveedores = proveedorServicio.listarProveedores();
        modelo.addAttribute("proveedores", proveedores);
        return "proveedor_list.html";
    }

    @GetMapping("/transacciones")
    public String listarTransacciones(ModelMap modelo, HttpSession session) {

        Usuario proveedor = (Usuario) session.getAttribute("usuariosession");
        String idProveedor = proveedor.getId();

        List<Transaccion> transacciones = transaccionServicio.listarTransaccionesPorProveedor(idProveedor);
        modelo.addAttribute("transacciones", transacciones);
        return "transaccion_list.html";

    }

    @GetMapping("/transacciones/cancelar/{idTransaccion}")
    public String cancelarTransaccion(ModelMap modelo, @PathVariable String idTransaccion, HttpSession session) {

        Transaccion transaccion = transaccionServicio.buscarTransaccionPorId(idTransaccion);
        
        transaccion.setEstado(Estado.CANCELADO);
        
        transaccionRepositorio.save(transaccion);
        
        Usuario proveedor = (Usuario) session.getAttribute("usuariosession");
        String idProveedor = proveedor.getId();
        
        List<Transaccion> transacciones = transaccionServicio.listarTransaccionesPorProveedor(idProveedor);
        modelo.addAttribute("transacciones", transacciones);
        
        return "transaccion_list.html";

    }
    
    
        @GetMapping("/transacciones/aceptar/{idTransaccion}")
    public String aceptarTransaccion(ModelMap modelo, @PathVariable String idTransaccion, HttpSession session) {

        Transaccion transaccion = transaccionServicio.buscarTransaccionPorId(idTransaccion);
        
        transaccion.setEstado(Estado.ACEPTADO);
        
        transaccionRepositorio.save(transaccion);
        
        Usuario proveedor = (Usuario) session.getAttribute("usuariosession");
        String idProveedor = proveedor.getId();
        
        List<Transaccion> transacciones = transaccionServicio.listarTransaccionesPorProveedor(idProveedor);
        modelo.addAttribute("transacciones", transacciones);
        
        return "transaccion_list.html";

    }
    
    
}
