package com.unir.gestorvacantes.dto;

import com.unir.gestorvacantes.model.UserProfile;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileDTO {
    private Long id;
    private Long userId;
    private String name;

    public UserProfileDTO(Long userId, String name) {
        this.userId = userId;
        this.name = name;
    }

    @Override
    public String toString() {
        return "UserProfileDTO{" +
                "id=" + id +
                ", userId=" + userId +
                ", name='" + name + '\'' +
                '}';
    }
}
