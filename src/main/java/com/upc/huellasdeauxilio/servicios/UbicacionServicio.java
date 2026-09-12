package com.upc.huellasdeauxilio.servicios;

import com.upc.huellasdeauxilio.entidades.Ubicacion;
import com.upc.huellasdeauxilio.repositorios.UbicacionRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UbicacionServicio {

    @Autowired
    private UbicacionRepositorio ubicacionRepositorio;

    public Ubicacion insertar(Ubicacion ubicacion) {
        return ubicacionRepositorio.save(ubicacion);
    }
}