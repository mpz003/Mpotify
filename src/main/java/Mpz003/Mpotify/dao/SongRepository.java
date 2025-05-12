package Mpz003.Mpotify.dao;

import Mpz003.Mpotify.entity.Song;
import org.antlr.v4.runtime.atn.SemanticContext;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

import static java.lang.foreign.MemorySegment.NULL;

import static org.hibernate.engine.jdbc.env.spi.IdentifierCaseStrategy.LOWER;

public interface SongRepository extends JpaRepository<Song,Integer> {

    @Query("SELECT s FROM Song s " +
            "WHERE (:name IS NULL OR LOWER(s.name) LIKE LOWER(CONCAT('%', :name, '%'))) " +
            "AND (:artist IS NULL OR LOWER(s.artist) LIKE LOWER(CONCAT('%', :artist, '%'))) " +
            "AND (:album IS NULL OR LOWER(s.album) LIKE LOWER(CONCAT('%', :album, '%')))")
    List<Song> searchSongs(@Param("name") String name,
                           @Param("artist") String artist,
                           @Param("album") String album);


}
