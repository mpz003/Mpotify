package Mpz003.Mpotify.rest;

import Mpz003.Mpotify.dao.PlaylistRepository;
import Mpz003.Mpotify.entity.Playlist;
import Mpz003.Mpotify.entity.PlaylistSong;
import Mpz003.Mpotify.entity.Song;
import Mpz003.Mpotify.service.PlaylistService;
import Mpz003.Mpotify.service.PlaylistSongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/mpz")
public class PlaylistRestController {

    private PlaylistService playlistService;
    private PlaylistSongService playlistSongService;
    private PlaylistRepository playlistRepository;

    @Autowired
    public PlaylistRestController(PlaylistService playlistService, PlaylistSongService playlistSongService, PlaylistRepository playlistRepository) {
        this.playlistService = playlistService;
        this.playlistRepository=playlistRepository;
        this.playlistSongService = playlistSongService;
    }




    @GetMapping("/playlists")
    public ResponseEntity<List<Playlist>> getOwnPlaylists() {
        return ResponseEntity.ok(playlistService.getPlaylistsForCurrentUser());
    }


    @GetMapping("/playlists/{id}")
    public ResponseEntity<Playlist> getPlaylistById(@PathVariable Integer id) {
        Playlist playlist = playlistRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Playlist not found with id: " + id));

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean isAdmin = SecurityContextHolder.getContext().getAuthentication()
                .getAuthorities().stream()
                .anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"));

        if (!isAdmin && !playlist.getUser().getUserName().equals(username)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build(); // 🔐 block access
        }

        return ResponseEntity.ok(playlist);
    }


    @PostMapping("/playlists")
    public Playlist addPlaylist(@RequestBody Playlist playlist) {
        return playlistService.addPlaylist(playlist);
    }

    @PutMapping("/playlists/{id}")
    public Playlist updatePlaylist(@PathVariable Integer id, @RequestBody Playlist updatedPlaylist){
        return playlistService.updatePlaylist(id,updatedPlaylist);
    }

    @DeleteMapping("/playlists/{id}")
    public ResponseEntity<String> deletePlaylist(@PathVariable Integer id) {
        try {
            playlistService.deletePlaylist(id);
            return ResponseEntity.ok("Deleted the playlist with id: " + id);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Playlist not found with id: " + id);
        }
    }

    @PostMapping("/playlists/{playlistId}/songs/{songId}")
    public ResponseEntity<PlaylistSong> addSongToPlaylist(@PathVariable Integer playlistId,
                                                          @PathVariable Integer songId) {
        PlaylistSong ps = playlistSongService.addSongToPlaylist(songId, playlistId);
        return ResponseEntity.ok(ps);
    }

    @GetMapping("/playlists/{playlistId}/songs")
    public List<Song> getSongsForPlaylist(@PathVariable Integer playlistId) {
        return playlistSongService.getSongsOfPlaylist(playlistId);
    }

    @PostMapping("/playlists/users/{userId}/playlists")
    public ResponseEntity<Playlist> createPlaylist(@PathVariable Integer userId,
                                                   @RequestParam String name) {
        Playlist playlist = playlistService.createPlaylistForUser(userId, name);
        return ResponseEntity.ok(playlist);
    }

    @GetMapping("/playlists/users/{userId}/playlists")
    public List<Playlist> getUserPlaylists(@PathVariable Integer userId) {
        return playlistService.getPlaylistsByUserId(userId);
    }

}
