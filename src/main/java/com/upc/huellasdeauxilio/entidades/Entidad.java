package com.upc.huellasdeauxilio.entidades;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;
}