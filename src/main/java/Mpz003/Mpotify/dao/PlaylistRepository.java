package Mpz003.Mpotify.dao;

import Mpz003.Mpotify.entity.Playlist;
import Mpz003.Mpotify.entity.Song;
import Mpz003.Mpotify.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(exported = false)
public interface PlaylistRepository extends JpaRepository<Playlist,Integer> {
    List<Playlist> findByNameContainingIgnoreCase(String name);

    List<Playlist> findByUserId(Integer userId);

    List<Playlist> findByUser(User user);

    List<Playlist> findByType(Playlist.PlaylistType type);

    List<Playlist> findByUserAndNameContainingIgnoreCase(User user, String name);

}
