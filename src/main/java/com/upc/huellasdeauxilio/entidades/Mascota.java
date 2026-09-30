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
public class Mascota {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMascota;

    @ManyToOne
    @JoinColumn(name ="id_entidad")
    private Entidad entidad;

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
