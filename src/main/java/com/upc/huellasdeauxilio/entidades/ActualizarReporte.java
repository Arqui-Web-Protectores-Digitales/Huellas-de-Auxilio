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
public class ActualizarReporte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idActualizacion;

    @ManyToOne
    @JoinColumn(name = "id_reporte")
    private Reporte reporte;

    private String descripcionActu;

    private LocalDateTime fechaActualizacion;
}