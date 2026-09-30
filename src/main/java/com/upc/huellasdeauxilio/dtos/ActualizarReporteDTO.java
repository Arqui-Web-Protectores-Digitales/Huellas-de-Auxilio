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
public class ActualizarReporteDTO {
    private Long idActualizacion;
    private String descripcionActu;
    private LocalDateTime fechaActualizacion;

    private Long reporteIdReporte;
}
