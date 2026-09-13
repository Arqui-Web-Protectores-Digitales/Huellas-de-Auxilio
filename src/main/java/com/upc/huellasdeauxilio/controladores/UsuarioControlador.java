package com.upc.huellasdeauxilio.controladores;

import com.upc.huellasdeauxilio.entidades.Usuario;
import com.upc.huellasdeauxilio.servicios.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

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

    @GetMapping("/usuario/login/{correo}/{contraseña}")
    public Usuario iniciarSesion(@PathVariable String correo,
                                 @PathVariable String contraseña) {
        return usuarioServicio.iniciarSesion(correo, contraseña);
    }

    @PutMapping("/usuario/contrasena/{correo}")
    public Usuario cambiarContrasena(@PathVariable String correo,
                                     @RequestBody Usuario usuario) {
        return usuarioServicio.cambiarContrasena(
                correo,
                usuario.getContraseña()
        );
    }

    @PutMapping("/usuario/perfil/contrasena/{correo}")
    public String cambiarContrasenaDesdePerfil(
            @PathVariable("correo") String correo,
            @RequestBody Map<String, String> datos
    ) {
        if (datos == null) {
            return "Completa los datos para cambiar la contraseña.";
        }

        Usuario actualizado = usuarioServicio.cambiarContrasenaDesdePerfil(
                correo,
                datos.get("contrasenaActual"),
                datos.get("nuevaContrasena"),
                datos.get("confirmarContrasena")
        );

        if (actualizado == null) {
            return "No se pudo cambiar la contraseña. "
                    + "Verifica los datos ingresados.";
        }

        return "Contraseña actualizada correctamente";
    }
}