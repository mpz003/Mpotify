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
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/mpz")
public class PlaylistRestController {

    private PlaylistService playlistService;
    private PlaylistSongService playlistSongService;

    @Autowired
    public PlaylistRestController(PlaylistService playlistService, PlaylistSongService playlistSongService) {
        this.playlistService = playlistService;
        this.playlistSongService = playlistSongService;
    }




    @GetMapping("/playlists")
    public List<Playlist> getAllPlaylists() {
        return playlistService.getAllPlaylists();
    }

    @GetMapping("playlists/{id}")
    public Playlist getPlaylistById(@PathVariable int id){
        Playlist thePlaylist = playlistService.getPlaylistById(id);
        if (thePlaylist == null) {
            throw new RuntimeException("Employee id not found - " + id);
        }
        return thePlaylist;
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
