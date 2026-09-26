package com.upc.huellasdeauxilio.servicios;

import com.upc.huellasdeauxilio.entidades.Entidad;
import com.upc.huellasdeauxilio.entidades.Mascota;
import com.upc.huellasdeauxilio.repositorios.EntidadRepositorio;
import com.upc.huellasdeauxilio.repositorios.MascotaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;


@Service
public class MascotaServicio {
    @Autowired
    private MascotaRepositorio mascotaRepositorio;

    @Autowired
    private EntidadRepositorio entidadRepositorio;

    public List<Mascota> listarDisponibles() {
        return mascotaRepositorio.findByEstadoTrue();
    }

    public Mascota buscarPorId(Long idMascota)
    {
        if(idMascota ==null)
        {
            return null;
        }
        return mascotaRepositorio.findById(idMascota).orElse(null);
    }

    //esta es una funcion extra para quitar especios extra y transformar
    //mayusculas a minusculas y facilitar la filtracion
    private String normalizarFiltro(String valor) {
        if (valor == null || valor.isBlank()) {
            return "todos";
        }

        return valor.trim().toLowerCase(Locale.ROOT);
    }

    public List<Mascota> filtrarMascotas(String nombre,
                                         String especie,
                                         String edad,
                                         String distrito)
    {
        return mascotaRepositorio.filtrarMascotas(
                normalizarFiltro(nombre),
                normalizarFiltro(especie),
                normalizarFiltro(edad),
                normalizarFiltro(distrito));
    }

    public List<Mascota> listarDisponiblesPorEntidad(Long idEntidad) {
        return mascotaRepositorio.findByEntidadIdEntidadAndEstadoTrue(idEntidad);
    }

    public List<Mascota> filtrarMascotasPorEntidad(Long idEntidad,
                                                   String busqueda,
                                                   String especie,
                                                   String edad) {
        return mascotaRepositorio.filtrarMascotasPorEntidad(
                idEntidad,
                normalizarFiltro(busqueda),
                normalizarFiltro(especie),
                normalizarFiltro(edad)
        );

    }
    public Mascota publicarMascota(Long idEntidad, Mascota mascota) {

        if (mascota == null
                || mascota.getNombre() == null
                || mascota.getNombre().isBlank()
                || mascota.getEspecie() == null
                || mascota.getEspecie().isBlank()
                || mascota.getEdad() == null
                || mascota.getEdad().isBlank()
                || mascota.getSexo() == null
                || mascota.getSexo().isBlank()
                || mascota.getDistrito() == null
                || mascota.getDistrito().isBlank()
                || mascota.getTamaño() == null
                || mascota.getTamaño().isBlank()
                || mascota.getDescripcion() == null
                || mascota.getDescripcion().isBlank()
                || mascota.getUrlFoto() == null
                || mascota.getUrlFoto().isBlank()) {
            return null;
        }

        Entidad entidad = entidadRepositorio.findById(idEntidad).orElse(null);

        if (entidad == null) {
            return null;
        }

        mascota.setEntidad(entidad);
        mascota.setEstado(true);

        return mascotaRepositorio.save(mascota);
    }

    public Mascota editarMascota(Long idMascota, Mascota datosMascota) {

        if (datosMascota == null
                || datosMascota.getNombre() == null
                || datosMascota.getNombre().isBlank()
                || datosMascota.getEspecie() == null
                || datosMascota.getEspecie().isBlank()
                || datosMascota.getEdad() == null
                || datosMascota.getEdad().isBlank()
                || datosMascota.getSexo() == null
                || datosMascota.getSexo().isBlank()
                || datosMascota.getDistrito() == null
                || datosMascota.getDistrito().isBlank()
                || datosMascota.getTamaño() == null
                || datosMascota.getTamaño().isBlank()
                || datosMascota.getDescripcion() == null
                || datosMascota.getDescripcion().isBlank()
                || datosMascota.getUrlFoto() == null
                || datosMascota.getUrlFoto().isBlank()) {
            return null;
        }

        Mascota mascota = buscarPorId(idMascota);

        if (mascota == null) {
            return null;
        }

        mascota.setNombre(datosMascota.getNombre());
        mascota.setEspecie(datosMascota.getEspecie());
        mascota.setEdad(datosMascota.getEdad());
        mascota.setSexo(datosMascota.getSexo());
        mascota.setDistrito(datosMascota.getDistrito());
        mascota.setTamaño(datosMascota.getTamaño());
        mascota.setDescripcion(datosMascota.getDescripcion());
        mascota.setUrlFoto(datosMascota.getUrlFoto());

        return mascotaRepositorio.save(mascota);
    }

    public Mascota cambiarEstado(Long idMascota, Boolean estado) {

        Mascota mascota = buscarPorId(idMascota);

        if (mascota == null || estado == null) {
            return null;
        }

        mascota.setEstado(estado);

        return mascotaRepositorio.save(mascota);
    }



}
