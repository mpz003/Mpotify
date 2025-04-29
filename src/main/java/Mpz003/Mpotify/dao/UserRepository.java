package Mpz003.Mpotify.dao;

import Mpz003.Mpotify.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {
}
