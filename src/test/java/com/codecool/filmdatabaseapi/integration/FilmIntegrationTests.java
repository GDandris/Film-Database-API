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
    }

    @Test
    public void addNewFilm_emptyDatabase_shouldReturnSameFilm() {
        Film testFilm = new Film();
        testFilm.setName("Full Metal Jacket");
        testFilm.setReleaseYear(1987);
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

}
