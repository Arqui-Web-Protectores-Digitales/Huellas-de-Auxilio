package com.upc.huellasdeauxilio.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UbicacionDTO {
    private Long idUbicacion;
    private String direccion;
    private String distrito;
    private String referencia;
    private Float latitud;
    private Float longitud;
}
