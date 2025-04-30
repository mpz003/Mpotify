package Mpz003.Mpotify.service;

import Mpz003.Mpotify.dao.PlaylistRepository;
import Mpz003.Mpotify.entity.Playlist;
import Mpz003.Mpotify.entity.Song;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlaylistService {
    private PlaylistRepository playlistRepository;

    @Autowired
    public PlaylistService(PlaylistRepository playlistRepository) {
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

    public Playlist getPlaylistById(int id) {
        Optional<Playlist> result = playlistRepository.findById(id);

        Playlist playlist = null;

        if (result.isPresent()) {
            playlist = result.get();
        }
        else {
            // we didn't find the employee
            throw new RuntimeException("Did not find employee id - " + id);
        }

        return playlist;
    }
}
