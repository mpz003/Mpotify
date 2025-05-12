package Mpz003.Mpotify.service;

import Mpz003.Mpotify.dao.SongRepository;
import Mpz003.Mpotify.entity.Song;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SongService {
    private SongRepository songRepository;

    @Autowired
    public SongService(SongRepository songRepository) {
        this.songRepository = songRepository;
    }

    public List<Song> getAllSongs() {
        return songRepository.findAll();
    }

    public Song getSongById(int id) {
        Optional<Song> result = songRepository.findById(id);

        Song TheSong = null;

        if (result.isPresent()) {
            TheSong = result.get();
        }
        else {
            // we didn't find the employee
            throw new RuntimeException("Did not find employee id - " + id);
        }

        return TheSong;
    }

    public Song addSong(Song song) {
        return songRepository.save(song);
    }

    public void deleteSong(Integer id) {
        songRepository.deleteById(id);
    }

    public Song updateSong(Integer id, Song updatedSong) {
        return songRepository.findById(id)
                .map(existingSong -> {
                    existingSong.setName(updatedSong.getName());
                    existingSong.setArtist(updatedSong.getArtist());
                    existingSong.setAlbum(updatedSong.getAlbum());
                    existingSong.setLength(updatedSong.getLength());
                    return songRepository.save(existingSong);
                })
                .orElseThrow(() -> new IllegalArgumentException("Song with ID " + id + " not found"));
    }
    public List<Song> searchSongs(String name, String artist, String album) {
        return songRepository.searchSongs(name, artist, album);
    }
}
