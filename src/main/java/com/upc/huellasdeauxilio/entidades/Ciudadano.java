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
public class Ciudadano {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCiudadano;

    private String dni;
    private String alias;
    private String distrito;
    private String nombreCompleto;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;
}