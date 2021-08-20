package com.codecool.filmdatabaseapi.integration;

import com.codecool.filmdatabaseapi.model.Director;
import com.codecool.filmdatabaseapi.model.Film;
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
        Director director = new Director(1L, "Stanley Kubrick");
        testRestTemplate.postForObject("http://localhost:" + port + "/director", director, Director.class);
    }

    @Test
    public void addNewFilm_emptyDatabase_shouldReturnSameFilm() {
        Film testFilm = new Film(null, "Full Metal Jacket", 1987, null);
        Director director = new Director();
        director.setId(1L);
        director.setName("Stanley Kubrick");
        testFilm.setDirector(director);
        Film result = testRestTemplate.postForObject(baseUrl, testFilm, Film.class);
        assertEquals(testFilm.getName(), result.getName());
        assertEquals(testFilm.getReleaseYear(), result.getReleaseYear());
        assertEquals(testFilm.getDirector().getName(), result.getDirector().getName());
    }

    @Test
    public void getFilms_emptyDatabase_returnsEmptyList() {
        List<Film> filmList = List.of(testRestTemplate.getForObject(baseUrl, Film[].class));
        assertEquals(0, filmList.size());
    }

    @Test
    public void getFilms_notEmptyDatabase_returnsFilmList(){
        List<Film> testFilmList = new ArrayList<>();
        testFilmList.add(new Film (null, "Full Metal Jacket", 1987, new Director(1L, "Stanley Kubrick")));
        testFilmList.add(new Film (null, "The Shining", 1980, new Director(1L, "Stanley Kubrick")));
        for (Film film : testFilmList) {
            testRestTemplate.postForObject(baseUrl, film, Film.class);
        }
        List<Film> filmList = List.of(testRestTemplate.getForObject(baseUrl, Film[].class));
        assertEquals(testFilmList.size(), filmList.size());
        for( int i = 0; i < testFilmList.size(); i++) {
            assertEquals(testFilmList.get(i).getName(), filmList.get(i).getName());
            assertEquals(testFilmList.get(i).getReleaseYear(), filmList.get(i).getReleaseYear());
            assertEquals(testFilmList.get(i).getDirector().getName(), filmList.get(i).getDirector().getName());
        }
    }

    @Test
    public void getFilmById_withOnePostedFilm_returnsFilmWithSameId() {
        Film testFilm = new Film (null, "Full Metal Jacket", 1987, new Director(1L, "Stanley Kubrick"));
        testFilm = testRestTemplate.postForObject(baseUrl, testFilm, Film.class);
        Film result = testRestTemplate.getForObject(baseUrl + "/" + testFilm.getId(), Film.class);
        assertEquals(testFilm.getId(), result.getId());
        assertEquals(testFilm.getName(), result.getName());
        assertEquals(testFilm.getReleaseYear(), result.getReleaseYear());
        assertEquals(testFilm.getDirector().getName(), result.getDirector().getName());
    }

    @Test
    public void getFilmById_withMultiplePostedFilms_returnsFilmWithSameId() {
        Film film1 = new Film (null, "Full Metal Jacket", 1987, new Director(1L, "Stanley Kubrick"));
        Film film2 = (new Film (null, "The Shining", 1980, new Director(1L, "Stanley Kubrick")));
        Film film3 = (new Film (null, "Eyes Wide Shut", 1999, new Director(1L, "Stanley Kubrick")));
        testRestTemplate.postForObject(baseUrl, film1, Film.class);
        Film testFilm = testRestTemplate.postForObject(baseUrl, film2, Film.class);
        testRestTemplate.postForObject(baseUrl, film3, Film.class);
        Film result = testRestTemplate.getForObject(baseUrl + "/" + testFilm.getId(), Film.class);
        assertEquals(testFilm.getId(), result.getId());
        assertEquals(testFilm.getName(), result.getName());
        assertEquals(testFilm.getReleaseYear(), result.getReleaseYear());
        assertEquals(testFilm.getDirector().getName(), result.getDirector().getName());
    }

    @Test
    public void updateFilmById_withOnePostedSong_returnsUpdatedFilm(){
        Film testFilm = new Film (null, "Full Metal Jacket", 1987, new Director(1L, "Stanley Kubrick"));
        testFilm = testRestTemplate.postForObject(baseUrl, testFilm, Film.class);

        testFilm.setName("Updated name");
        testRestTemplate.put(baseUrl + "/" + testFilm.getId(), testFilm);
        Film updatedFilm = testRestTemplate.getForObject(baseUrl + "/" + testFilm.getId(), Film.class);

        assertEquals("Updated name", updatedFilm.getName());
    }

}
