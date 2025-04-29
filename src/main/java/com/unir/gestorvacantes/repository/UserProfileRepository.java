package com.unir.gestorvacantes.repository;
import com.unir.gestorvacantes.model.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {

    List<UserProfile> findByUserId(Long userId);

}
