
package com.equipo15.servicio.servicios;

import com.equipo15.servicio.entidades.Proveedor;
import com.equipo15.servicio.entidades.Residente;
import com.equipo15.servicio.entidades.Transaccion;
import com.equipo15.servicio.excepciones.MiException;
import com.equipo15.servicio.repositorios.ProveedorRepositorio;
import com.equipo15.servicio.repositorios.ResidenteRepositorio;
import com.equipo15.servicio.repositorios.TransaccionRepositorio;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author alviz
 */
@Service
public class TransaccionServicio {
    
    @Autowired
    private TransaccionRepositorio transaccionRepositorio;
    
    @Autowired
    private ProveedorRepositorio proveedorRepositorio;
        
    @Autowired
    private ResidenteRepositorio residenteRepositorio;
    
    @Transactional
    public void crearTransaccion(Integer puntaje, String comentario, String presupuesto, String idProveedor, String idResidente) throws MiException{
        
        validar(puntaje, comentario, presupuesto, idProveedor, idResidente);
        
        Proveedor proveedor = proveedorRepositorio.findById(idProveedor).get();
        Residente residente = residenteRepositorio.findById(idResidente).get();
        Transaccion transaccion = new Transaccion();
        
        transaccion.setPuntaje(puntaje);
        transaccion.setComentario(comentario);
        transaccion.setPresupuesto(presupuesto);
        transaccion.setInicio(new Date());
        transaccion.setTermino(new Date());
        
        transaccion.setProveedor(proveedor);
        transaccion.setResidente(residente);
        
        transaccionRepositorio.save(transaccion);      
    }
    
    public List<Transaccion> listarTransacciones(){
    
        List<Transaccion> transacciones = new ArrayList();
        
        transacciones = transaccionRepositorio.findAll();
        
        return transacciones;
    }
    
    public Transaccion getOne(String id){
        return transaccionRepositorio.getOne(id);
    }
    
    public void modificarTransaccion(String id, Integer puntaje, String comentario, String presupuesto, String idProveedor, String idResidente)throws MiException{
        
        validar( puntaje, comentario, idProveedor, idResidente);
    
        Optional<Transaccion> respuestaTransaccion  = transaccionRepositorio.findById(id);
        Optional<Proveedor> respuestaProveedor  = proveedorRepositorio.findById(idProveedor);
        Optional<Residente> respuestaResidente  = residenteRepositorio.findById(idResidente);
        
        Proveedor proveedor = new Proveedor();
        Residente residente = new Residente();
        
        if(respuestaProveedor.isPresent()){
            
            proveedor = respuestaProveedor.get();
        }
        
        if(respuestaResidente.isPresent()){
        
            residente = respuestaResidente.get();
        }
        
        if(respuestaTransaccion.isPresent()){
        
            Transaccion transaccion = respuestaTransaccion.get();
            
            transaccion.setPuntaje(puntaje);
            transaccion.setComentario(comentario);
            transaccion.setPresupuesto(presupuesto);
            transaccion.setInicio(new Date());
            transaccion.setTermino(new Date());
            transaccion.setProveedor(proveedor);
            transaccion.setResidente(residente);
            transaccionRepositorio.save(transaccion);
       
        }
    
    }
    
    private void validar( Integer puntaje, String comentario, String presupuesto, String idProveedor, String idResidente) throws MiException {
    
         
        if (puntaje>10 || puntaje <=1 ||  puntaje == null) {
            throw new MiException("El puntaje no puede ser mayor a 10 o ser nulo o igual o menor a cero");
        }
         
        if (comentario.isEmpty() || comentario == null) {
            throw new MiException("El comentario no puede estar vacio o ser nulo");
        }
        if (presupuesto.isEmpty() || presupuesto == null) {
            throw new MiException("El comentario no puede estar vacio o ser nulo");
        }
        if(idProveedor.isEmpty() || idProveedor == null) {
            throw new MiException("el proveedor no puede ser nulo o estar vacio");
        }
        
        if(idResidente.isEmpty() || idResidente == null) {
            throw new MiException("el residente no puede ser nulo o estar vacio");
        }
    
    }
    
    
}
