package com.upc.huellasdeauxilio.repositorios;

import com.upc.huellasdeauxilio.entidades.Ubicacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UbicacionRepositorio extends JpaRepository<Ubicacion, Long> {
}