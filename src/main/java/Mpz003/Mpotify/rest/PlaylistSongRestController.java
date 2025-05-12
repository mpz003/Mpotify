package Mpz003.Mpotify.rest;

import Mpz003.Mpotify.dao.PlaylistSongRepository;
import Mpz003.Mpotify.entity.PlaylistSong;
import Mpz003.Mpotify.service.PlaylistSongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mpz")
public class PlaylistSongRestController {
    private PlaylistSongService playlistSongService;

    @Autowired
    public PlaylistSongRestController(PlaylistSongService playlistSongService) {
        this.playlistSongService = playlistSongService;
    }

    @GetMapping("/playlistsongs")
    public List<PlaylistSong> getAllPlaylistSongs() {
        return playlistSongService.findAll();
    }

    @GetMapping("/playlistsongs/{id}")
    public PlaylistSong getPlaylistSongById(@PathVariable int id) {
        return playlistSongService.findById(id);
    }

    @PostMapping("/playlistsongs/{playlistId}/songs/{songId}")
    public ResponseEntity<PlaylistSong> addSongToPlaylist(@PathVariable Integer playlistId,
                                                          @PathVariable Integer songId) {
        PlaylistSong ps = playlistSongService.addSongToPlaylist(songId, playlistId);
        return ResponseEntity.ok(ps);
    }


}
