package com.unir.auth.security;

import com.unir.auth.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;


/**
 *Esta clase es un filtro que se ejecuta en cada petición HTTP para verificar si el usuario está autenticado mediante un token JWT.
 *
 * 📌 Flujo de trabajo:
 *
 *     - Extrae el token JWT desde la cabecera Authorization.
 *     - Valida el token usando JwtTokenProvider.
 *     - Obtiene el email del usuario desde el token.
 *     - Busca al usuario en la base de datos usando UserService.
 *     - Crea una autenticación en el contexto de seguridad para que el usuario esté autenticado durante la petición.
 *
 * 🔹 Dependencias importantes:
 *
 *     - JwtTokenProvider → Maneja la validación y extracción de información del token JWT.
 *     - UserService → Se usa para obtener los datos del usuario con el email extraído del token.
 *
 * */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getServletPath();
        return path.startsWith("/api/auth/") || path.startsWith("/error") || path.startsWith("/home");
    }



    @Autowired
    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider, UserService userService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String token = extractToken(request);
        logger.info("🔍 [Filtro JWT] Token recibido: " + token);

        if (token != null && jwtTokenProvider.validateToken(token)) {
            String email = jwtTokenProvider.getEmailFromToken(token);
            UserDetails user = userService.getUserByEmail(email);
            logger.info("👤 [Filtro JWT] Usuario cargado: " + email);

            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authentication);
            logger.info("🔐 [Filtro JWT] Autenticación completada.");

            filterChain.doFilter(request, response);
        } else {
            logger.warn("❌ [Filtro JWT] Token inválido o no presente.");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Token inválido o expirado");
            return; // 🔁 CRUCIAL: corta aquí la ejecución
        }
    }



    private String extractToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        return null;
    }
}
