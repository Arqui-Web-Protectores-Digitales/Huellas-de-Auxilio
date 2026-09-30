package com.upc.huellasdeauxilio.controladores;

import com.upc.huellasdeauxilio.dtos.NotificacionDTO;
import com.upc.huellasdeauxilio.entidades.Notificacion;
import com.upc.huellasdeauxilio.servicios.NotificacionServicio;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class NotificacionControlador {

    @Autowired
    private NotificacionServicio notificacionServicio;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/notificacion")
    public NotificacionDTO insertar(@RequestBody NotificacionDTO notificacionDTO) {
        Notificacion notificacion = modelMapper.map(notificacionDTO, Notificacion.class);
        notificacion = notificacionServicio.insertar(notificacion);
        return modelMapper.map(notificacion, NotificacionDTO.class);
    }

    @GetMapping("/notificaciones/usuario/{idUsuario}")
    public List<NotificacionDTO> listarPorUsuario(@PathVariable Long idUsuario) {
        List<Notificacion> notificaciones = notificacionServicio.listarPorUsuario(idUsuario);
        return notificaciones.stream()
                .map(n -> modelMapper.map(n, NotificacionDTO.class))
                .collect(Collectors.toList());
    }

    @PutMapping("/notificacion/leer/{idNotificacion}")
    public NotificacionDTO marcarComoLeida(@PathVariable Long idNotificacion) {
        Notificacion notificacion = notificacionServicio.marcarComoLeida(idNotificacion);
        return (notificacion != null) ? modelMapper.map(notificacion, NotificacionDTO.class) : null;
    }

}
