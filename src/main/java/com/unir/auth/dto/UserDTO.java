package com.unir.auth.dto;
import com.unir.roleapp.dto.CharacterResponseDTO;
import com.unir.session.dto.GameSessionDTO;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;



@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO implements UserDetails {

    private Long id;
    private String name;
    private String email;
    private String password;

    // Relación con CharacterEntity (se pasa la lista de characterEntities)
    private List<CharacterResponseDTO> characterEntities;
    private List<GameSessionDTO> gameSessions;

    // Constructor sin ID
    public UserDTO(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }
    public UserDTO( Long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new ArrayList<>();//implementa la logica de tus roles
    }
    @Override
    public String getPassword() {
        return null;//no se usa en JWT
    }
    @Override
    public String getUsername() {
        return this.getEmail();
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @Override
    public boolean isEnabled() {
        return true;
    }
}