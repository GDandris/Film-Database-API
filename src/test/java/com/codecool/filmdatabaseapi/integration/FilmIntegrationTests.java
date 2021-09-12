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
public class FilmIntegrationTests {

    @LocalServerPort
    private int port;

    private String baseUrl;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @BeforeEach
    public void setUp() {
        this.baseUrl = "http://localhost:" + port + "/film";
        Director director = new Director("Stanley Kubrick");
        testRestTemplate.postForObject("http://localhost:" + port + "/director", director, Director.class);
    }

    @Test
    public void addNewFilm_emptyDatabase_shouldReturnSameFilm() {
        Film testFilm = new Film("Full Metal Jacket", 1987, null);
        Director director = new Director();
        director.setId(1L);
        director.setName("Stanley Kubrick");
        testFilm.setDirector(director);
        Film result = testRestTemplate.postForObject(baseUrl, testFilm, Film.class);
        assertEquals(testFilm, result);
    }

    @Test
    public void getFilms_emptyDatabase_returnsEmptyList() {
        List<Film> filmList = List.of(testRestTemplate.getForObject(baseUrl, Film[].class));
        assertEquals(0, filmList.size());
    }

    @Test
    public void getFilms_notEmptyDatabase_returnsFilmList(){
        List<Film> testFilmList = new ArrayList<>();
        Director director = new Director("Stanley Kubrick");
        director.setId(1L);
        testFilmList.add(new Film ("Full Metal Jacket", 1987, director));
        testFilmList.add(new Film ("The Shining", 1980, director));
        for (Film film : testFilmList) {
            testRestTemplate.postForObject(baseUrl, film, Film.class);
        }
        List<Film> filmList = List.of(testRestTemplate.getForObject(baseUrl, Film[].class));
        assertEquals(testFilmList.size(), filmList.size());
        for( int i = 0; i < testFilmList.size(); i++) {
            assertEquals(testFilmList.get(i), filmList.get(i));
        }
    }

    @Test
    public void getFilmById_withOnePostedFilm_returnsFilmWithSameId() {
        Director director = new Director("Stanley Kubrick");
        director.setId(1L);
        Film testFilm = new Film ("Full Metal Jacket", 1987, director);
        testFilm = testRestTemplate.postForObject(baseUrl, testFilm, Film.class);
        Film result = testRestTemplate.getForObject(baseUrl + "/" + testFilm.getId(), Film.class);
        assertEquals(testFilm.getId(), result.getId());
        assertEquals(testFilm, result);
    }

    @Test
    public void getFilmById_withMultiplePostedFilms_returnsFilmWithSameId() {
        Director director = new Director("Stanley Kubrick");
        director.setId(1L);
        Film film1 = new Film ("Full Metal Jacket", 1987, director);
        Film film2 = (new Film ("The Shining", 1980, director));
        Film film3 = (new Film ("Eyes Wide Shut", 1999, director));
        testRestTemplate.postForObject(baseUrl, film1, Film.class);
        Film testFilm = testRestTemplate.postForObject(baseUrl, film2, Film.class);
        testRestTemplate.postForObject(baseUrl, film3, Film.class);
        Film result = testRestTemplate.getForObject(baseUrl + "/" + testFilm.getId(), Film.class);
        assertEquals(testFilm.getId(), result.getId());
        assertEquals(testFilm, result);
    }

    @Test
    public void updateFilmById_withOnePostedFilm_returnsUpdatedFilm(){
        Director director = new Director("Stanley Kubrick");
        director.setId(1L);
        Film testFilm = new Film ("Full Metal Jacket", 1987, director);
        testFilm = testRestTemplate.postForObject(baseUrl, testFilm, Film.class);

        testFilm.setName("Updated name");
        testRestTemplate.put(baseUrl + "/" + testFilm.getId(), testFilm);
        Film updatedFilm = testRestTemplate.getForObject(baseUrl + "/" + testFilm.getId(), Film.class);

        assertEquals("Updated name", updatedFilm.getName());
    }

    @Test
    public void deleteFilmById_withMultiplePostedFilms_returnsRemainingFilms(){
        Director director = new Director("Stanley Kubrick");
        director.setId(1L);
        Film film1 = new Film ("Full Metal Jacket", 1987, director);
        Film film2 = new Film ("The Shining", 1980, director);
        Film film3 = new Film ("Eyes Wide Shut", 1999, director);
        List<Film> testFilms = new ArrayList<>();
        testFilms.add(film1);
        testFilms.add(film2);
        testFilms.add(film3);

        testFilms.forEach(film -> film.setId(testRestTemplate.postForObject(baseUrl, film, Film.class).getId()));

        testRestTemplate.delete(baseUrl + "/" + film2.getId());
        testFilms.remove(film2);

        List<Film> remainingFilms = List.of(testRestTemplate.getForObject(baseUrl, Film[].class));

        assertEquals(testFilms.size(), remainingFilms.size());

        for(int i = 0; i< testFilms.size(); i++) {
            assertEquals(testFilms.get(i), remainingFilms.get(i));
        }


    }

}
