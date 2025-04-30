package Mpz003.Mpotify.dao;

import Mpz003.Mpotify.entity.Playlist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaylistRepository extends JpaRepository<Playlist,Integer> {
}
