package com.upc.huellasdeauxilio.controladores;

import com.upc.huellasdeauxilio.dtos.EntidadDTO;
import com.upc.huellasdeauxilio.entidades.Entidad;
import com.upc.huellasdeauxilio.servicios.EntidadServicio;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class EntidadControlador {

    @Autowired
    private EntidadServicio entidadServicio;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/entidad")
    public EntidadDTO insertar(@RequestBody EntidadDTO entidadDTO) {
        Entidad entidad = modelMapper.map(entidadDTO, Entidad.class);
        entidad = entidadServicio.insertar(entidad);
        return modelMapper.map(entidad, EntidadDTO.class);
    }

    @GetMapping("/entidad/usuario/{idUsuario}")
    public EntidadDTO buscarPorUsuario(@PathVariable Long idUsuario) {
        Entidad entidad = entidadServicio.buscarPorUsuario(idUsuario);
        return (entidad != null) ? modelMapper.map(entidad, EntidadDTO.class) : null;
    }

    @GetMapping("/entidades/zona/{zonaAtencion}")
    public List<EntidadDTO> buscarPorZonaAtencion(@PathVariable String zonaAtencion) {
        List<Entidad> entidades = entidadServicio.buscarPorZonaAtencion(zonaAtencion);
        return entidades.stream()
                .map(e -> modelMapper.map(e, EntidadDTO.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/entidades")
    public List<EntidadDTO> listarTodas() {
        List<Entidad> entidades = entidadServicio.listarTodas();
        return entidades.stream()
                .map(e -> modelMapper.map(e, EntidadDTO.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/entidad/detalle/{idEntidad}")
    public EntidadDTO buscarPorId(@PathVariable Long idEntidad) {
        Entidad entidad = entidadServicio.buscarPorId(idEntidad);
        return (entidad != null) ? modelMapper.map(entidad, EntidadDTO.class) : null;
    }

    @GetMapping("/entidades/nombre/{nombre}")
    public List<EntidadDTO> buscarPorNombre(@PathVariable String nombre) {
        List<Entidad> entidades = entidadServicio.buscarPorNombre(nombre);
        return entidades.stream()
                .map(e -> modelMapper.map(e, EntidadDTO.class))
                .collect(Collectors.toList());
    }

    @PutMapping("/entidades/{Id}")
    public EntidadDTO modificarEntidad(
            @PathVariable Long Id,
            @RequestBody EntidadDTO entidadDTO) {

        Entidad entidadActualizada = entidadServicio.modificarEntidad(
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
        return (entidadActualizada != null) ? modelMapper.map(entidadActualizada, EntidadDTO.class) : null;
    }

    @PutMapping("/entidades/{Id}/contraseñanueva")
    public void modificarContraseñaEntidad(@PathVariable Long Id, @RequestBody String contraseñanueva){
        entidadServicio.modificarContraseñaEntidad(Id, contraseñanueva);
    }

    @GetMapping ("/entidades/{Id}")
    public EntidadDTO buscarEntidadPorId(@PathVariable Long Id){
        Entidad entidad = entidadServicio.buscarEntidadPorId(Id);
        return (entidad != null) ? modelMapper.map(entidad, EntidadDTO.class) : null;
    }

}