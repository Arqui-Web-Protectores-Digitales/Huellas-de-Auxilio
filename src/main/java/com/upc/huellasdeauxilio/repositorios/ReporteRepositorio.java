package com.upc.huellasdeauxilio.repositorios;

import com.upc.huellasdeauxilio.entidades.Reporte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReporteRepositorio extends JpaRepository<Reporte, Long> {
}
