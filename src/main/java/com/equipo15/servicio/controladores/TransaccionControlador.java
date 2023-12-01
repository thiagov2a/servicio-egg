package com.equipo15.servicio.controladores;

import com.equipo15.servicio.entidades.Proveedor;
import com.equipo15.servicio.entidades.Transaccion;
import com.equipo15.servicio.entidades.Usuario;
import com.equipo15.servicio.excepciones.MiException;
import com.equipo15.servicio.servicios.ProveedorServicio;
import com.equipo15.servicio.servicios.TransaccionServicio;
import com.equipo15.servicio.servicios.UsuarioServicio;

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
@RequestMapping("/transaccion")
public class TransaccionControlador {

    @Autowired
    private TransaccionServicio transaccionServicio;
    @Autowired
    private ProveedorServicio proveedorServicio;
    @Autowired
    private UsuarioServicio usuarioServicio;

    @GetMapping("/registrar")
    public String registrar(ModelMap modelo) {

        List<Proveedor> proveedores = proveedorServicio.listarProveedores();
        List<Usuario> residentes = usuarioServicio.listarUsuarios();

        modelo.addAttribute("proveedores", proveedores);
        modelo.addAttribute("residentes", residentes);

        return "transaccion_form.html";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam(required = false) String id, @RequestParam String comentario,
            @RequestParam String idProveedor,
            @RequestParam String idResidente, @RequestParam(required = false) Integer calificacion,
            @RequestParam(required = false) Long presupuesto, ModelMap modelo) {

        try {
            transaccionServicio.registrar(comentario, calificacion, presupuesto, idProveedor, idResidente);
            modelo.put("exito", "La Transacción fué cargada correctamente!");
            return "index.html";
        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());

            List<Proveedor> proveedores = proveedorServicio.listarProveedores();
            List<Usuario> residentes = usuarioServicio.listarUsuarios();

            modelo.addAttribute("proveedores", proveedores);
            modelo.addAttribute("residentes", residentes);

            return "transaccion_form.html";
        }
    }

    @GetMapping("/lista")
    public String listar(ModelMap modelo) {
        List<Transaccion> transacciones = transaccionServicio.listarTransacciones();
        modelo.addAttribute("transacciones", transacciones);
        return "transaccion_list.html";
    }

    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, ModelMap modelo) {
        Transaccion transaccion = transaccionServicio.buscarTransaccionPorId(id);
        modelo.put("transaccion", transaccion);

        List<Proveedor> proveedores = proveedorServicio.listarProveedores();
        List<Usuario> residentes = usuarioServicio.listarUsuarios();

        modelo.addAttribute("proveedores", proveedores);
        modelo.addAttribute("residentes", residentes);

        return "transaccion_modificar.html";
    }

    @PostMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, String comentario, String idProveedor, String idUsuario,
            Integer calificacion, Long presupuesto, ModelMap modelo) {
        try {

            List<Proveedor> proveedores = proveedorServicio.listarProveedores();
            List<Usuario> residentes = usuarioServicio.listarUsuarios();

            modelo.addAttribute("proveedores", proveedores);
            modelo.addAttribute("residentes", residentes);

            transaccionServicio.modificar(id, comentario, calificacion, presupuesto, idProveedor, idUsuario);

            return "redirect:../lista";
        } catch (MiException ex) {
            List<Proveedor> proveedores = proveedorServicio.listarProveedores();
            List<Usuario> residentes = usuarioServicio.listarUsuarios();

            modelo.put("error", ex.getMessage());
            modelo.addAttribute("proveedores", proveedores);
            modelo.addAttribute("residentes", residentes);
            return "transaccion_modificar.html";
        }
    }

}
