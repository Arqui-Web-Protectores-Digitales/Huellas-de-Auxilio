package com.upc.huellasdeauxilio.controladores;

import com.upc.huellasdeauxilio.dtos.CiudadanoDTO;
import com.upc.huellasdeauxilio.entidades.Ciudadano;
import com.upc.huellasdeauxilio.entidades.Usuario;
import com.upc.huellasdeauxilio.servicios.CiudadanoServicio;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CiudadanoControlador {

    @Autowired
    private CiudadanoServicio ciudadanoServicio;

    @Autowired
    private ModelMapper modelMapper;

    private Ciudadano convertirAEntidad(
            CiudadanoDTO dto) {

        Ciudadano ciudadano =
                modelMapper.map(dto, Ciudadano.class);

        if (dto.getUsuarioIdUsuario() != null) {

            Usuario usuario = new Usuario();

            usuario.setIdUsuario(
                    dto.getUsuarioIdUsuario()
            );

            ciudadano.setUsuario(usuario);
        }

        return ciudadano;
    }

    private CiudadanoDTO convertirADTO(
            Ciudadano ciudadano) {

        if (ciudadano == null) {
            return null;
        }

        CiudadanoDTO dto =
                modelMapper.map(
                        ciudadano,
                        CiudadanoDTO.class
                );

        if (ciudadano.getUsuario() != null) {

            dto.setUsuarioIdUsuario(
                    ciudadano
                            .getUsuario()
                            .getIdUsuario()
            );
        }

        return dto;
    }

    // HU01
    @PostMapping("/ciudadano")
    public CiudadanoDTO insertar(
            @RequestBody CiudadanoDTO ciudadanoDTO) {

        Ciudadano ciudadano =
                convertirAEntidad(ciudadanoDTO);

        ciudadano =
                ciudadanoServicio.insertar(ciudadano);

        return convertirADTO(ciudadano);
    }

    // HU01
    @GetMapping("/ciudadano/dni/{dni}")
    public CiudadanoDTO buscarPorDni(
            @PathVariable String dni) {

        return convertirADTO(
                ciudadanoServicio.buscarPorDni(dni)
        );
    }

    @GetMapping("/ciudadano/usuario/{idUsuario}")
    public CiudadanoDTO buscarPorUsuario(
            @PathVariable Long idUsuario) {

        return convertirADTO(
                ciudadanoServicio.buscarPorUsuario(
                        idUsuario
                )
        );
    }

    @GetMapping("/ciudadano/perfil/{idCiudadano}")
    public CiudadanoDTO obtenerPerfil(
            @PathVariable Long idCiudadano) {

        return convertirADTO(
                ciudadanoServicio.obtenerPerfil(
                        idCiudadano
                )
        );
    }

    @PutMapping("/ciudadano/perfil/{idCiudadano}")
    public String actualizarPerfil(
            @PathVariable Long idCiudadano,
            @RequestBody CiudadanoDTO ciudadanoDTO) {

        Ciudadano ciudadano =
                convertirAEntidad(ciudadanoDTO);

        Ciudadano actualizado =
                ciudadanoServicio.actualizarPerfil(
                        idCiudadano,
                        ciudadano
                );

        if (actualizado == null) {
            return "No se pudo actualizar el perfil";
        }

        return "Perfil actualizado correctamente";
    }
}