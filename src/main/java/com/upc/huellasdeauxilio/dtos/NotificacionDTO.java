package com.upc.huellasdeauxilio.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NotificacionDTO {
    private Long idNotificacion;
    private String titulo;
    private String descripcion;
    private Boolean estadoNotificacion;
    private LocalDateTime fechaNotificacion;

    private Long reporteIdReporte;
    private Long solicitudIdSolicitud;
    private Long usuarioIdUsuario;
}
