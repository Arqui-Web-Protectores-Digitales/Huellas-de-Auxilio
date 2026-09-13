package com.upc.huellasdeauxilio.controladores;

import com.upc.huellasdeauxilio.entidades.Notificacion;
import com.upc.huellasdeauxilio.servicios.NotificacionServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class NotificacionControlador {

    @Autowired
    private NotificacionServicio notificacionServicio;

    @PostMapping("/notificacion")
    public Notificacion insertar(@RequestBody Notificacion notificacion) {
        return notificacionServicio.insertar(notificacion);
    }
}
