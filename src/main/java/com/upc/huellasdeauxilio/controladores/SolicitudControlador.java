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
    public Solicitud buscarPorCodigoYCiudadano(@PathVariable Long idCiudadano, @PathVariable Long idSolicitud)
    {
        return solicitudServicio.buscarPorCodigoYCiudadano(idCiudadano,idSolicitud);
    }

    @GetMapping("/solicitudes/ciudadano/{idCiudadano}/filtrar/{nombreMascota}/{estado}")
    public List<Solicitud> filtrarSolicitudes(@PathVariable Long idCiudadano,
                                              @PathVariable String nombreMascota,
                                              @PathVariable String estado)
    {
        return solicitudServicio.filtrarSolicitudes(idCiudadano,nombreMascota,estado);
    }
}

