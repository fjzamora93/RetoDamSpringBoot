package com.unir.roleapp.repository;
import com.unir.roleapp.model.GameSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface GameSessionRepository extends JpaRepository<GameSession, Long> {

    List<GameSession> findByUserId(Long userId);

    @Query("SELECT gs FROM GameSession gs JOIN gs.characters c WHERE c.id = :characterId")
    Optional<GameSession> findByCharacterId(@Param("characterId") Long characterId);

}
