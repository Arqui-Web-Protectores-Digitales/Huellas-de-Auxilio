package com.upc.huellasdeauxilio.repositorios;

import com.upc.huellasdeauxilio.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, Long> {

    Usuario findByCorreo(String correo);

    Usuario findByCorreoAndContraseña(String correo, String contraseña);
}