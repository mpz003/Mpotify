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

    public Optional<Song> getSongById(Integer id) {
        return songRepository.findById(id);
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
}
