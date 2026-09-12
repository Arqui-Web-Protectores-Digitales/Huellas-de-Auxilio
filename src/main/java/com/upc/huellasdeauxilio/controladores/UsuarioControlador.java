package com.upc.huellasdeauxilio.controladores;

import com.upc.huellasdeauxilio.entidades.Usuario;
import com.upc.huellasdeauxilio.servicios.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UsuarioControlador {

    @Autowired
    private UsuarioServicio usuarioServicio;

    @PostMapping("/usuario")
    public Usuario insertar(@RequestBody Usuario usuario) {
        return usuarioServicio.insertar(usuario);
    }

    @GetMapping("/usuario/correo/{correo}")
    public Usuario buscarPorCorreo(@PathVariable String correo) {
        return usuarioServicio.buscarPorCorreo(correo);
    }
}