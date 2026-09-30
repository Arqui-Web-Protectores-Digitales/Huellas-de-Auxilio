package com.upc.huellasdeauxilio.controladores;


import com.upc.huellasdeauxilio.dtos.MascotaDTO;
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
    public List<MascotaDTO> listarMascotasDisponibles()
    {
        return mascotaServicio.listarDisponibles();
    }

    @GetMapping("/mascota/{idMascota}")
    public MascotaDTO buscarPorId(
            @PathVariable("idMascota") Long idMascota
    ) {
        return mascotaServicio.buscarPorId(idMascota);
    }

    @GetMapping("/mascotas/filtrar/{nombre}/{especie}/{edad}/{distrito}")
    public List<MascotaDTO> filtrarMascotas(@PathVariable String nombre,
                                            @PathVariable String especie,
                                            @PathVariable String edad,
                                            @PathVariable String distrito) {
        return mascotaServicio.filtrarMascotas(
                nombre,
                especie,
                edad,
                distrito
        );
    }

    @GetMapping("/mascotas/entidad/{idEntidad}")
    public List<MascotaDTO> listarMascotasPorEntidad(@PathVariable Long idEntidad)
    {
        return mascotaServicio.listarDisponiblesPorEntidad(idEntidad);
    }

    @GetMapping("/mascotas/entidad/{idEntidad}/filtrar/{busqueda}/{especie}/{edad}")
    public List<MascotaDTO> filtrarMascotasPorEntidad(@PathVariable Long idEntidad,
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
    public MascotaDTO publicarMascota(@PathVariable Long idEntidad,
                                      @RequestBody MascotaDTO mascotaDTO)
    {
        return mascotaServicio.publicarMascota(idEntidad, mascotaDTO);
    }

    @PutMapping("/mascota/{idMascota}")
    public MascotaDTO editarMascota(@PathVariable Long idMascota,
                                    @RequestBody MascotaDTO mascotaDTO) {
        return mascotaServicio.editarMascota(idMascota, mascotaDTO);
    }

}
