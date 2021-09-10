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

    public void addTagToMovie(Long filmId, Long tagId){
        String sql = "INSERT INTO FILM_TAGS (?,?) VALUES" + filmId + ", " + tagId + ";";
        jdbcTemplate.update(sql);
    }

    public void removeTagFromMovie(Long filmId, Long tagId){
        String sql = "DELETE FROM FILM_TAGS WHERE FILM_ID IS" + filmId + "AND TAG_ID" + tagId + ";";
        jdbcTemplate.update(sql);
    }

    public void removeTagFromAllMovies(Long tagId) {
        String sql = "DELETE FROM FILM_TAGS WHERE TAG_ID" + tagId + ";";
        jdbcTemplate.update(sql);
    }
}
