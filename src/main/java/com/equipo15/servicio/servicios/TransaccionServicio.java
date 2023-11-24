package com.equipo15.servicio.servicios;

import com.equipo15.servicio.entidades.Proveedor;
import com.equipo15.servicio.entidades.Residente;
import com.equipo15.servicio.entidades.Transaccion;
import com.equipo15.servicio.enumeraciones.Estado;
import com.equipo15.servicio.excepciones.MiException;
import com.equipo15.servicio.repositorios.ProveedorRepositorio;
import com.equipo15.servicio.repositorios.ResidenteRepositorio;
import com.equipo15.servicio.repositorios.TransaccionRepositorio;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransaccionServicio {
    
    @Autowired
    private TransaccionRepositorio transaccionRepositorio;
    
    @Autowired
    private ProveedorRepositorio proveedorRepositorio;
        
    @Autowired
    private ResidenteRepositorio residenteRepositorio;
    
    @Transactional
    public void crearTransaccion(String id, String comentario, String idProveedor, String idResidente, Integer calificacion, Long presupuesto) throws MiException{
        
        validar(id, comentario, idProveedor, idResidente, calificacion, presupuesto);
        
        Proveedor proveedor = proveedorRepositorio.findById(idProveedor).get();
        Residente residente = residenteRepositorio.findById(idResidente).get();
        Transaccion transaccion = new Transaccion();
        
        transaccion.setCalificacion(calificacion);
        transaccion.setComentario(comentario);
        transaccion.setPresupuesto(presupuesto);
        transaccion.setEstado(Estado.ACEPTADO);
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
    
    public void modificarTransaccion(String id, String comentario, String idProveedor, String idResidente, Integer calificacion, Long presupuesto)throws MiException{
        
        validar(id, comentario, idProveedor, idResidente, calificacion, presupuesto);
    
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
        
        if (respuestaTransaccion.isPresent()) {

            Transaccion transaccion = respuestaTransaccion.get();

            transaccion.setCalificacion(calificacion);
            transaccion.setComentario(comentario);
            transaccion.setPresupuesto(presupuesto);
            transaccion.setEstado(Estado.ACEPTADO);
            transaccion.setProveedor(proveedor);
            transaccion.setResidente(residente);

            transaccionRepositorio.save(transaccion);

        }
    
    }
    
    private void validar(String id, String comentario, String idProveedor, String idResidente, Integer calificacion,Long presupuesto) throws MiException {
    
        if(id == null){
            throw new MiException("el isbn no puede ser nulo");
        }
        
        if (comentario.isEmpty() || comentario == null) {
            throw new MiException("el titulo no puede ser nulo o estar vacio");
        }
        
        if (calificacion < 0 || calificacion > 5 || calificacion == null) {
            throw new MiException("la Calificación no puede ser menor a cero, mayor que cinco o nula");
        }
        
        if(idProveedor.isEmpty() || idProveedor == null) {
            throw new MiException("el Proveedor no puede ser nulo o estar vacio");
        }
        
        if(idResidente.isEmpty() || idResidente == null) {
            throw new MiException("el Residente no puede ser nulo o estar vacio");
        }
        
        if (presupuesto < 0 || presupuesto == null) {
            throw new MiException("el Presupuesto no puede ser menor a cero o nulo");
        }
    
    }
    
}
