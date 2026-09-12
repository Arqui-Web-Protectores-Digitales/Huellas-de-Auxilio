package com.upc.huellasdeauxilio.controladores;

import com.upc.huellasdeauxilio.entidades.TipoEntidad;
import com.upc.huellasdeauxilio.servicios.TipoEntidadServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class TipoEntidadControlador {

    @Autowired
    private TipoEntidadServicio tipoEntidadServicio;

    @PostMapping("/tipoentidad")
    public TipoEntidad insertar(@RequestBody TipoEntidad tipoEntidad) {
        return tipoEntidadServicio.insertar(tipoEntidad);
    }
}