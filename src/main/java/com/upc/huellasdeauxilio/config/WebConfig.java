package com.upc.huellasdeauxilio.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // chicos con esto, todo lo que entre a http://localhost:8088/imagenes/...
        // se buscará en la carpeta que creé "imagenes" en la raíz del proyecto
        registry.addResourceHandler("/imagenes/**")
                .addResourceLocations("file:imagenes/");
    }
}
