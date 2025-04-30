package Mpz003.Mpotify.service;

import Mpz003.Mpotify.dao.PlaylistRepository;
import Mpz003.Mpotify.entity.Playlist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class playlistService {
    private PlaylistRepository playlistRepository;

    @Autowired
    public playlistService(PlaylistRepository playlistRepository) {
        this.playlistRepository = playlistRepository;
    }

    public List<Playlist> getAllPlaylists() {
        return playlistRepository.findAll();
    }

    public Playlist addPlaylist(Playlist playlist) {
        return playlistRepository.save(playlist);
    }

    public void deletePlaylist(Integer id) {
        playlistRepository.deleteById(id);
    }

    public Optional<Playlist> getPlaylistById(Integer id) {
        return playlistRepository.findById(id);
    }
}
