package com.codecool.filmdatabaseapi.controller;

import com.codecool.filmdatabaseapi.model.Tag;
import com.codecool.filmdatabaseapi.service.TagService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tag")
public class TagController {

    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping
    private List<Tag> listAllTags() {
        return tagService.listAllTags();
    }

    @PostMapping
    private Tag addNewTag(@RequestBody Tag tag) {
        return tagService.addNewTag(tag);
    }

    @PutMapping("/{id}")
    private Tag editTag(@PathVariable Long id, @RequestBody Tag tag) {
        return tagService.editTag(id, tag);
    }

    @DeleteMapping("/{id}")
    private void deleteTag(@PathVariable Long id) {
        tagService.deleteTag(id);
    }

    @PostMapping("/{tagId}/film/{filmId}")
    private void addTagToFilm(@PathVariable Long tagId, @PathVariable Long filmId) {
        tagService.addTagToFilm(tagId, filmId);
    }

    @DeleteMapping("/{tagId}/film/{filmId}")
    private void deleteTagFromFilm(@PathVariable Long tagId, @PathVariable Long filmId) {
        tagService.removeTagFromFilm(tagId, filmId);
    }

    @DeleteMapping("/{tagId}/film/all")
    private void deleteTagFromAllFilms(@PathVariable Long tagId) {
        tagService.removeTagFromAllFilms(tagId);
    }
}
