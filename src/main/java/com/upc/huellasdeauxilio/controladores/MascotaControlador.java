package com.upc.huellasdeauxilio.controladores;


import com.upc.huellasdeauxilio.entidades.Mascota;
import com.upc.huellasdeauxilio.servicios.MascotaServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class MascotaControlador {

    @Autowired
    private MascotaServicio mascotaServicio;

    @GetMapping("/mascotas")
    public List<Mascota> listarMascotasDisponibles()
    {
        return mascotaServicio.listarDisponibles();
    }

    @GetMapping("/mascota/{idMascota}")
    public Mascota buscarPorId(@PathVariable Long idMascota)
    {
        return mascotaServicio.buscarPorId(idMascota);
    }

    @GetMapping("/mascotas/filtrar/{nombre}/{especie}/{edad}/{distrito}")
    public List<Mascota> filtrarMascotas(@PathVariable String nombre,
                                         @PathVariable String especie,
                                         @PathVariable String edad,
                                         @PathVariable String distrito)
    {
        return mascotaServicio.filtrarMascotas(nombre, especie, edad, distrito);
    }
    //para limpiar los filtros se vuelve a consultar /api/mascotas
}
