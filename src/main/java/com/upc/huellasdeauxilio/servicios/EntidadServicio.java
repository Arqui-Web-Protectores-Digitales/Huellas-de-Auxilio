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
}