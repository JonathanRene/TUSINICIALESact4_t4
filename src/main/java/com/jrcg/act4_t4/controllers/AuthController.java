package com.jrcg.act4_t4.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jrcg.act4_t4.dtos.AuthRequestDTO;
import com.jrcg.act4_t4.dtos.AuthResponseDTO;
import com.jrcg.act4_t4.models.Usuario;
import com.jrcg.act4_t4.repositories.UsuarioRepository;
import com.jrcg.act4_t4.security.JwtUtil;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // 1. ENDPOINT PARA REGISTRARSE
    // Usamos @Valid para que Spring revise el DTO antes de entrar al método
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody AuthRequestDTO authRequestDTO) {
        
        // Verificamos si el email ya existe en la base de datos
        if (usuarioRepository.findByEmail(authRequestDTO.getEmail()).isPresent()) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "El correo ya está registrado");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST); // 400 Bad Request
        }

        // Creamos el nuevo usuario
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setEmail(authRequestDTO.getEmail());
        // ENCRIPTAMOS la contraseña antes de guardarla. ¡Nunca guardar en texto plano!
        nuevoUsuario.setPassword(passwordEncoder.encode(authRequestDTO.getPassword()));

        usuarioRepository.save(nuevoUsuario);

        Map<String, String> exito = new HashMap<>();
        exito.put("mensaje", "Usuario registrado exitosamente");
        return new ResponseEntity<>(exito, HttpStatus.CREATED); // 201 Created
    }

    // 2. ENDPOINT PARA INICIAR SESIÓN Y OBTENER EL TOKEN
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@Valid @RequestBody AuthRequestDTO authRequestDTO) {
        try {
            // Spring Security intenta autenticar con el email y la contraseña sin encriptar (él se encarga de comparar hashes)
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequestDTO.getEmail(), authRequestDTO.getPassword())
            );

            // Si la autenticación es exitosa, buscamos los detalles del usuario
            UserDetails userDetails = userDetailsService.loadUserByUsername(authRequestDTO.getEmail());
            
            // Generamos el token JWT mágico
            String jwt = jwtUtil.generateToken(userDetails);

            // Devolvemos el DTO con el token y un código 200 OK
            return ResponseEntity.ok(new AuthResponseDTO(jwt, "Autenticación exitosa"));

        } catch (Exception e) {
            // Si la contraseña es incorrecta o el usuario no existe, devolvemos un 401 Unauthorized
            Map<String, String> error = new HashMap<>();
            error.put("error", "Credenciales inválidas");
            return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
        }
    }
}