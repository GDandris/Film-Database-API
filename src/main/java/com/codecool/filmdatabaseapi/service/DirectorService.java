package com.codecool.filmdatabaseapi.service;

import com.codecool.filmdatabaseapi.model.Director;
import com.codecool.filmdatabaseapi.repository.DirectorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DirectorService {

    private final DirectorRepository directorRepository;
    private final FilmService filmService;

    public DirectorService(DirectorRepository directorRepository, FilmService filmService) {
        this.directorRepository = directorRepository;
        this.filmService = filmService;
    }

    public List<Director> listAllDirectors() {
        return directorRepository.findAll();
    }

    public Director addNewDirector(Director director) {
        return directorRepository.save(director);
    }

    public Director editDirector(long id, Director director) {
        director.setId(id);
        return directorRepository.save(director);
    }

    public void deleteDirector(long id) {
        filmService.deleteFilmsFromDirector(id);
        directorRepository.deleteById(id);
    }
}
