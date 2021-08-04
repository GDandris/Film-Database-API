package com.codecool.filmdatabaseapi.repository;

import com.codecool.filmdatabaseapi.model.Film;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FilmRepository extends JpaRepository<Film, Long> {
    List<Film> findFilmsByReleaseYear (int releaseYear);
}
