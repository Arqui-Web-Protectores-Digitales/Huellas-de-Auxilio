package com.upc.huellasdeauxilio.security.controllers;

import com.upc.huellasdeauxilio.security.dtos.AuthRequestDTO;
import com.upc.huellasdeauxilio.security.dtos.AuthResponseDTO;
import com.upc.huellasdeauxilio.security.services.CustomUserDetailsService;
import com.upc.huellasdeauxilio.security.util.JwtUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "${ip.frontend}", allowCredentials = "true", exposedHeaders = "Authorization")
@RestController
@RequestMapping("/api")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService userDetailsService;

    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, CustomUserDetailsService userDetailsService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthResponseDTO> createAuthenticationToken(@RequestBody AuthRequestDTO authRequest) throws Exception {

        // Autentica usando el correo y contraseña
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getCorreo(), authRequest.getContraseña())
        );

        // Si es correcto, carga los detalles y genera el Token
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getCorreo());
        final String token = jwtUtil.generateToken(userDetails);

        // Extrae el rol del usuario, por ejemplo ROLE_CIUDADANO
        Set<String> roles = userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());

        // Prepara la respuesta para el Frontend / Swagger
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Authorization", token);

        AuthResponseDTO authResponseDTO = new AuthResponseDTO();
        authResponseDTO.setRoles(roles);
        authResponseDTO.setJwt(token);

        return ResponseEntity.ok().headers(responseHeaders).body(authResponseDTO);
    }
}
