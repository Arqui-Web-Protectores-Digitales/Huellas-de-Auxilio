package com.upc.huellasdeauxilio.repositorios;

import com.upc.huellasdeauxilio.entidades.Mascota;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MascotaRepositorio extends JpaRepository<Mascota, Long> {
    //Estado true se usa para hacerle saber al programa que la mascota se encuentra disponible
    List<Mascota> findByEstadoTrue();

    @Query("""
        SELECT m
        FROM Mascota m
        WHERE m.estado = true
          AND (
              ?1 = 'todos'
              OR LOWER(m.nombre) LIKE CONCAT('%', ?1, '%')
              )
          AND (
              ?2 = 'todos'
              OR LOWER(m.especie) = ?2
              )
          AND (
              ?3 = 'todos'
              OR LOWER(m.edad) = ?3
              )
          AND (
              ?4 = 'todos'
              OR LOWER(m.distrito) = ?4
              )
        ORDER BY m.idMascota DESC
        """)

    List<Mascota> filtrarMascotas(
            String nombre,
            String especie,
            String edad,
            String distrito
    );

    List<Mascota> findByEntidadIdEntidadAndEstadoTrue(Long idEntidad);

    @Query("""
    SELECT m
    FROM Mascota m
    WHERE m.entidad.idEntidad = ?1
      AND m.estado = true
      AND (
          ?2 = 'todos'
          OR LOWER(m.nombre) LIKE CONCAT('%', LOWER(?2), '%')
          )
      AND (
          ?3 = 'todos'
          OR LOWER(m.especie) = LOWER(?3)
          )
      AND (
          ?4 = 'todos'
          OR LOWER(m.edad) = LOWER(?4)
          )
    ORDER BY m.idMascota DESC
    """)
    List<Mascota> filtrarMascotasPorEntidad(
            Long idEntidad,
            String busqueda,
            String especie,
            String edad
    );
}
