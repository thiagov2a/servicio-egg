
package com.equipo15.servicio.servicios;

import com.equipo15.servicio.entidades.Residente;
import com.equipo15.servicio.excepciones.MiException;
import com.equipo15.servicio.repositorios.ResidenteRepositorio;
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
public class ResidenteServicio {
    
    @Autowired
    private ResidenteRepositorio residenteRepositorio;
    
    @Transactional
    public void crearResidente(String dni, String nombre, String direccion) throws MiException{
        
        validar(dni, nombre, direccion);
        
        Residente residente = new Residente();
        residente.setDni(dni);
        residente.setNombre(nombre);
        residente.setDireccion(direccion);
        residente.setAltaBaja(true);
        
        residenteRepositorio.save(residente);
        
    }
    
     public List<Residente> listarResidentes(){
    
        List<Residente> residentes = new ArrayList();
        
        residentes = residenteRepositorio.findAll();
        
        return residentes;
    }
     
    public void modificarResidente(String dni, String nombre, String direccion) throws MiException{
        
        validar(dni, nombre, direccion);
     
         Optional<Residente> respuesta = residenteRepositorio.findById(dni);
         
         if(respuesta.isPresent()){
             
             Residente residente = respuesta.get();
             
             residente.setDni(dni);
             residente.setNombre(nombre);
             residente.setDireccion(direccion);
             residente.setAltaBaja(true);
             
             residenteRepositorio.save(residente);
         }
     }
    
    public Residente getOne(String dni){
         return residenteRepositorio.getOne(dni);
     }
    
     private void validar(String dni, String nombre, String direccion) throws MiException {
        if (dni.isEmpty() || dni == null) {
            throw new MiException("el numero de DNI no puede ser nulo o estar vacio");
        } 
            if (nombre.isEmpty() || nombre == null) {
            throw new MiException("el nombre no puede ser nulo o estar vacio");
        }
        if (direccion.isEmpty() || direccion == null) {
            throw new MiException("la dirección no puede ser nulo o estar vacio");
        }
     }    
}
