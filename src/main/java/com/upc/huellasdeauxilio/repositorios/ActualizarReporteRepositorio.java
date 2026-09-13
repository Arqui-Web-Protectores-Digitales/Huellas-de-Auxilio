package com.upc.huellasdeauxilio.repositorios;

import com.upc.huellasdeauxilio.entidades.ActualizarReporte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActualizarReporteRepositorio
        extends JpaRepository<ActualizarReporte, Long> {
}
