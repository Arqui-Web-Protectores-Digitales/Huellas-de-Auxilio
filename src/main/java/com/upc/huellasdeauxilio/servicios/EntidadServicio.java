package com.upc.huellasdeauxilio.servicios;

import com.upc.huellasdeauxilio.entidades.Entidad;
import com.upc.huellasdeauxilio.repositorios.EntidadRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EntidadServicio {

    @Autowired
    private EntidadRepositorio entidadRepositorio;

    public Entidad insertar(Entidad entidad) {
        return entidadRepositorio.save(entidad);
    }

    public Entidad buscarPorUsuario(Long idUsuario) {
        return entidadRepositorio.findByUsuario_IdUsuario(idUsuario);
    }

    public List<Entidad> buscarPorZonaAtencion(String zonaAtencion) {
        return entidadRepositorio.findByZonaAtencionContainingIgnoreCase(zonaAtencion);
    }

    public List<Entidad> listarTodas() {
        return entidadRepositorio.findAll();
    }

    public Entidad buscarPorId(Long idEntidad) {
        return entidadRepositorio.findById(idEntidad).orElse(null);
    }

    public List<Entidad> buscarPorNombre(String nombre) {
        return entidadRepositorio.findByNombreEntidadContainingIgnoreCase(nombre);
    }

    // logica para encontrar la entidad más cercana
    public Entidad obtenerEntidadMasCercana(Float latReporte, Float lonReporte) {
        List<Entidad> todasLasEntidades = entidadRepositorio.findAll();
        Entidad masCercana = null;
        double distanciaMinima = Double.MAX_VALUE; // Iniciamos con un número gigante

        for (Entidad entidad : todasLasEntidades) {
            // Solo calculamos si la entidad tiene sus coordenadas registradas
            if (entidad.getLatitud() != null && entidad.getLongitud() != null) {

                double distancia = calcularDistancia(latReporte, lonReporte, entidad.getLatitud(), entidad.getLongitud());

                // Si esta distancia es menor que la que teníamos guardada, esta es la nueva más cercana
                if (distancia < distanciaMinima) {
                    distanciaMinima = distancia;
                    masCercana = entidad;
                }
            }
        }
        return masCercana;
    }

    private double calcularDistancia(float lat1, float lon1, float lat2, float lon2) {
        final int RADIO_TIERRA = 6371; // esto es el radio de la tierra en kilómetros

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);

        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return RADIO_TIERRA * c; // retorna la distancia en Kilómetros
    }



}