package Mpz003.Mpotify.rest;

import Mpz003.Mpotify.dao.PlaylistRepository;
import Mpz003.Mpotify.entity.Playlist;
import Mpz003.Mpotify.service.PlaylistService;
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

    @Autowired
    public PlaylistRestController(PlaylistService playlistService) {
        this.playlistService = playlistService;
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
}
