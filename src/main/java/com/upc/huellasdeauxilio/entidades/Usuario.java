package com.upc.huellasdeauxilio.entidades;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUsuario;

    private String correo;
    private String telefono;
    private String contraseña;
    private Boolean estadoUsuario=true;
}