package com.unir.roleapp.SecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


/**
 * SecurityConfig (Configuración de Seguridad)
 *
 * Configura la seguridad de la aplicación con Spring Security.
 *
 * 📌 Configuraciones clave:
 *
 *     - Deshabilita CSRF (porque se usa autenticación sin estado con JWT).
 *     - Permite acceso libre a /api/auth/** (por ejemplo, para login y registro).
 *     - Protege todas las demás rutas, obligando a que los usuarios estén autenticados.
 *     - Usa SessionCreationPolicy.STATELESS, lo que significa que no se guardará sesión del usuario en el servidor.
 *     - Registra el JwtAuthenticationFilter para validar tokens en cada petición.
 *
 * 🔹 Dependencias importantes:
 *
 *     - JwtAuthenticationFilter → Se encarga de validar cada petición entrante con un token JWT.
 *
 * */

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Deshabilitar CSRF para APIs REST
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll() // Permitir acceso público a /api/auth/**
                        .anyRequest().authenticated() // El resto de las rutas requieren autenticación
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Sin sesiones
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Usar BCrypt para encriptar contraseñas
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}