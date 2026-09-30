package com.upc.huellasdeauxilio.repositorios;

import com.upc.huellasdeauxilio.entidades.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolRepositorio extends JpaRepository<Rol, Long> {
}