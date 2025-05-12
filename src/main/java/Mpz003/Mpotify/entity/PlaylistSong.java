package Mpz003.Mpotify.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "playlist_song")
public class PlaylistSong {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "song_id", nullable = false)
    private Song song;

    @ManyToOne
    @JoinColumn(name = "playlist_id", nullable = false)
    private Playlist playlist;

    public PlaylistSong(){

    }

    public PlaylistSong(Song song, Playlist playlist) {
        this.song = song;
        this.playlist = playlist;
    }

    public int getId() {
        return id;
    }

    public Song getSong() {
        return song;
    }

    public void setSong(Song song) {
        this.song = song;
    }

    public Playlist getPlaylist() {
        return playlist;
    }

    public void setPlaylist(Playlist playlist) {
        this.playlist = playlist;
    }

    @Override
    public String toString() {
        return "PlaylistSong{" +
                "id=" + id +
                ", song=" + song +
                ", playlist=" + playlist +
                '}';
    }
}
