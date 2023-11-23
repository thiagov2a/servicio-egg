package com.equipo15.servicio.servicios;

import com.equipo15.servicio.entidades.Residente;
import com.equipo15.servicio.entidades.Usuario;
import com.equipo15.servicio.excepciones.MiException;
import com.equipo15.servicio.repositorios.ResidenteRepositorio;
import com.equipo15.servicio.repositorios.UsuarioRepositorio;
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
    
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;
    
    @Transactional
    public void crearResidente(String dni_cuil, String domicilio, String idUsuario) throws MiException{
        
        validar(dni_cuil, domicilio, idUsuario);
        Usuario usuario = usuarioRepositorio.findById(idUsuario).get();
        
        Residente residente = new Residente();
        
        residente.setDni_cuil(dni_cuil);
        residente.setDomicilio(domicilio);
        residente.setUsuario(usuario);
        
        residenteRepositorio.save(residente);
        
    }
    
     public List<Residente> listarResidentes(){
    
        List<Residente> residentes = new ArrayList();
        
        residentes = residenteRepositorio.findAll();
        
        return residentes;
    }
     
    public void modificarResidente(String dni_cuil, String domicilio, String idUsuario) throws MiException{
        
        validar(dni_cuil, domicilio, idUsuario);
     
         Optional<Residente> respuestaResidente = residenteRepositorio.findById(dni_cuil);
         Optional<Usuario> respuestaUsuario = usuarioRepositorio.findById(idUsuario);
         
         Usuario usuario = new Usuario();
         
          if(respuestaUsuario.isPresent()){
            
            usuario = respuestaUsuario.get();
        }
         
         if(respuestaResidente.isPresent()){
             
             Residente residente = respuestaResidente.get();
             
             residente.setDni_cuil(dni_cuil);
             residente.setDomicilio(domicilio);
             residente.setUsuario(usuario);
             
             residenteRepositorio.save(residente);
         }
     }
    
    public Residente getOne(String id){
         return residenteRepositorio.getOne(id);
     }
    
     private void validar(String dni_cuil, String domicilio, String idUsuario) throws MiException {
         
            if (dni_cuil.isEmpty() || dni_cuil == null) {
            throw new MiException("el Cuil no puede ser nulo o estar vacio");
        }
            if (domicilio.isEmpty() || domicilio == null) {
            throw new MiException("el Domicilio no puede ser nulo o estar vacio");
        }
            if (idUsuario.isEmpty() || idUsuario == null) {
            throw new MiException("el Id Usuario no puede ser nulo o estar vacio");
        }
            
     }    
}
