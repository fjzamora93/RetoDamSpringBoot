package com.unir.auth.controller;

import com.unir.auth.dto.RefreshTokenRequest;
import com.unir.auth.security.JwtTokenProvider;
import com.unir.auth.dto.LoginRequest;
import com.unir.auth.dto.LoginResponse;
import com.unir.auth.dto.UserDTO;
import com.unir.auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;


    @Autowired
    public AuthController(UserService userService, JwtTokenProvider jwtTokenProvider, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
    }


    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        try {
            System.out.println("🔐 Intentando login para: " + loginRequest.getEmail());

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
            );

            String accessToken = jwtTokenProvider.createAccessToken(authentication.getName());
            String refreshToken = jwtTokenProvider.createRefreshToken(authentication.getName());
            Date expiration = jwtTokenProvider.getExpirationDateFromToken(accessToken);

            UserDetails user = userService.getUserByEmail(loginRequest.getEmail());

            return ResponseEntity.ok(new LoginResponse(accessToken, refreshToken, user, expiration));

        } catch (BadCredentialsException e) {
            System.out.println("❌ Credenciales inválidas: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new LoginResponse("Credenciales inválidas"));
        } catch (Exception e) {
            System.out.println("💥 Error en login: " + e.getMessage());
            e.printStackTrace(); // esto imprime el stacktrace completo
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new LoginResponse("Error interno al autenticar"));
        }
    }


    // TODO: IMplementar el Doble Factor  al registrase (o mandar un email o algo)
    // TODO: Mejorar el Sign UP para que haga un autologin (coordinarlo con el Front)
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody UserDTO newUser) {
        try {
            userService.save(newUser);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Usuario registrado exitosamente");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al registrar el usuario: " + e.getMessage());
        }
    }


    @PostMapping("/refresh-token")
    public ResponseEntity<LoginResponse> refreshToken(
            @RequestBody RefreshTokenRequest refreshTokenRequest
    ) {
        try {
            String refreshToken = refreshTokenRequest.getRefreshToken();

            // Validar el refresh token
            if (jwtTokenProvider.validateToken(refreshToken)) {
                String email = jwtTokenProvider.getEmailFromToken(refreshToken);
                UserDetails user = userService.getUserByEmail(email);

                // Generar un nuevo access token
                String newAccessToken = jwtTokenProvider.createAccessToken(email);
                Date expiration = jwtTokenProvider.getExpirationDateFromToken(newAccessToken);

                // Crear respuesta
                LoginResponse loginResponse = new LoginResponse(newAccessToken, refreshToken, user, expiration);

                return ResponseEntity.ok(loginResponse);
            } else {
                throw new RuntimeException("Refresh token inválido o expirado");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new LoginResponse("Error al refrescar el token: " + e.getMessage()));
        }
    }


    /** TODO: Aunque el AccessToken caduque por sí mismo al cabo de unos minutos, el Refresh Token hay que "invalidarlo" en el backend.
     *
     * Si un atacante consigue el RefreshToken, podría usarlo para obtener infinitos AccessToken, así que hay que reforzar la seguridad.
     *
     * Para "invalidar" el Refresh Token hay varias propuestas. Ya que no se puede revocar, las opciones serían estas:
     * - LIsta NEgra de REfresh Token (SIN IMPLEMENTAR)
     * - DUración corta de Access TOken (hecho, pero se puede revisar los tiempos).
     * - Cambiar el Refresh TOken automáticamente cada vez que cambie el Access TOken (esto ya se está haciendo).
     *
     * */
    @PostMapping("/logout")
    public ResponseEntity<Void> logoutUser() {
        System.out.println("EL usuario se fue");
        return ResponseEntity.ok().build();
    }

    @GetMapping("/ping")
    public String ping() {
        return "pong";
    }


}
