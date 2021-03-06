package com.codecool.filmdatabaseapi.controller;

import com.codecool.filmdatabaseapi.model.Film;
import com.codecool.filmdatabaseapi.service.FilmService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/film")
public class FilmController {

    private final FilmService filmService;

    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping
    public List<Film> listAllFilms() {
        return filmService.listAllFilms();
    }

    @PostMapping
    public Film addNewFilm(@RequestBody Film film) {
        return filmService.addNewFilm(film);
    }

    @GetMapping("/year/{year}")
    public List<Film> getFilmsByYear(@PathVariable int year) {
        return filmService.getFilmsByYear(year);
    }

    @GetMapping("/{id}")
    public Film getFilmById(@PathVariable Long id) {
        return filmService.getFilmById(id);
    }

    @PutMapping("/{id}")
    public Film editFilm(@PathVariable Long id, @RequestBody Film film){
        return filmService.editFilm(id, film);
    }

    @DeleteMapping("/{id}")
    public void deleteFilmById(@PathVariable Long id) {
        filmService.deleteFilmById(id);
    }

    @GetMapping("/director/{directorId}")
    public List<Film> getFilmsFromDirector(@PathVariable Long directorId) {
        return filmService.getFilmsFromDirector(directorId);
    }

    @DeleteMapping("/director/{directorId}")
    public void deleteFilmsFromDirector(@PathVariable Long directorId) {
        filmService.deleteFilmsFromDirector(directorId);
    }

    @GetMapping("/tag")
    public List<Film> findFilmsByTagIds(@RequestParam(name = "tagId") List<Long> tagIds) {
       return filmService.findFilmsByTagIds(tagIds);
    }


}
