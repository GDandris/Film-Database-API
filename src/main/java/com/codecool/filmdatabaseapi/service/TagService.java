package com.codecool.filmdatabaseapi.service;

import com.codecool.filmdatabaseapi.model.Tag;
import com.codecool.filmdatabaseapi.repository.TagRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagService {

    private final TagRepository tagRepository;
    private final JdbcTemplate jdbcTemplate;

    public TagService(TagRepository tagRepository, JdbcTemplate jdbcTemplate) {
        this.tagRepository = tagRepository;
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Tag> listAllTags(){
        return tagRepository.findAll();
    }

    public Tag addNewTag(Tag tag){
        return tagRepository.save(tag);
    }

    public Tag editTag(Long id, Tag tag) {
        tag.setId(id);
        return tagRepository.save(tag);
    }

    public void deleteTag(Long id) {
        tagRepository.deleteById(id);
    }

    public void addTagToFilm(Long tagId, Long filmId){
        String sql = "INSERT INTO FILM_TAGS  VALUES (" + filmId + ", " + tagId + ");";
        jdbcTemplate.update(sql);
    }

    public void removeTagFromFilm(Long tagId, Long filmId){
        String sql = "DELETE FROM FILM_TAGS WHERE FILM_ID = " + filmId + " AND TAGS_ID = " + tagId + ";";
        jdbcTemplate.update(sql);
    }

    public void removeTagFromAllFilms(Long tagId) {
        String sql = "DELETE FROM FILM_TAGS WHERE TAGS_ID = " + tagId + ";";
        jdbcTemplate.update(sql);
    }
}
