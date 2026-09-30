package com.upc.huellasdeauxilio.entidades;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Solicitud {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSolicitud;

    //Holi aqui le puse las restricciones en falso ya que modelar una relacion de 0 a muchos no se puede directamente
    //la restriccion sirve para asegurar que una solicitud no pueda existir sin estar relacionada a una mascota y ciudadano.
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_mascota", nullable = false)
    private Mascota mascota;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_ciudadano", nullable = false)
    private Ciudadano ciudadano;

    private LocalDateTime fechaSolicitud;
    private String tipoVivienda;
    private String motivo;
    private Boolean experiencia;
    private String estadoSolicitud;

}
