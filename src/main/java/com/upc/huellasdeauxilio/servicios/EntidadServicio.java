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
}