package com.codecool.filmdatabaseapi.integration;

import com.codecool.filmdatabaseapi.model.Director;
import com.codecool.filmdatabaseapi.model.Film;
import com.codecool.filmdatabaseapi.model.Tag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ActiveProfiles("test")
public class TagIntegrationTest {

    @LocalServerPort
    private int port;

    private String baseUrl;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @BeforeEach
    public void setUp() {
        this.baseUrl = "http://localhost:" + port + "/tag";
    }

    @Test
    public void addNewTag_emptyDatabase_returnsSameTag() {
        Tag testTag = new Tag("Fantasy");
        Tag result = testRestTemplate.postForObject(baseUrl, testTag, Tag.class);
        assertEquals(testTag, result);
    }

    @Test
    public void getAllTags_emptyDatabase_returnsEmptyList() {
        List<Tag> tags = List.of(testRestTemplate.getForObject(baseUrl, Tag[].class));
        assertEquals(0, tags.size());
    }

    @Test
    public void getAllTags_notEmptyDatabase_returnsTagList() {
        List<Tag> testTagList = new ArrayList<>();
        testTagList.add(new Tag("Fantasy"));
        testTagList.add(new Tag("Drama"));

        for (Tag tag : testTagList) {
            testRestTemplate.postForObject(baseUrl, tag, Tag.class);
        }

        List<Tag> result = List.of(testRestTemplate.getForObject(baseUrl, Tag[].class));
        assertEquals(testTagList, result);
    }

    @Test
    public void updateTagById_withOneDirectorPosted_returnsUpdatedTag() {
        Tag testTag = new Tag("Fantasy");
        testTag = testRestTemplate.postForObject(baseUrl, testTag, Tag.class);

        testTag.setName("updatedName");

        testRestTemplate.put(baseUrl+ "/"+ testTag.getId(), testTag);

        Tag result = testRestTemplate.getForObject(baseUrl, Tag[].class)[0];

        assertEquals(testTag.getId(), result.getId());
        assertEquals(testTag, result);
    }

    @Test
    public void deleteTagById_withMultipleTagsPosted_returnsRemainingTags() {
        Tag tag1 = new Tag("Fantasy");
        Tag tag2 = new Tag("Drama");
        Tag tag3 = new Tag("Action");

        List<Tag> testTagList = new ArrayList<>();
        testTagList.add(tag1);
        testTagList.add(tag2);
        testTagList.add(tag3);

        testTagList.forEach(tag -> tag.setId(testRestTemplate.postForObject(baseUrl, tag, Tag.class).getId()));

        testRestTemplate.delete(baseUrl + "/" + tag2.getId());
        testTagList.remove(tag2);

        List<Tag> remainingTags = List.of(testRestTemplate.getForObject(baseUrl, Tag[].class));

        assertEquals(testTagList, remainingTags);
    }

    @Test
    public void addTagToFilm_withOneFilmPostedWithoutTags_returnsFilmWithTags() {
        Director director = new Director();
        director.setId(1L);
        director.setName("Stanley Kubrick");
        Film testFilm = new Film("Paths of Glory", 1957, director);
        Tag testTag = new Tag("War");

        testRestTemplate.postForObject("http://localhost:" + port + "/director", director, Director.class);
        testFilm =testRestTemplate.postForObject("http://localhost:" + port + "/film", testFilm, Film.class);
        testTag = testRestTemplate.postForObject(baseUrl, testTag, Tag.class);
        testRestTemplate.postForObject(baseUrl + "/" + testTag.getId() + "/film/" + testFilm.getId(), null, Tag.class);

        Film resultFilm = testRestTemplate.getForObject("http://localhost:" + port + "/film/" + testFilm.getId(), Film.class);
        List<Tag> tags = List.of(testTag);

        assertEquals(tags, resultFilm.getTags());
    }

    @Test
    public void deleteTagFromFilm_withOneFilmPostedWithMultipleTags_returnsFilmWithRemainingTags () {
        Director director = new Director();
        director.setId(1L);
        director.setName("Stanley Kubrick");
        Film testFilm = new Film("Paths of Glory", 1957, director);
        Tag tag1 = new Tag("War");
        tag1.setId(1L);
        Tag tag2 = new Tag("Sci-fi");
        tag2.setId(2L);
        List<Tag> tags = List.of(tag1, tag2);
        testFilm.setTags(tags);

        testRestTemplate.postForObject("http://localhost:" + port + "/director", director, Director.class);
        testFilm =testRestTemplate.postForObject("http://localhost:" + port + "/film", testFilm, Film.class);
        tag1 = testRestTemplate.postForObject(baseUrl, tag1, Tag.class);
        tag2 = testRestTemplate.postForObject(baseUrl, tag2, Tag.class);

        testRestTemplate.delete(baseUrl + "/"+ tag2.getId() +"/film/" + testFilm.getId());

        testFilm.getTags().remove(tag2);
        Film resultFilm = testRestTemplate.getForObject("http://localhost:" + port + "/film/" + testFilm.getId(), Film.class);

        assertEquals(testFilm.getTags(), resultFilm.getTags());
    }
    @Test
    public void deleteTagFromAllFilms_withMultipleFilmPostedWithMultipleTags_returnsFilmsWithRemainingTags () {
        Director director = new Director();
        director.setId(1L);
        director.setName("Stanley Kubrick");
        Film film1 = new Film("Paths of Glory", 1957, director);
        Film film2 = new Film("Full Metal Jacket", 1987, director);
        Tag tag1 = new Tag("War");
        Tag tag2 = new Tag("Sci-fi");
        tag1 = testRestTemplate.postForObject(baseUrl, tag1, Tag.class);
        tag2 = testRestTemplate.postForObject(baseUrl, tag2, Tag.class);
        List<Tag> tags = List.of(tag1, tag2);
        film1.setTags(tags);
        film2.setTags(tags);

        testRestTemplate.postForObject("http://localhost:" + port + "/director", director, Director.class);
        film1 =testRestTemplate.postForObject("http://localhost:" + port + "/film", film1, Film.class);
        film2 =testRestTemplate.postForObject("http://localhost:" + port + "/film", film2, Film.class);

        testRestTemplate.delete(baseUrl + "/"+ tag2.getId() +"/film/all");

        film1.getTags().remove(tag2);
        film2.getTags().remove(tag2);
        List<Film> testFilmList = List.of(film1, film2);
        List<Film> resultFilmList = List.of(testRestTemplate.getForObject("http://localhost:" + port + "/film", Film[].class));

        for(int i = 0; i< testFilmList.size(); i++) {
            assertEquals(testFilmList.get(i).getTags(), resultFilmList.get(i).getTags());
        }
    }
}
