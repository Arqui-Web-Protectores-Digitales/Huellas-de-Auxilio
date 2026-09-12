package com.upc.huellasdeauxilio.servicios;

import com.upc.huellasdeauxilio.entidades.Entidad;
import com.upc.huellasdeauxilio.repositorios.EntidadRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public Entidad buscarPorZonaAtencion(String zonaAtencion) {
        return entidadRepositorio.findByZonaAtencion(zonaAtencion);
    }
}