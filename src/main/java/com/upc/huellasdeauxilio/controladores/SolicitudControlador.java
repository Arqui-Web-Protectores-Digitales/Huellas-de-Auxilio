package com.upc.huellasdeauxilio.controladores;

import com.upc.huellasdeauxilio.entidades.Solicitud;
import com.upc.huellasdeauxilio.servicios.SolicitudServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class SolicitudControlador {
    @Autowired
    private SolicitudServicio solicitudServicio;

    @PostMapping("/solicitud/ciudadano/{idCiudadano}")
    public Solicitud insertar(@PathVariable Long idCiudadano, @RequestBody Solicitud solicitud)
    {
        return solicitudServicio.insertar(idCiudadano,solicitud);
    }

    @GetMapping("/solicitudes/ciudadano/{idCiudadano}")
    public List<Solicitud> listarPorCiudadano(@PathVariable Long idCiudadano)
    {
        return solicitudServicio.listarPorCiudadano(idCiudadano);
    }

    @GetMapping("/solicitud/ciudadano/{idCiudadano}/{idSolicitud}")
    public Solicitud buscarPorCodigoYCiudadano(
            @PathVariable Long idCiudadano,
            @PathVariable Long idSolicitud)
    {
        return solicitudServicio.buscarPorCodigoYCiudadano(
                idSolicitud,
                idCiudadano
        );
    }

    @GetMapping("/solicitudes/ciudadano/{idCiudadano}/filtrar/{nombreMascota}/{estado}")
    public List<Solicitud> filtrarSolicitudes(@PathVariable Long idCiudadano,
                                              @PathVariable String nombreMascota,
                                              @PathVariable String estado)
    {
        return solicitudServicio.filtrarSolicitudes(idCiudadano,nombreMascota,estado);
    }

    @GetMapping("/solicitudes/mascota/{idMascota}")
    public List<Solicitud> listarPorMascota(@PathVariable Long idMascota) {
        return solicitudServicio.listarPorMascota(idMascota);
    }

    @GetMapping("/solicitud/mascota/{idMascota}/{idSolicitud}")
    public Solicitud buscarPorSolicitudYMascota(
            @PathVariable Long idMascota,
            @PathVariable Long idSolicitud
    ) {
        return solicitudServicio.buscarPorSolicitudYMascota(
                idSolicitud,
                idMascota
        );
    }
    @GetMapping("/solicitudes/entidad/{idEntidad}")
    public List<Solicitud> listarPorEntidad(@PathVariable Long idEntidad) {
        return solicitudServicio.listarPorEntidad(idEntidad);
    }

    @GetMapping("/solicitudes/mascota/{idMascota}/filtrar/{codigo}/{estado}")
    public List<Solicitud> filtrarSolicitudesPorMascota(
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
    public List<Solicitud> filtrarSolicitudesPorEntidad(
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
    public Solicitud rechazarSolicitud(@PathVariable Long idSolicitud) {
        return solicitudServicio.rechazarSolicitud(idSolicitud);
    }

    @PutMapping("/solicitud/{idSolicitud}/aprobar")
    public Solicitud aprobarSolicitud(@PathVariable Long idSolicitud) {
        return solicitudServicio.aprobarSolicitud(idSolicitud);
    }
}

