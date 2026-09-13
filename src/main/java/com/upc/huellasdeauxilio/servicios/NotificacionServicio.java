package com.upc.huellasdeauxilio.servicios;

import com.upc.huellasdeauxilio.entidades.Notificacion;
import com.upc.huellasdeauxilio.repositorios.NotificacionRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificacionServicio {

    @Autowired
    private NotificacionRepositorio notificacionRepositorio;

    public Notificacion insertar(Notificacion notificacion) {

        notificacion.setEstadoNotificacion(false);

        return notificacionRepositorio.save(notificacion);
    }
}
