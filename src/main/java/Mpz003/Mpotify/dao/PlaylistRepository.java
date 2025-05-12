package Mpz003.Mpotify.dao;

import Mpz003.Mpotify.entity.Playlist;
import Mpz003.Mpotify.entity.Song;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlaylistRepository extends JpaRepository<Playlist,Integer> {
    List<Playlist> findByNameContainingIgnoreCase(String name);
}
