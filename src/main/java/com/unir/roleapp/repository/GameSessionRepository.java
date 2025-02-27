package com.unir.roleapp.repository;
import com.unir.roleapp.model.GameSession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GameSessionRepository extends JpaRepository<GameSession, Long> {

    List<GameSession> findByUserId(Long userId);

}
