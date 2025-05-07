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
    public List<PlaylistSong> findAll() {
        return playlistSongRepository.findAll();
    }

    public PlaylistSong findById(int id) {
        return playlistSongRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("PlaylistSong not found with id: " + id));
    }
}
