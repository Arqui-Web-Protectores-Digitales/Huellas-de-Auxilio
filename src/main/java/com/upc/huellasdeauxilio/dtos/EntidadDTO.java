package com.upc.huellasdeauxilio.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EntidadDTO {

    private Long idEntidad;
    private String nombreEntidad;
    private String tipoEntidad;
    private String zonaAtencion;
    private String sitioWeb;
    private LocalTime fechaAtencionInicio;
    private LocalTime fechaAtencionFinal;
    private String diasAtencion;
    private Float latitud;
    private Float longitud;

    private Long idUsuario;
}