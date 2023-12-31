package com.equipo15.servicio.servicios;

import com.equipo15.servicio.entidades.Imagen;
import com.equipo15.servicio.excepciones.MiException;
import com.equipo15.servicio.repositorios.ImagenRepositorio;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImagenServicio {

    @Autowired
    private ImagenRepositorio imagenRepositorio;

    @Transactional
    public Imagen guardar(MultipartFile archivo, String directorio) throws MiException {
        try {
            Imagen imagen = new Imagen();

            if (archivo != null && !archivo.isEmpty()) {
                imagen.setMime(archivo.getContentType());
                imagen.setNombre(archivo.getName());
                imagen.setContenido(archivo.getBytes());
            } else {
                imagen = cargarImagenPredeterminada(directorio);
            }

            return imagenRepositorio.save(imagen);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    public Imagen actualizar(MultipartFile archivo, String idImagen, String directorio) throws MiException {
        try {
            Imagen imagen = new Imagen();

            if (idImagen != null) {
                Optional<Imagen> respuesta = imagenRepositorio.findById(idImagen);
                if (respuesta.isPresent()) {
                    imagen = respuesta.get();
                }
            }

            if (archivo != null && !archivo.isEmpty()) {
                imagen.setMime(archivo.getContentType());
                imagen.setNombre(archivo.getName());
                imagen.setContenido(archivo.getBytes());
            } else {
                imagen = cargarImagenPredeterminada(directorio);
            }

            return imagenRepositorio.save(imagen);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    private Imagen cargarImagenPredeterminada(String directorio) throws Exception {
        Imagen imagen = new Imagen();

        String userDirectory = System.getProperty("user.dir");
        String rutaImagenPredeterminada = userDirectory + directorio;

        Path pathImagenPredeterminada = Paths.get(rutaImagenPredeterminada);
        byte[] contenidoImagenPredeterminada = Files.readAllBytes(pathImagenPredeterminada);

        imagen.setMime(Files.probeContentType(pathImagenPredeterminada));
        imagen.setNombre("img_default.png");
        imagen.setContenido(contenidoImagenPredeterminada);

        return imagen;
    }
}
