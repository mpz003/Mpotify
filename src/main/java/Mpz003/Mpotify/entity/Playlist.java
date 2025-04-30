package Mpz003.Mpotify.entity;

import jakarta.persistence.*;

@Entity
@Table
public class Playlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;

    @Column
    private String songName;

    @Column
    private String songArtist;

    public Playlist(){

    }

    public Playlist(String songName, String songArtist) {
        this.songName = songName;
        this.songArtist = songArtist;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getSongArtist() {
        return songArtist;
    }

    public void setSongArtist(String songArtist) {
        this.songArtist = songArtist;
    }

    @Override
    public String toString() {
        return "PlayList{" +
                "id=" + id +
                ", songName='" + songName + '\'' +
                ", songArtist='" + songArtist + '\'' +
                '}';
    }
}
