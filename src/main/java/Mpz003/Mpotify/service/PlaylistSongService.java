package Mpz003.Mpotify.service;

import Mpz003.Mpotify.dao.PlaylistRepository;
import Mpz003.Mpotify.dao.PlaylistSongRepository;
import Mpz003.Mpotify.dao.SongRepository;
import Mpz003.Mpotify.entity.Playlist;
import Mpz003.Mpotify.entity.PlaylistSong;
import Mpz003.Mpotify.entity.Song;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlaylistSongService {
    private final PlaylistRepository playlistRepository;
    private final SongRepository songRepository;
    private final PlaylistSongRepository playlistSongRepository;

    @Autowired
    public PlaylistSongService(PlaylistRepository playlistRepository,
                               SongRepository songRepository,
                               PlaylistSongRepository playlistSongRepository) {
        this.playlistRepository = playlistRepository;
        this.songRepository = songRepository;
        this.playlistSongRepository = playlistSongRepository;
    }
    public List<PlaylistSong> findAll() {
        return playlistSongRepository.findAll();
    }

    public PlaylistSong findById(int id) {
        return playlistSongRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("PlaylistSong not found with id: " + id));
    }

    public PlaylistSong addSongToPlaylist(Integer songId, Integer playlistId) {
        Playlist playlist = playlistRepository.findById(playlistId)
                .orElseThrow(() -> new RuntimeException("Playlist not found with id: " + playlistId));

        Song song = songRepository.findById(songId)
                .orElseThrow(() -> new RuntimeException("Song not found with id: " + songId));

        PlaylistSong ps = new PlaylistSong();
        ps.setPlaylist(playlist);
        ps.setSong(song);

        return playlistSongRepository.save(ps);
    }

    public List<Song> getSongsOfPlaylist(Integer playlistId) {
        return playlistSongRepository.findSongsByPlaylistId(playlistId);
    }


}
