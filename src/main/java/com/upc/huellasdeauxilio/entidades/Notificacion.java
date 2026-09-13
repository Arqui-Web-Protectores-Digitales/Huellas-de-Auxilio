package com.upc.huellasdeauxilio.entidades;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Notificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idNotificacion;

    @ManyToOne
    @JoinColumn(name = "id_reporte")
    private Reporte reporte;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    private String descripcion;
    private Boolean estadoNotificacion;
    private String titulo;
}
