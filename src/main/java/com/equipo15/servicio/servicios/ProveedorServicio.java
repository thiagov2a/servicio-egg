
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
    public void crearProveedor(String cuil, String nombre, String tipoServicio)throws MiException{
        
        validar(cuil, nombre, tipoServicio);
        
        Proveedor proveedor = new Proveedor();
        proveedor.setCuil(cuil);
        proveedor.setNombre(nombre);
        proveedor.setTipoServicio(tipoServicio);
        proveedor.setAltaBaja(true);
        
        proveedorRepositorio.save(proveedor);
        
    }
    
     public List<Proveedor> listarProveedores(){
    
        List<Proveedor> proveedores = new ArrayList();
        
        proveedores = proveedorRepositorio.findAll();
        
        return proveedores;
    }
    
     public void modificarProveedor(String cuil, String nombre, String tipoServicio) throws MiException{
         
         validar(cuil, nombre, tipoServicio);
     
         Optional<Proveedor> respuesta = proveedorRepositorio.findById(cuil);
         
         if(respuesta.isPresent()){
             
             Proveedor proveedor = respuesta.get();
             proveedor.setCuil(cuil);
             proveedor.setNombre(nombre);
             proveedor.setTipoServicio(tipoServicio);
             proveedor.setAltaBaja(true);
             
             proveedorRepositorio.save(proveedor);
         }
     }
     
     public Proveedor getOne(String cuil){
         return proveedorRepositorio.getOne(cuil);
     }
     
    private void validar(String cuil, String nombre, String tipoServicio) throws MiException {
        if (cuil.isEmpty() || cuil == null) {
                throw new MiException("el CUIL no puede ser nulo o estar vacio");
        }
            if (nombre.isEmpty() || nombre == null) {
                throw new MiException("el nombre no puede ser nulo o estar vacio");
        }
    if (tipoServicio.isEmpty() || tipoServicio == null) {
                throw new MiException("el tipo de servicio no puede ser nulo o estar vacio");
        }
    }
}
