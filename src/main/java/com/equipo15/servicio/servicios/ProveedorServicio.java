
package com.equipo15.servicio.servicios;

import com.equipo15.servicio.entidades.Proveedor;
import com.equipo15.servicio.entidades.Servicio;
import com.equipo15.servicio.entidades.Usuario;
import com.equipo15.servicio.excepciones.MiException;
import com.equipo15.servicio.repositorios.ProveedorRepositorio;
import com.equipo15.servicio.repositorios.ServicioRepositorio;
import com.equipo15.servicio.repositorios.UsuarioRepositorio;
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
    
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;
    
    @Autowired
    private ServicioRepositorio servicioRepositorio;
    
    @Transactional
    public void crearProveedor(String cuil, String contacto, String calificacion, String descripcion, String idUsuario, String idServicio, Integer precioh)throws MiException{
        
        validar(cuil, contacto, calificacion, descripcion, idUsuario, idServicio, precioh);
        
        Usuario usuario = usuarioRepositorio.findById(idUsuario).get();
        Servicio servicio = servicioRepositorio.findById(idServicio).get();
        Proveedor proveedor = new Proveedor();
        
        proveedor.setCuil(cuil);
        proveedor.setContacto(contacto);
        proveedor.setCalificacion(calificacion);
        proveedor.setDescripcion(descripcion);
        proveedor.setPrecioh(precioh);
        proveedor.setServicio(servicio);
        proveedor.setUsuario(usuario);
        
        proveedorRepositorio.save(proveedor);
        
    }
    
     public List<Proveedor> listarProveedores(){
    
        List<Proveedor> proveedores = new ArrayList();
        
        proveedores = proveedorRepositorio.findAll();
        
        return proveedores;
    }
    
    public void modificarProveedor(String cuil, String contacto, String calificacion, String descripcion, String idUsuario, String idServicio, Integer precioh) throws MiException {

        validar(cuil, contacto, calificacion, descripcion, idUsuario, idServicio, precioh);

        Optional<Proveedor> respuestaProveedor = proveedorRepositorio.findById(cuil);
        Optional<Usuario> respuestaUsuario = usuarioRepositorio.findById(idUsuario);
        Optional<Servicio> respuestaServicio = servicioRepositorio.findById(idServicio);
        
        Usuario usuario = new Usuario();
        Servicio servicio = new Servicio();

         if(respuestaUsuario.isPresent()){
            
            usuario = respuestaUsuario.get();
        }
        
        if(respuestaServicio.isPresent()){
        
            servicio = respuestaServicio.get();
        }
        
        if(respuestaProveedor.isPresent()){
        
            Proveedor proveedor = respuestaProveedor.get();;

            proveedor.setCuil(cuil);
            proveedor.setContacto(contacto);
            proveedor.setCalificacion(calificacion);
            proveedor.setDescripcion(descripcion);
            proveedor.setPrecioh(precioh);
            proveedor.setServicio(servicio);
            proveedor.setUsuario(usuario);

            proveedorRepositorio.save(proveedor);
        }
    }
     
     public Proveedor getOne(String id){
         return proveedorRepositorio.getOne(id);
     }
     
    private void validar(String cuil, String contacto, String calificacion, String descripcion, String idUsuario, String idServicio, Integer precioh) throws MiException {
        
            if (cuil.isEmpty() || cuil == null) {
                throw new MiException("el Cuil no puede ser nulo o estar vacio");
        }
            
        if (contacto.isEmpty() || contacto == null) {
            throw new MiException("el Contacto no puede ser nulo o estar vacio");
        }
        
        if (calificacion.isEmpty() || calificacion == null) {
            throw new MiException("la Calificacion no puede ser nulo o estar vacia");
        }
        
        if(descripcion.isEmpty() || descripcion == null) {
            throw new MiException("la Descripción no puede ser nula o estar vacia");
        }
        
        if(idUsuario.isEmpty() || idUsuario == null) {
            throw new MiException("el Id Usuario no puede ser nulo o estar vacio");
        }
        
        if(idServicio.isEmpty() || idServicio == null) {
            throw new MiException("el Id Servicio no puede ser nulo o estar vacio");
        }
        
        if(precioh < 0 || precioh == null) {
            throw new MiException("el Precio por Hora no puede ser nulo o menor que cero");
        }
    
    }
            

}
