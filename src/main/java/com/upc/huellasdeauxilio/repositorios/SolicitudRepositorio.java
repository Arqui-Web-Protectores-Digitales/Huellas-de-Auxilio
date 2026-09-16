package com.upc.huellasdeauxilio.repositorios;

import com.upc.huellasdeauxilio.entidades.Solicitud;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SolicitudRepositorio extends JpaRepository<Solicitud, Long> {

    //busca las solicitudes de un ciudadano desde la mas reciente
    List<Solicitud> findByCiudadano_IdCiudadanoOrderByFechaSolicitudDesc(Long ciudadano);

    //esta busca UNA solicitud especifica de el ciudadano
    Solicitud findByIdSolicitudAndCiudadano_IdCiudadano(Long idSolicitud, Long idCiudadano);

    @Query("""
        SELECT s
        FROM Solicitud s
        WHERE s.ciudadano.idCiudadano = ?1
          AND (
              ?2 = 'todos'
              OR LOWER(s.mascota.nombre) LIKE CONCAT('%', ?2, '%')
              )
          AND (
              ?3 = 'todos'
              OR LOWER(s.estadoSolicitud) = ?3
              )
        ORDER BY s.fechaSolicitud DESC, s.idSolicitud DESC
        """)
    List<Solicitud> filtrarSolicitudes(
            Long idCiudadano,
            String nombreMascota,
            String estado
    );
}
