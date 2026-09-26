package com.upc.huellasdeauxilio.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReporteDTO {
    private Long idReporte;
    private LocalDate fechaReporte;
    private String tipoCaso;
    private String nivelUrgencia;
    private String descripcion;
    private String estado;

    private UbicacionDTO ubicacion;
    private CiudadanoDTO ciudadano;
    private EntidadDTO entidad;
}
