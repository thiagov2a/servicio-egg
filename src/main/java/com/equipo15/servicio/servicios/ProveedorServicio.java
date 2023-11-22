
package com.equipo15.servicio.servicios;

import com.equipo15.servicio.entidades.Proveedor;
import com.equipo15.servicio.excepciones.MiException;
import com.equipo15.servicio.repositorios.ProveedorRepositorio;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author alviz
 */
@Service
public class ProveedorServicio {
    
    @Autowired
    private ProveedorRepositorio proveedorRepositorio;
    
    @Transactional
    public void crearProveedor(String nombre)throws MiException{
        
        validar(nombre);
        
        Proveedor proveedor = new Proveedor();
        
        proveedor.setNombre(nombre);
        
        proveedorRepositorio.save(proveedor);
        
    }
    
     public List<Proveedor> listarProveedores(){
    
        List<Proveedor> proveedores = new ArrayList();
        
        proveedores = proveedorRepositorio.findAll();
        
        return proveedores;
    }
    
     public void modificarProveedor(String nombre, String id) throws MiException{
         
         validar(nombre);
     
         Optional<Proveedor> respuesta = proveedorRepositorio.findById(id);
         
         if(respuesta.isPresent()){
             
             Proveedor proveedor = respuesta.get();
             
             proveedor.setNombre(nombre);
             
             proveedorRepositorio.save(proveedor);
         }
     }
     
     public Proveedor getOne(String id){
         return proveedorRepositorio.getOne(id);
     }
     
    private void validar(String nombre) throws MiException {
        
            if (nombre.isEmpty() || nombre == null) {
                throw new MiException("el nombre no puede ser nulo o estar vacio");
        }
    
    }
}
