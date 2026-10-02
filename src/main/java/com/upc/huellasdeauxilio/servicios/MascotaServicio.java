package com.upc.huellasdeauxilio.servicios;

import com.upc.huellasdeauxilio.dtos.MascotaDTO;
import com.upc.huellasdeauxilio.entidades.Entidad;
import com.upc.huellasdeauxilio.entidades.Mascota;
import com.upc.huellasdeauxilio.repositorios.EntidadRepositorio;
import com.upc.huellasdeauxilio.repositorios.MascotaRepositorio;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;


@Service
public class MascotaServicio {
    @Autowired
    private MascotaRepositorio mascotaRepositorio;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private EntidadRepositorio entidadRepositorio;

    public List<MascotaDTO> listarDisponibles() {
        return mascotaRepositorio.findByEstadoTrue().stream().
                map(mascota -> modelMapper.map(mascota, MascotaDTO.class)).
                collect(Collectors.toList());
    }

    public MascotaDTO buscarPorId(Long idMascota)
    {
        if (idMascota == null)
        {
            return null;
        }

        Mascota mascota = mascotaRepositorio.findById(idMascota).orElse(null);

        if (mascota == null)
        {
            return null;
        }

        MascotaDTO dto = modelMapper.map(mascota, MascotaDTO.class);

        if (mascota.getEntidad() != null) {dto.setEntidadIdEntidad(mascota.getEntidad().getIdEntidad()
        );
        }

        return dto;
    }

    //esta es una funcion extra para quitar especios extra y transformar
    //mayusculas a minusculas y facilitar la filtracion
    private String normalizarFiltro(String valor) {
        if (valor == null || valor.isBlank()) {
            return "todos";
        }

        return valor.trim().toLowerCase(Locale.ROOT);
    }

    public List<MascotaDTO> filtrarMascotas(String nombre, String especie,
                                            String edad, String distrito) {

        return mascotaRepositorio.filtrarMascotas(
                        normalizarFiltro(nombre),
                        normalizarFiltro(especie),
                        normalizarFiltro(edad),
                        normalizarFiltro(distrito)
                )
                .stream()
                .map(mascota -> {

                    MascotaDTO dto = modelMapper.map(
                            mascota,
                            MascotaDTO.class
                    );

                    if (mascota.getEntidad() != null) {
                        dto.setEntidadIdEntidad(
                                mascota.getEntidad().getIdEntidad()
                        );
                    }

                    return dto;
                })
                .collect(Collectors.toList());
    }

    public List<MascotaDTO> listarDisponiblesPorEntidad(Long idEntidad) {

        return mascotaRepositorio
                .findByEntidadIdEntidadAndEstadoTrue(idEntidad)
                .stream()
                .map(mascota -> {

                    MascotaDTO dto = modelMapper.map(
                            mascota,
                            MascotaDTO.class
                    );

                    dto.setEntidadIdEntidad(
                            mascota.getEntidad().getIdEntidad()
                    );

                    return dto;
                })
                .collect(Collectors.toList());
    }

    public List<MascotaDTO> filtrarMascotasPorEntidad(Long idEntidad,
                                                      String busqueda,
                                                      String especie,
                                                      String edad) {

        return mascotaRepositorio.filtrarMascotasPorEntidad(
                        idEntidad,
                        normalizarFiltro(busqueda),
                        normalizarFiltro(especie),
                        normalizarFiltro(edad)
                )
                .stream()
                .map(mascota -> {

                    MascotaDTO dto = modelMapper.map(
                            mascota,
                            MascotaDTO.class
                    );

                    dto.setEntidadIdEntidad(
                            mascota.getEntidad().getIdEntidad()
                    );

                    return dto;
                })
                .collect(Collectors.toList());
    }

    public MascotaDTO publicarMascota(Long idEntidad, MascotaDTO mascotaDTO) {

        if (idEntidad == null
                || mascotaDTO == null
                || mascotaDTO.getNombre() == null
                || mascotaDTO.getNombre().isBlank()
                || mascotaDTO.getEspecie() == null
                || mascotaDTO.getEspecie().isBlank()
                || mascotaDTO.getEdad() == null
                || mascotaDTO.getEdad().isBlank()
                || mascotaDTO.getSexo() == null
                || mascotaDTO.getSexo().isBlank()
                || mascotaDTO.getDistrito() == null
                || mascotaDTO.getDistrito().isBlank()
                || mascotaDTO.getTamaño() == null
                || mascotaDTO.getTamaño().isBlank()
                || mascotaDTO.getDescripcion() == null
                || mascotaDTO.getDescripcion().isBlank()
                || mascotaDTO.getUrlFoto() == null
                || mascotaDTO.getUrlFoto().isBlank()) {

            return null;
        }

        Entidad entidad = entidadRepositorio.findById(idEntidad).orElse(null);

        if (entidad == null) {
            return null;
        }

        // Convertir DTO a entidad
        Mascota mascota = modelMapper.map(mascotaDTO, Mascota.class);

        // Estos datos los establece el backend, no el cliente
        mascota.setIdMascota(null);
        mascota.setEntidad(entidad);
        mascota.setEstado(true);

        // Guardar en la base de datos
        Mascota mascotaGuardada = mascotaRepositorio.save(mascota);

        // Convertir la entidad guardada a DTO
        MascotaDTO respuesta = modelMapper.map(
                mascotaGuardada,
                MascotaDTO.class
        );

        respuesta.setEntidadIdEntidad(entidad.getIdEntidad());

        return respuesta;
    }

    public MascotaDTO editarMascota(Long idMascota, MascotaDTO datosMascota) {

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

        if (idMascota == null) {
            return null;
        }

        Mascota mascota = mascotaRepositorio
                .findById(idMascota)
                .orElse(null);

        if (mascota == null) {
            return null;
        }

        // Actualizar únicamente los atributos editables
        mascota.setNombre(datosMascota.getNombre());
        mascota.setEspecie(datosMascota.getEspecie());
        mascota.setEdad(datosMascota.getEdad());
        mascota.setSexo(datosMascota.getSexo());
        mascota.setDistrito(datosMascota.getDistrito());
        mascota.setTamaño(datosMascota.getTamaño());
        mascota.setDescripcion(datosMascota.getDescripcion());
        mascota.setUrlFoto(datosMascota.getUrlFoto());

        // Guardar los cambios
        Mascota mascotaActualizada = mascotaRepositorio.save(mascota);

        // Convertir la entidad actualizada a DTO
        MascotaDTO respuesta = modelMapper.map(
                mascotaActualizada,
                MascotaDTO.class
        );

        respuesta.setEntidadIdEntidad(
                mascotaActualizada.getEntidad().getIdEntidad()
        );

        return respuesta;
    }

    public Mascota cambiarEstado(Long idMascota, Boolean estado) {

        if (idMascota == null) {
            return null;
        }

        Mascota mascota = mascotaRepositorio
                .findById(idMascota)
                .orElse(null);

        if (mascota == null || estado == null) {
            return null;
        }

        mascota.setEstado(estado);

        return mascotaRepositorio.save(mascota);
    }



}
