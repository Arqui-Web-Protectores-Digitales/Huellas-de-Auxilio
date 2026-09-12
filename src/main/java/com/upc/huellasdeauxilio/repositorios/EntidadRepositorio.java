package com.upc.huellasdeauxilio.repositorios;

import com.upc.huellasdeauxilio.entidades.Entidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EntidadRepositorio extends JpaRepository<Entidad, Long> {

    Entidad findByUsuario_IdUsuario(Long idUsuario);

    Entidad findByZonaAtencion(String zonaAtencion);
}