package com.upc.huellasdeauxilio.dtos;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SolicitudDTO {
    private Long idSolicitud;
    private Long mascotaIdMascota;
    private Long ciudadanoIdCiudadano;

    private LocalDateTime fechaSolicitud;
    private String tipoVivienda;
    private String motivo;
    private Boolean experiencia;
    private String estadoSolicitud;


}
