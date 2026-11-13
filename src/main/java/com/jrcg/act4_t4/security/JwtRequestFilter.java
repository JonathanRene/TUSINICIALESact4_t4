package com.jrcg.act4_t4.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        // 1. Extraer la cabecera "Authorization" de la petición
        final String authorizationHeader = request.getHeader("Authorization");

        String username = null;
        String jwt = null;

        // 2. Verificar si la cabecera existe y empieza con "Bearer " (formato estándar de JWT)
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            // Extraemos el token quitando los primeros 7 caracteres ("Bearer ")
            jwt = authorizationHeader.substring(7);
            try {
                // Sacamos el email (username) del token
                username = jwtUtil.extractUsername(jwt);
            } catch (Exception e) {
                System.out.println("Error leyendo el token: " + e.getMessage());
            }
        }

        // 3. Si hay un email y el usuario aún no ha sido autenticado en este ciclo
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // Buscamos los datos del usuario en la base de datos
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            // 4. Validamos que el token coincida y no esté vencido
            if (jwtUtil.validateToken(jwt, userDetails)) {

                // 5. ¡Aprobado! Le decimos a Spring Security que este usuario tiene acceso
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                
                // Guardamos la sesión en el contexto de seguridad
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        
        // 6. Finalmente, dejamos que la petición continúe su camino hacia el Controlador
        chain.doFilter(request, response);
    }
}