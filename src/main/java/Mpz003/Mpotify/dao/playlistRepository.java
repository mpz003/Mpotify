package Mpz003.Mpotify.dao;

import Mpz003.Mpotify.entity.PlayList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface playlistRepository extends JpaRepository<PlayList,Integer> {
}
