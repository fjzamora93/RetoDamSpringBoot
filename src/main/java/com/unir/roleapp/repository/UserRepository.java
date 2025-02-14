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



    //! NO USAR. LA CONTRASEÑA SE RECUPERA DESDE EL EMAIL Y SE VERIFICA EN LA API, NO EN BASE DE DATOS
    @Query(
            "SELECT u FROM User u " +
            "WHERE LOWER(u.email) = LOWER(:email) " +
            "AND u.password = :password"
    )
    Optional<User> findByEmailAndPassword(
            @Param("email") String email,
            @Param("password") String password
    );


}
