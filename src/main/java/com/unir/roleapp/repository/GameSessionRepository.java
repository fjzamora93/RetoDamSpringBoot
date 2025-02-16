package com.unir.roleapp.repository;
import com.unir.roleapp.entity.CharacterEntity;
import com.unir.roleapp.entity.GameSession;
import com.unir.roleapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GameSessionRepository extends JpaRepository<GameSession, Long> {

    List<GameSession> findByUserId(Long userId);

}
