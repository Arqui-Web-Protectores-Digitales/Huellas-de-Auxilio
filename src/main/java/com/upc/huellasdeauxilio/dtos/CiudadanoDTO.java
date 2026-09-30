package com.upc.huellasdeauxilio.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CiudadanoDTO {

    private Long idCiudadano;
    private String dni;
    private String distrito;
    private String nombreCompleto;

    private Long idUsuario;
}