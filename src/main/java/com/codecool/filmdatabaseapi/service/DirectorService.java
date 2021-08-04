package com.codecool.filmdatabaseapi.service;

import com.codecool.filmdatabaseapi.model.Director;
import com.codecool.filmdatabaseapi.repository.DirectorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DirectorService {

    private final DirectorRepository directorRepository;

    public DirectorService(DirectorRepository directorRepository) {
        this.directorRepository = directorRepository;
    }

    public List<Director> listAllDirectors() {
        return directorRepository.findAll();
    }

    public void addNewDirector(Director director) {
        directorRepository.save(director);
    }

    public void editDirector(long id, Director director) {
        director.setId(id);
        directorRepository.save(director);
    }

    public void deleteDirector(long id) {
        directorRepository.deleteById(id);
    }
}
