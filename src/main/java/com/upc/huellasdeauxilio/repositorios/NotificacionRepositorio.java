package com.upc.huellasdeauxilio.repositorios;

import com.upc.huellasdeauxilio.entidades.Notificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificacionRepositorio
        extends JpaRepository<Notificacion, Long> {
    List<Notificacion> findByUsuario_IdUsuarioOrderByFechaNotificacionDesc(Long idUsuario);

}
