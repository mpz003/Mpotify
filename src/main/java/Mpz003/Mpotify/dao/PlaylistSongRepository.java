package Mpz003.Mpotify.dao;

import Mpz003.Mpotify.entity.PlaylistSong;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaylistSongRepository extends JpaRepository<PlaylistSong,Integer> {
}
