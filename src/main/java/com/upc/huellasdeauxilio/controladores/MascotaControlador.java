package com.upc.huellasdeauxilio.controladores;


import com.upc.huellasdeauxilio.entidades.Mascota;
import com.upc.huellasdeauxilio.servicios.MascotaServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/mascotas/entidad/{idEntidad}")
    public List<Mascota> listarMascotasPorEntidad(@PathVariable Long idEntidad)
    {
        return mascotaServicio.listarDisponiblesPorEntidad(idEntidad);
    }

    @GetMapping("/mascotas/entidad/{idEntidad}/filtrar/{busqueda}/{especie}/{edad}")
    public List<Mascota> filtrarMascotasPorEntidad(@PathVariable Long idEntidad,
                                                   @PathVariable String busqueda,
                                                   @PathVariable String especie,
                                                   @PathVariable String edad)
    {
        return mascotaServicio.filtrarMascotasPorEntidad(
                idEntidad,
                busqueda,
                especie,
                edad
        );
    }

    @PostMapping("/mascotas/entidad/{idEntidad}")
    public Mascota publicarMascota(@PathVariable Long idEntidad,
                                   @RequestBody Mascota mascota)
    {
        return mascotaServicio.publicarMascota(idEntidad, mascota);
    }

    @PutMapping("/mascota/{idMascota}")
    public Mascota editarMascota(@PathVariable Long idMascota,
                                 @RequestBody Mascota mascota) {
        return mascotaServicio.editarMascota(idMascota, mascota);
    }

}
