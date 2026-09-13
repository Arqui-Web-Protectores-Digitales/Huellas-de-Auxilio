package com.upc.huellasdeauxilio.servicios;

import com.upc.huellasdeauxilio.entidades.Mascota;
import com.upc.huellasdeauxilio.repositorios.MascotaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;


@Service
public class MascotaServicio {
    @Autowired
    private MascotaRepositorio mascotaRepositorio;

    public List<Mascota> listarDisponibles() {
        return mascotaRepositorio.findByEstadoTrue();
    }

    public Mascota buscarPorId(Long idMascota)
    {
        if(idMascota ==null)
        {
            return null;
        }
        return mascotaRepositorio.findById(idMascota).orElse(null);
    }

    //esta es una funcion extra para quitar especios extra y transformar
    //mayusculas a minusculas y facilitar la filtracion
    private String normalizarFiltro(String valor) {
        if (valor == null || valor.isBlank()) {
            return "todos";
        }

        return valor.trim().toLowerCase(Locale.ROOT);
    }

    public List<Mascota> filtrarMascotas(String nombre,
                                         String especie,
                                         String edad,
                                         String distrito)
    {
        return mascotaRepositorio.filtrarMascotas(
                normalizarFiltro(nombre),
                normalizarFiltro(especie),
                normalizarFiltro(edad),
                normalizarFiltro(distrito));
    }


}
