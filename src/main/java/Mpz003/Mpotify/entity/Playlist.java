package Mpz003.Mpotify.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "playlist")
public class Playlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "song_name")
    private String songName;

    @Column(name = "song_artist")
    private String songArtist;

    public Playlist(){

    }

    public Playlist(String name, String songName, String songArtist) {
        this.name=name;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Playlist{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", songName='" + songName + '\'' +
                ", songArtist='" + songArtist + '\'' +
                '}';
    }
}
