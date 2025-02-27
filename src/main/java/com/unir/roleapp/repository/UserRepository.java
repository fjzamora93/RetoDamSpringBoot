package com.unir.roleapp.repository;
import com.unir.roleapp.entity.CharacterEntity;
import com.unir.roleapp.entity.Spell;
import com.unir.roleapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>{
    Optional<User> findById(Long id);
    Optional<User> findByEmail(String email);
}
