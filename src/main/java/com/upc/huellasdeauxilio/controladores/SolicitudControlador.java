package com.upc.huellasdeauxilio.controladores;

import com.upc.huellasdeauxilio.dtos.SolicitudDTO;
import com.upc.huellasdeauxilio.servicios.SolicitudServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

@RestController
@RequestMapping("/api")
public class SolicitudControlador {
    @Autowired
    private SolicitudServicio solicitudServicio;

    @PostMapping("/solicitud/ciudadano/{idCiudadano}")
    @PreAuthorize("hasRole('CIUDADANO')")
    public SolicitudDTO insertar(@PathVariable Long idCiudadano, @RequestBody SolicitudDTO solicitudDTO)
    {
        return solicitudServicio.insertar(idCiudadano, solicitudDTO);
    }

    @GetMapping("/solicitudes/ciudadano/{idCiudadano}")
    @PreAuthorize("hasRole('CIUDADANO')")
    public List<SolicitudDTO> listarPorCiudadano(@PathVariable Long idCiudadano)
    {
        return solicitudServicio.listarPorCiudadano(idCiudadano);
    }

    @GetMapping("/solicitud/ciudadano/{idCiudadano}/{idSolicitud}")
    @PreAuthorize("hasRole('CIUDADANO')")
    public SolicitudDTO buscarPorCodigoYCiudadano(
            @PathVariable Long idCiudadano,
            @PathVariable Long idSolicitud)
    {
        return solicitudServicio.buscarPorCodigoYCiudadano(
                idSolicitud,
                idCiudadano
        );
    }

    @GetMapping("/solicitudes/ciudadano/{idCiudadano}/filtrar/{nombreMascota}/{estado}")
    @PreAuthorize("hasRole('CIUDADANO')")
    public List<SolicitudDTO> filtrarSolicitudes(@PathVariable Long idCiudadano,
                                              @PathVariable String nombreMascota,
                                              @PathVariable String estado)
    {
        return solicitudServicio.filtrarSolicitudes(idCiudadano,nombreMascota,estado);
    }

    @GetMapping("/solicitudes/mascota/{idMascota}")
    @PreAuthorize("hasAnyRole('CIUDADANO', 'ENTIDAD')")
    public List<SolicitudDTO> listarPorMascota(@PathVariable Long idMascota) {
        return solicitudServicio.listarPorMascota(idMascota);
    }

    @GetMapping("/solicitud/mascota/{idMascota}/{idSolicitud}")
    @PreAuthorize("hasAnyRole('CIUDADANO', 'ENTIDAD')")
    public SolicitudDTO buscarPorSolicitudYMascota(
            @PathVariable Long idMascota,
            @PathVariable Long idSolicitud
    ) {
        return solicitudServicio.buscarPorSolicitudYMascota(
                idSolicitud,
                idMascota
        );
    }
    @GetMapping("/solicitudes/entidad/{idEntidad}")
    @PreAuthorize("hasRole('ENTIDAD')")
    public List<SolicitudDTO> listarPorEntidad(@PathVariable Long idEntidad) {
        return solicitudServicio.listarPorEntidad(idEntidad);
    }

    @GetMapping("/solicitudes/mascota/{idMascota}/filtrar/{codigo}/{estado}")
    @PreAuthorize("hasAnyRole('CIUDADANO', 'ENTIDAD')")
    public List<SolicitudDTO> filtrarSolicitudesPorMascota(
            @PathVariable Long idMascota,
            @PathVariable String codigo,
            @PathVariable String estado) {

        return solicitudServicio.filtrarSolicitudesPorMascota(
                idMascota,
                codigo,
                estado
        );
    }

    @GetMapping("/solicitudes/entidad/{idEntidad}/filtrar/{codigo}/{estado}/{especie}")
    @PreAuthorize("hasRole('ENTIDAD')")
    public List<SolicitudDTO> filtrarSolicitudesPorEntidad(
            @PathVariable Long idEntidad,
            @PathVariable String codigo,
            @PathVariable String estado,
            @PathVariable String especie
    ) {
        return solicitudServicio.filtrarSolicitudesPorEntidad(
                idEntidad,
                codigo,
                estado,
                especie
        );
    }

    @PutMapping("/solicitud/{idSolicitud}/rechazar")
    @PreAuthorize("hasRole('ENTIDAD')")
    public SolicitudDTO rechazarSolicitud(@PathVariable Long idSolicitud) {
        return solicitudServicio.rechazarSolicitud(idSolicitud);
    }

    @PutMapping("/solicitud/{idSolicitud}/aprobar")
    @PreAuthorize("hasRole('ENTIDAD')")
    public SolicitudDTO aprobarSolicitud(@PathVariable Long idSolicitud) {
        return solicitudServicio.aprobarSolicitud(idSolicitud);
    }
}

