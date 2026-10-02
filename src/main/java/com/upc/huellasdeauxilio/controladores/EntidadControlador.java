package com.upc.huellasdeauxilio.controladores;

import com.upc.huellasdeauxilio.dtos.EntidadDTO;
import com.upc.huellasdeauxilio.entidades.Entidad;
import com.upc.huellasdeauxilio.entidades.Usuario;
import com.upc.huellasdeauxilio.servicios.EntidadServicio;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class EntidadControlador {

    @Autowired
    private EntidadServicio entidadServicio;

    @Autowired
    private ModelMapper modelMapper;

    private Entidad convertirAEntidad(EntidadDTO dto) {

        Entidad entidad =
                modelMapper.map(dto, Entidad.class);

        if (dto.getIdUsuario() != null) {

            Usuario usuario = new Usuario();

            usuario.setIdUsuario(
                    dto.getIdUsuario()
            );

            entidad.setUsuario(usuario);
        }

        return entidad;
    }

    private EntidadDTO convertirADTO(Entidad entidad) {

        if (entidad == null) {
            return null;
        }

        EntidadDTO dto =
                modelMapper.map(
                        entidad,
                        EntidadDTO.class
                );

        if (entidad.getUsuario() != null) {

            dto.setIdUsuario(
                    entidad
                            .getUsuario()
                            .getIdUsuario()
            );
        }

        return dto;
    }

    @PostMapping("/entidad")
    public EntidadDTO insertar(
            @RequestBody EntidadDTO entidadDTO) {

        Entidad entidad =
                convertirAEntidad(entidadDTO);

        entidad =
                entidadServicio.insertar(entidad);

        return convertirADTO(entidad);
    }

    @GetMapping("/entidad/usuario/{idUsuario}")
    @PreAuthorize("hasRole('ENTIDAD')")
    public EntidadDTO buscarPorUsuario(
            @PathVariable Long idUsuario) {

        Entidad entidad =
                entidadServicio.buscarPorUsuario(
                        idUsuario
                );

        return convertirADTO(entidad);
    }

    @GetMapping("/entidades/zona/{zonaAtencion}")
    @PreAuthorize("hasAnyRole('CIUDADANO', 'ENTIDAD')")
    public List<EntidadDTO> buscarPorZonaAtencion(
            @PathVariable String zonaAtencion) {

        List<Entidad> entidades =
                entidadServicio.buscarPorZonaAtencion(
                        zonaAtencion
                );

        return entidades.stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/entidades")
    @PreAuthorize("hasAnyRole('CIUDADANO', 'ENTIDAD')")
    public List<EntidadDTO> listarTodas() {

        List<Entidad> entidades =
                entidadServicio.listarTodas();

        return entidades.stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/entidad/detalle/{idEntidad}")
    @PreAuthorize("hasAnyRole('CIUDADANO', 'ENTIDAD')")
    public EntidadDTO buscarPorId(
            @PathVariable Long idEntidad) {

        Entidad entidad =
                entidadServicio.buscarPorId(
                        idEntidad
                );

        return convertirADTO(entidad);
    }

    @GetMapping("/entidades/nombre/{nombre}")
    @PreAuthorize("hasAnyRole('CIUDADANO', 'ENTIDAD')")
    public List<EntidadDTO> buscarPorNombre(
            @PathVariable String nombre) {

        List<Entidad> entidades =
                entidadServicio.buscarPorNombre(
                        nombre
                );

        return entidades.stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    @PutMapping("/entidades/{Id}")
    @PreAuthorize("hasRole('ENTIDAD')")
    public EntidadDTO modificarEntidad(
            @PathVariable Long Id,
            @RequestBody EntidadDTO entidadDTO) {

        Entidad entidadActualizada =
                entidadServicio.modificarEntidad(
                        Id,
                        entidadDTO.getNombreEntidad(),
                        entidadDTO.getTipoEntidad(),
                        entidadDTO.getZonaAtencion(),
                        entidadDTO.getSitioWeb(),
                        entidadDTO.getFechaAtencionInicio(),
                        entidadDTO.getFechaAtencionFinal(),
                        entidadDTO.getDiasAtencion(),
                        entidadDTO.getLatitud(),
                        entidadDTO.getLongitud()
                );

        return convertirADTO(
                entidadActualizada
        );
    }

    @PutMapping("/entidades/{Id}/contraseñanueva")
    @PreAuthorize("hasRole('ENTIDAD')")
    public void modificarContraseñaEntidad(
            @PathVariable Long Id,
            @RequestBody String contraseñanueva) {

        entidadServicio.modificarContraseñaEntidad(
                Id,
                contraseñanueva
        );
    }

    @GetMapping("/entidades/{Id}")
    @PreAuthorize("hasAnyRole('CIUDADANO', 'ENTIDAD')")
    public EntidadDTO buscarEntidadPorId(
            @PathVariable Long Id) {

        Entidad entidad =
                entidadServicio.buscarEntidadPorId(Id);

        return convertirADTO(entidad);
    }
}