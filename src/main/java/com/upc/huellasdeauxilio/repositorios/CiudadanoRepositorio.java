package com.upc.huellasdeauxilio.repositorios;

import com.upc.huellasdeauxilio.entidades.Ciudadano;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CiudadanoRepositorio extends JpaRepository<Ciudadano, Long> {

    Ciudadano findByDni(String dni);
}