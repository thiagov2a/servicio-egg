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

@Service
public class TransaccionServicio {
    
    @Autowired
    private TransaccionRepositorio transaccionRepositorio;
    
    @Autowired
    private ProveedorRepositorio proveedorRepositorio;
        
    @Autowired
    private ResidenteRepositorio residenteRepositorio;
    
    @Transactional
    public void crearTransaccion(String id, String titulo, Integer ejemplares, String idProveedor, String idResidente) throws MiException{
        
        validar(id, titulo, idProveedor, idResidente, ejemplares);
        
        Proveedor proveedor = proveedorRepositorio.findById(idProveedor).get();
        Residente residente = residenteRepositorio.findById(idResidente).get();
        Transaccion transaccion = new Transaccion();
        
        transaccion.
        transaccion.setComentario(ejemplares);
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
    
    public void modificarTransaccion(String id, String titulo, String idProveedor, String idResidente, Integer ejemplares)throws MiException{
        
        validar(id, titulo, idProveedor, idResidente, ejemplares);
    
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
            
            transaccion.setPuntaje(titulo);
            transaccion.setComentario(ejemplares);
            transaccion.setInicio(new Date());
            transaccion.setTermino(new Date());
            
            transaccionRepositorio.save(transaccion);
       
        }
    
    }
    
    private void validar(String id, String titulo, String idProveedor, String idResidente, Integer ejemplares) throws MiException {
    
        if(id == null){
            throw new MiException("el isbn no puede ser nulo");
        }
        
        if (titulo.isEmpty() || titulo == null) {
            throw new MiException("el titulo no puede ser nulo o estar vacio");
        }
        
        if (ejemplares == null) {
            throw new MiException("ejemplares no puede ser nulo");
        }
        
        if(idProveedor.isEmpty() || idProveedor == null) {
            throw new MiException("el Autor no puede ser nulo o estar vacio");
        }
        
        if(idResidente.isEmpty() || idResidente == null) {
            throw new MiException("el Editorial no puede ser nulo o estar vacio");
        }
    
    }
    
    
}
