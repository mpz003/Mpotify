package Mpz003.Mpotify.dao;

import Mpz003.Mpotify.entity.PlaylistSong;
import Mpz003.Mpotify.entity.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(exported = false)
public interface PlaylistSongRepository extends JpaRepository<PlaylistSong,Integer> {
    @Query("SELECT ps.song FROM PlaylistSong ps WHERE ps.playlist.id = :playlistId")
    List<Song> findSongsByPlaylistId(@Param("playlistId") Integer playlistId);

}
