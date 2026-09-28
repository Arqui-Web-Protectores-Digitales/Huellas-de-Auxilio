package com.upc.huellasdeauxilio.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class MascotaDTO {
    private Long idMascota;
    private Long entidadIdEntidad;
    private String nombre;
    private String edad;
    private String sexo;
    private String distrito;
    private String tamaño;
    private Boolean estado;
    private String especie;
    private String urlFoto;
    private String descripcion;
}
