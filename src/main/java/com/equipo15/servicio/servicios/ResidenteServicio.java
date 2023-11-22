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

@Service
public class ResidenteServicio {
    
    @Autowired
    private ResidenteRepositorio residenteRepositorio;
    
    @Transactional
    public void crearResidente(String nombre) throws MiException{
        
        validar(nombre);
        
        Residente residente = new Residente();
        
        residente.setNombre(nombre);
        
        residenteRepositorio.save(residente);
        
    }
    
     public List<Residente> listarResidentes(){
    
        List<Residente> residentes = new ArrayList();
        
        residentes = residenteRepositorio.findAll();
        
        return residentes;
    }
     
    public void modificarResidente(String nombre, String id) throws MiException{
        
        validar(nombre);
     
         Optional<Residente> respuesta = residenteRepositorio.findById(id);
         
         if(respuesta.isPresent()){
             
             Residente residente = respuesta.get();
             
             residente.setNombre(nombre);
             
             residenteRepositorio.save(residente);
         }
     }
    
    public Residente getOne(String id){
         return residenteRepositorio.getOne(id);
     }
    
     private void validar(String nombre) throws MiException {
         
            if (nombre.isEmpty() || nombre == null) {
            throw new MiException("el nombre no puede ser nulo o estar vacio");
        }
     }    
}
