package Mpz003.Mpotify.rest;

import Mpz003.Mpotify.entity.Song;
import Mpz003.Mpotify.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/mpz")
public class SongRestController {

    private SongService songService;

    @Autowired
    public SongRestController(SongService songService) {
        this.songService = songService;
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/songs")
    public List<Song> getAllSongs() {
        return songService.getAllSongs();
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/songs/{id}")
    public Song getSongById(@PathVariable int id) {
        Song theSong = songService.getSongById(id);

        if (theSong == null) {
            throw new RuntimeException("Song id not found - " + id);
        }

        return theSong;

    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/songs")
    public Song addSong(@RequestBody Song song) {
        song.setId(0);
        Song theSong = songService.addSong(song);
        return theSong;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/songs/{id}")
    public Song updateSong(@PathVariable Integer id, @RequestBody Song updatedSong) {
        return songService.updateSong(id, updatedSong);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/songs/{id}")
    public ResponseEntity<String> deleteSong(@PathVariable Integer id) {
        try {
            songService.deleteSong(id);
            return ResponseEntity.ok("Deleted the song with id: " + id);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Song not found with id: " + id);
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/songs/search")
    public List<Song> searchSongs(@RequestParam(required = false) String name,
                                  @RequestParam(required = false) String artist,
                                  @RequestParam(required = false) String album) {
        return songService.searchSongs(name, artist, album);
    }

}


