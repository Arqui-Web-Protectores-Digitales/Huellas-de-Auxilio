package com.upc.huellasdeauxilio.repositorios;

import com.upc.huellasdeauxilio.entidades.TipoEntidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoEntidadRepositorio extends JpaRepository<TipoEntidad, Long> {
}