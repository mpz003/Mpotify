package Mpz003.Mpotify.dao;

import Mpz003.Mpotify.entity.PlaylistSong;
import Mpz003.Mpotify.entity.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PlaylistSongRepository extends JpaRepository<PlaylistSong,Integer> {
    @Query("SELECT ps.song FROM PlaylistSong ps WHERE ps.playlist.id = :playlistId")
    List<Song> findSongsByPlaylistId(@Param("playlistId") Integer playlistId);

}
