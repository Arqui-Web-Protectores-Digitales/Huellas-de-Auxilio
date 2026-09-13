package com.upc.huellasdeauxilio.entidades;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Entidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEntidad;

    private String nombreEntidad;
    private String zonaAtencion;

    private String sitioWeb;
    private LocalTime fechaAtencionInicio;
    private LocalTime fechaAtencionFinal;
    private String diasAtencion;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;
}