package Mpz003.Mpotify.rest;

import Mpz003.Mpotify.dao.PlaylistSongRepository;
import Mpz003.Mpotify.entity.PlaylistSong;
import Mpz003.Mpotify.service.PlaylistSongService;
import org.springframework.beans.factory.annotation.Autowired;
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





    @PostMapping("/playlistsongs")
    public PlaylistSong addSongToPlaylist(@RequestBody PlaylistSong ps) {
        return playlistSongService .addSongToPlaylist(ps);
    }

    @DeleteMapping("/playlistsongs/{id}")
    public void removeSongFromPlaylist(@PathVariable Integer id) {
        playlistSongService .removeSongFromPlaylist(id);
    }

    @GetMapping("/playlistsongs")
    public List<PlaylistSong> getAllPlaylistSongs() {
        return playlistSongService .getAllPlaylistSongs();
    }
}
