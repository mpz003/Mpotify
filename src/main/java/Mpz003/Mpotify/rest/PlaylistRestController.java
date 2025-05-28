package Mpz003.Mpotify.rest;

import Mpz003.Mpotify.dao.PlaylistRepository;
import Mpz003.Mpotify.dao.UserRepository;
import Mpz003.Mpotify.entity.Playlist;
import Mpz003.Mpotify.entity.PlaylistSong;
import Mpz003.Mpotify.entity.Song;
import Mpz003.Mpotify.entity.User;
import Mpz003.Mpotify.service.PlaylistService;
import Mpz003.Mpotify.service.PlaylistSongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/mpz")
public class PlaylistRestController {

    private PlaylistService playlistService;
    private PlaylistSongService playlistSongService;
    private PlaylistRepository playlistRepository;
    private UserRepository userRepository;

    @Autowired
    public PlaylistRestController(PlaylistService playlistService,UserRepository userRepository, PlaylistSongService playlistSongService, PlaylistRepository playlistRepository) {
        this.playlistService = playlistService;
        this.userRepository=userRepository;
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

/*
    @PostMapping("/playlists")
    public Playlist addPlaylist(@RequestBody Playlist playlist) {
        return playlistService.addPlaylist(playlist);
    }
*/

    @PostMapping("/playlists")
    public ResponseEntity<Playlist> createPlaylist(@RequestBody Playlist playlist,
                                                   @AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();

        User user = userRepository.findByUserName(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        playlist.setUser(user);
        Playlist saved = playlistRepository.save(playlist);

        return ResponseEntity.ok(saved);
    }

    @PutMapping("/playlists/{id}")
    public ResponseEntity<?> updatePlaylist(@PathVariable Integer id,
                                            @RequestBody Playlist updatedPlaylist,
                                            @AuthenticationPrincipal UserDetails userDetails) {
        Playlist existing = playlistRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Playlist not found"));

        String username = userDetails.getUsername();
        boolean isAdmin = userDetails.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        if (!isAdmin && !existing.getUser().getUserName().equals(username)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You cannot edit this playlist");
        }

        existing.setName(updatedPlaylist.getName());
        return ResponseEntity.ok(playlistRepository.save(existing));
    }


    @DeleteMapping("/playlists/{id}")
    public ResponseEntity<?> deletePlaylist(@PathVariable Integer id,
                                            @AuthenticationPrincipal UserDetails userDetails) {
        Playlist playlist = playlistRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Playlist not found"));

        String username = userDetails.getUsername();
        boolean isAdmin = userDetails.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        if (!isAdmin && !playlist.getUser().getUserName().equals(username)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You cannot delete this playlist");
        }

        playlistRepository.deleteById(id);
        return ResponseEntity.ok("Deleted the playlist with id: " + id);
    }


    @PostMapping("/playlists/{playlistId}/songs/{songId}")
    public ResponseEntity<?> addSongToPlaylist(@PathVariable Integer playlistId,
                                               @PathVariable Integer songId,
                                               @AuthenticationPrincipal UserDetails userDetails) {
        Playlist playlist = playlistRepository.findById(playlistId)
                .orElseThrow(() -> new NoSuchElementException("Playlist not found"));

        String username = userDetails.getUsername();
        boolean isAdmin = userDetails.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        if (!isAdmin && !playlist.getUser().getUserName().equals(username)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You cannot modify this playlist");
        }

        PlaylistSong ps = playlistSongService.addSongToPlaylist(songId, playlistId);
        return ResponseEntity.ok(ps);
    }


    @GetMapping("/playlists/{playlistId}/songs")
    public ResponseEntity<?> getSongsForPlaylist(@PathVariable Integer playlistId,
                                                 @AuthenticationPrincipal UserDetails userDetails) {
        Playlist playlist = playlistRepository.findById(playlistId)
                .orElseThrow(() -> new NoSuchElementException("Playlist not found"));

        String username = userDetails.getUsername();
        boolean isAdmin = userDetails.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        if (!isAdmin && !playlist.getUser().getUserName().equals(username)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You cannot view this playlist");
        }

        return ResponseEntity.ok(playlistSongService.getSongsOfPlaylist(playlistId));
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/playlists/users/{userId}/playlists")
    public ResponseEntity<Playlist> createPlaylist(@PathVariable Integer userId,
                                                   @RequestParam String name) {
        Playlist playlist = playlistService.createPlaylistForUser(userId, name);
        return ResponseEntity.ok(playlist);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/playlists/users/{userId}/playlists")
    public List<Playlist> getUserPlaylists(@PathVariable Integer userId) {
        return playlistService.getPlaylistsByUserId(userId);
    }

}
