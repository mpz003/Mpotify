package Mpz003.Mpotify.dao;

import Mpz003.Mpotify.entity.Song;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SongRepository extends JpaRepository<Song,Integer> {
}
