package com.codecool.filmdatabaseapi.service;

import com.codecool.filmdatabaseapi.model.Film;
import com.codecool.filmdatabaseapi.repository.FilmRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class FilmService {

    private final FilmRepository filmRepository;

    public FilmService(FilmRepository filmRepository) {
        this.filmRepository = filmRepository;
    }

    public List<Film> listAllFilms() {
        return filmRepository.findAll();
    }

    public Film addNewFilm(Film film) {
        return filmRepository.save(film);
    }

    public List<Film> getFilmsByYear(int year) {
        return filmRepository.findFilmsByReleaseYear(year);
    }

    public Film getFilmById(long id) {
        Optional<Film> film = filmRepository.findById(id);
        if (film.isPresent()) {
            return film.get();
        }
        else{
            throw new RuntimeException("Film doesn't exist!");
        }
    }

    public Film editFilm(Long id, Film film) {
        film.setId(id);
        return filmRepository.save(film);
    }

    public void deleteFilmById(Long id) {
        filmRepository.deleteById(id);
    }

    public List<Film> getFilmsFromDirector(Long directorId) {
        return filmRepository.findFilmsByDirector_Id(directorId);
    }

    public void deleteFilmsFromDirector(Long directorId) {
        List<Film> filmsFromDirector = filmRepository.findAll().stream()
                .filter(film -> film.getDirector().getId().equals(directorId))
                .collect(Collectors.toList());
        filmRepository.deleteAll(filmsFromDirector);
    }

    public List<Film> findFilmsByTagId(Long tagId) {
        return filmRepository.findAll().stream()
                .filter(film -> film.getTags().stream().anyMatch(tag -> tag.getId().equals(tagId)))
                .collect(Collectors.toList());
    }
}
