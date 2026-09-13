package com.upc.huellasdeauxilio.repositorios;

import com.upc.huellasdeauxilio.entidades.Reporte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReporteRepositorio extends JpaRepository<Reporte, Long> {

    List<Reporte> findByCiudadano_IdCiudadano(Long idCiudadano);

    Reporte findByIdReporteAndCiudadano_IdCiudadano(Long idReporte, Long idCiudadano);

    long countByCiudadano_IdCiudadano(Long idCiudadano);
    long countByCiudadano_IdCiudadanoAndEstado(Long idCiudadano, String estado);

    @Query("SELECT r FROM Reporte r WHERE r.ciudadano.idCiudadano = :idCiudadano " +
            "AND (:estado IS NULL OR r.estado = :estado) " +
            "AND (:urgencia IS NULL OR r.nivelUrgencia = :urgencia) " +
            "AND (:distrito IS NULL OR r.ubicacion.distrito = :distrito)")
    List<Reporte> filtrarReportes(
            @Param("idCiudadano") Long idCiudadano,
            @Param("estado") String estado,
            @Param("urgencia") String urgencia,
            @Param("distrito") String distrito
    );
}
