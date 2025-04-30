package Mpz003.Mpotify.rest;

import Mpz003.Mpotify.entity.Song;
import Mpz003.Mpotify.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mpz")
public class SongRestController {

    private SongService songService;

    @Autowired
    public SongRestController(SongService songService) {
        this.songService = songService;
    }

    @GetMapping("/songs")
    public List<Song> getAllSongs() {
        return songService.getAllSongs();
    }

    @GetMapping("/songs/{id}")
    public Song getSongById(@PathVariable int id) {
        Song theSong = songService.getSongById(id);

        if (theSong == null) {
            throw new RuntimeException("Employee id not found - " + id);
        }

        return theSong;

    }

    @PostMapping("/songs")
    public Song addSong(@RequestBody Song song) {
        song.setId(0);
        Song theSong = songService.addSong(song);
        return theSong;
    }

    @PutMapping("/songs/{id}")
    public Song updateSong(@PathVariable Integer id, @RequestBody Song updatedSong) {
        return songService.updateSong(id, updatedSong);
    }

    @DeleteMapping("/songs/{id}")
    public void deleteSong(@PathVariable Integer id) {
        songService.deleteSong(id);
    }

}


