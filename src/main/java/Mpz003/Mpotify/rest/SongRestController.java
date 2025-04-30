package Mpz003.Mpotify.rest;

import Mpz003.Mpotify.entity.Song;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/mpz")
public class SongRestController {

    private

    @GetMapping("/songs")
    public List<Song> findAll(){
        return
    }

}
