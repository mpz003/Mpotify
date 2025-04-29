package Mpz003.Mpotify.entity;

import jakarta.persistence.*;

@Entity
@Table
public class PlaylistSong {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;

    @Column
    private int songId;

    @Column
    private int playlistId;

    public PlaylistSong(){

    }

    public PlaylistSong(int songId, int playlistId) {
        this.songId = songId;
        this.playlistId = playlistId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSongId() {
        return songId;
    }

    public void setSongId(int songId) {
        this.songId = songId;
    }

    public int getPlaylistId() {
        return playlistId;
    }

    public void setPlaylistId(int playlistId) {
        this.playlistId = playlistId;
    }

    @Override
    public String toString() {
        return "PlaylistSong{" +
                "id=" + id +
                ", songId=" + songId +
                ", playlistId=" + playlistId +
                '}';
    }
}
