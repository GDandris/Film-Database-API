package com.codecool.filmdatabaseapi.controller;

import com.codecool.filmdatabaseapi.model.Director;
import com.codecool.filmdatabaseapi.service.DirectorService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/director")
public class DirectorController {

    private final DirectorService directorService;

    public DirectorController(DirectorService directorService) {
        this.directorService = directorService;
    }

    @GetMapping
    public List<Director> listAllDirectors() {
        return directorService.listAllDirectors();
    }

    @PostMapping
    public void addNewDirector(@RequestBody Director director) {
        directorService.addNewDirector(director);
    }

    @PutMapping("/{id}")
    public void editDirector(@PathVariable long id, @RequestBody Director director) {
        directorService.editDirector(id, director);
    }

    @DeleteMapping("/{id}")
    public void deleteDirector(@PathVariable long id){
        directorService.deleteDirector(id);
    }
}
