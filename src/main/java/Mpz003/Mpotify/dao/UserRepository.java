package Mpz003.Mpotify.dao;

import Mpz003.Mpotify.entity.Playlist;
import Mpz003.Mpotify.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

import static java.lang.foreign.MemorySegment.NULL;
import static org.hibernate.engine.jdbc.env.spi.IdentifierCaseStrategy.LOWER;
import org.antlr.v4.runtime.atn.SemanticContext;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User,Integer> {

    Optional<User> findByUserName(String userName);

    @Query("SELECT u FROM User u " +
            "WHERE (:userName IS NULL OR LOWER(u.userName) LIKE LOWER(CONCAT('%', :userName, '%'))) " +
            "AND (:email IS NULL OR LOWER(u.email) LIKE LOWER(CONCAT('%', :email, '%')))")
    List<User> searchUsers(@Param("userName") String username,
                           @Param("email") String email);
}

