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
public class EvidenciaReporte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEvidencia;

    @ManyToOne
    @JoinColumn(name = "id_reporte")
    private Reporte reporte;

    private String urlEvidencia;
}
