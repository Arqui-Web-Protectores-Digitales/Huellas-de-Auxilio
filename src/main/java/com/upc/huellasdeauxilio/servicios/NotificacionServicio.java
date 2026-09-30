package com.upc.huellasdeauxilio.servicios;

import com.upc.huellasdeauxilio.entidades.Notificacion;
import com.upc.huellasdeauxilio.repositorios.NotificacionRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificacionServicio {

    @Autowired
    private NotificacionRepositorio notificacionRepositorio;

    public Notificacion insertar(Notificacion notificacion) {

        notificacion.setEstadoNotificacion(false);

        return notificacionRepositorio.save(notificacion);
    }

    public List<Notificacion> listarPorUsuario(Long idUsuario) {
        return notificacionRepositorio.findByUsuario_IdUsuarioOrderByFechaNotificacionDesc(idUsuario);
    }

    public Notificacion marcarComoLeida(Long idNotificacion) {
        Notificacion notificacion = notificacionRepositorio.findById(idNotificacion).orElse(null);
        if (notificacion != null) {
            notificacion.setEstadoNotificacion(true); // true = leída
            return notificacionRepositorio.save(notificacion);
        }
        return null;
    }

}
