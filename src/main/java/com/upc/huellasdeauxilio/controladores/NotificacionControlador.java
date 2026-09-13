package com.upc.huellasdeauxilio.controladores;

import com.upc.huellasdeauxilio.entidades.Notificacion;
import com.upc.huellasdeauxilio.servicios.NotificacionServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class NotificacionControlador {

    @Autowired
    private NotificacionServicio notificacionServicio;

    @PostMapping("/notificacion")
    public Notificacion insertar(@RequestBody Notificacion notificacion) {
        return notificacionServicio.insertar(notificacion);
    }

    @GetMapping("/notificaciones/usuario/{idUsuario}")
    public List<Notificacion> listarPorUsuario(@PathVariable Long idUsuario) {
        return notificacionServicio.listarPorUsuario(idUsuario);
    }

    @PutMapping("/notificacion/leer/{idNotificacion}")
    public Notificacion marcarComoLeida(@PathVariable Long idNotificacion) {
        return notificacionServicio.marcarComoLeida(idNotificacion);
    }


}
