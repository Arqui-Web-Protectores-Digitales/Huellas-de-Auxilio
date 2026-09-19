package com.upc.huellasdeauxilio.controladores;

import com.upc.huellasdeauxilio.entidades.Entidad;
import com.upc.huellasdeauxilio.servicios.EntidadServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Time;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/api")
public class EntidadControlador {

    @Autowired
    private EntidadServicio entidadServicio;

    @PostMapping("/entidad")
    public Entidad insertar(@RequestBody Entidad entidad) {
        return entidadServicio.insertar(entidad);
    }

    @GetMapping("/entidad/usuario/{idUsuario}")
    public Entidad buscarPorUsuario(@PathVariable Long idUsuario) {
        return entidadServicio.buscarPorUsuario(idUsuario);
    }

    @GetMapping("/entidades/zona/{zonaAtencion}")
    public List<Entidad> buscarPorZonaAtencion(@PathVariable String zonaAtencion) {
        return entidadServicio.buscarPorZonaAtencion(zonaAtencion);
    }

    @GetMapping("/entidades")
    public List<Entidad> listarTodas() {
        return entidadServicio.listarTodas();
    }

    @GetMapping("/entidad/detalle/{idEntidad}")
    public Entidad buscarPorId(@PathVariable Long idEntidad) {
        return entidadServicio.buscarPorId(idEntidad);
    }

    @GetMapping("/entidades/nombre/{nombre}")
    public List<Entidad> buscarPorNombre(@PathVariable String nombre) {
        return entidadServicio.buscarPorNombre(nombre);
    }

    //Modificar una entidad ya existente
    @PutMapping("/entidades/{Id}")
    public Entidad modificarEntidad(
            @PathVariable Long Id,
            @RequestBody Entidad entidad) {

        return entidadServicio.modificarEntidad(
                Id,
                entidad.getNombreEntidad(),
                entidad.getTipoEntidad(),
                entidad.getZonaAtencion(),
                entidad.getSitioWeb(),
                entidad.getFechaAtencionInicio(),
                entidad.getFechaAtencionFinal(),
                entidad.getDiasAtencion(),
                entidad.getLatitud(),
                entidad.getLongitud()
        );
    }
    //Modificar la contraseña de un usuario perteneciente a una ENTIDAD EXISTENTE
    @PutMapping("/entidades/{Id}/contraseñanueva")
    public void modificarContraseñaEntidad(@PathVariable Long Id, @RequestBody String contraseñanueva){
        entidadServicio.modificarContraseñaEntidad(Id, contraseñanueva);
    }

}