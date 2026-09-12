package com.upc.huellasdeauxilio.servicios;

import com.upc.huellasdeauxilio.entidades.Ciudadano;
import com.upc.huellasdeauxilio.repositorios.CiudadanoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CiudadanoServicio {

    @Autowired
    private CiudadanoRepositorio ciudadanoRepositorio;

    public Ciudadano insertar(Ciudadano ciudadano) {
        return ciudadanoRepositorio.save(ciudadano);
    }

    public Ciudadano buscarPorDni(String dni) {
        return ciudadanoRepositorio.findByDni(dni);
    }
}