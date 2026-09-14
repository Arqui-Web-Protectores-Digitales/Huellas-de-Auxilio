package com.upc.huellasdeauxilio.servicios;

import com.upc.huellasdeauxilio.entidades.TipoEntidad;
import com.upc.huellasdeauxilio.repositorios.TipoEntidadRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TipoEntidadServicio {

    @Autowired
    private TipoEntidadRepositorio tipoEntidadRepositorio;

    public TipoEntidad insertar(TipoEntidad tipoEntidad) {
        return tipoEntidadRepositorio.save(tipoEntidad);
    }

    
}