package com.upc.huellasdeauxilio.repositorios;

import com.upc.huellasdeauxilio.entidades.EvidenciaReporte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EvidenciaReporteRepositorio
        extends JpaRepository<EvidenciaReporte, Long> {
}
