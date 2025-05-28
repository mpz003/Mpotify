package Mpz003.Mpotify.service;

import Mpz003.Mpotify.dao.PlaylistRepository;
import Mpz003.Mpotify.dao.UserRepository;
import Mpz003.Mpotify.entity.Playlist;
import Mpz003.Mpotify.entity.Song;
import Mpz003.Mpotify.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlaylistService {
    private PlaylistRepository playlistRepository;
    private UserRepository userRepository;

    @Autowired
    public PlaylistService(PlaylistRepository playlistRepository, UserRepository userRepository) {
        this.playlistRepository = playlistRepository;
        this.userRepository = userRepository;
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
        } else {
            // we didn't find the playlist
            throw new RuntimeException("Did not find employee id - " + id);
        }

        return playlist;
    }

    public Playlist updatePlaylist(Integer id, Playlist updatedPlaylist) {
        return playlistRepository.findById(id)
                .map(existingPlaylist -> {
                    existingPlaylist.setName(updatedPlaylist.getName());
                    return playlistRepository.save(existingPlaylist);
                })
                .orElseThrow(() -> new IllegalArgumentException("Playlist with ID " + id + " not found"));
    }

    public List<Playlist> searchPlaylistByName(String name) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        boolean isAdmin = auth.getAuthorities().stream()
                .anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"));

        if (isAdmin) {
            return playlistRepository.findByNameContainingIgnoreCase(name);
        }

        User user = userRepository.findByUserName(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return playlistRepository.findByUserAndNameContainingIgnoreCase(user, name);
    }


    public Playlist createPlaylistForUser(Integer userId, String name, Playlist.PlaylistType type) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        Playlist playlist = new Playlist(name, user, type != null ? type : Playlist.PlaylistType.PRIVATE);
        return playlistRepository.save(playlist);
    }


    public List<Playlist> getPlaylistsByUserId(Integer userId) {
        return playlistRepository.findByUserId(userId);
    }

    public List<Playlist> getPlaylistsForCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        boolean isAdmin = auth.getAuthorities().stream()
                .anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"));

        if (isAdmin) {
            return playlistRepository.findAll();
        }

        User user = userRepository.findByUserName(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return playlistRepository.findByUser(user);
    }
}

