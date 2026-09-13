package com.upc.huellasdeauxilio.repositorios;

import com.upc.huellasdeauxilio.entidades.EvidenciaReporte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EvidenciaReporteRepositorio
        extends JpaRepository<EvidenciaReporte, Long> {
    List<EvidenciaReporte> findByReporte_IdReporte(Long idReporte);
}
