package Mpz003.Mpotify.service;

import Mpz003.Mpotify.dao.PlaylistSongRepository;
import Mpz003.Mpotify.entity.PlaylistSong;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlaylistSongService {
    private PlaylistSongRepository playlistSongRepository;

    @Autowired
    public PlaylistSongService(PlaylistSongRepository playlistSongRepository) {
        this.playlistSongRepository = playlistSongRepository;
    }

    public PlaylistSong addSongToPlaylist(PlaylistSong playlistSong) {
        return playlistSongRepository.save(playlistSong);
    }

    public void removeSongFromPlaylist(Integer id) {
        playlistSongRepository.deleteById(id);
    }

    public List<PlaylistSong> getAllPlaylistSongs() {
        return playlistSongRepository.findAll();
    }
}
