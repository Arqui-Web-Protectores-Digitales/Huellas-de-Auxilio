package com.upc.huellasdeauxilio.controladores;

import com.upc.huellasdeauxilio.entidades.Entidad;
import com.upc.huellasdeauxilio.servicios.EntidadServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/entidad/zona/{zonaAtencion}")
    public Entidad buscarPorZonaAtencion(@PathVariable String zonaAtencion) {
        return entidadServicio.buscarPorZonaAtencion(zonaAtencion);
    }
}