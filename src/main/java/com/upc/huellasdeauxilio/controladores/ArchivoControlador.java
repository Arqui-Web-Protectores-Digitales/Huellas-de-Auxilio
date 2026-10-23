package com.upc.huellasdeauxilio.controladores;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class ArchivoControlador {

    // El nombre de la carpeta en la raíz del proyecto, osea al lado de pom.xml
    private final String CARPETA_IMAGENES = "imagenes";

    @PostMapping(value = "/archivos/subir", consumes = {"multipart/form-data"})
    @PreAuthorize("hasAnyRole('CIUDADANO', 'ENTIDAD')")
    public ResponseEntity<Map<String, String>> subirImagen(@RequestParam("archivo") MultipartFile archivo) {
        Map<String, String> respuesta = new HashMap<>();

        try {
            // 1 crear la carpeta "imagenes" si no existe en el disco duro, solo si no existe
            File directorio = new File(CARPETA_IMAGENES);
            if (!directorio.exists()) {
                directorio.mkdirs();
            }

            // 2 generar un nombre único para que no se chanquen las fotos
            // ejemplo: foto.jpg -> 1234abcd-foto.jpg
            String nombreUnico = UUID.randomUUID().toString() + "-" + archivo.getOriginalFilename();

            // 3 ruta completa donde se guardará
            Path rutaFisica = Paths.get(CARPETA_IMAGENES).resolve(nombreUnico).toAbsolutePath();

            // 4 copiar el archivo que mandó el usuario a nuestra carpeta
            Files.copy(archivo.getInputStream(), rutaFisica);

            // 5 generar la URL con la que Angular podrá ver la imagen
            String urlImagen = "http://localhost:8088/imagenes/" + nombreUnico;

            // 6 devolver la URL
            respuesta.put("url", urlImagen);
            return new ResponseEntity<>(respuesta, HttpStatus.OK);

        } catch (IOException e) {
            respuesta.put("error", "Ocurrió un error al subir la imagen");
            return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
