package com.upc.huellasdeauxilio.controladores;

import com.upc.huellasdeauxilio.entidades.Entidad;
import com.upc.huellasdeauxilio.servicios.EntidadServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
}