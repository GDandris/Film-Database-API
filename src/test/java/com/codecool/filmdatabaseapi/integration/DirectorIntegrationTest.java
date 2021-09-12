package com.codecool.filmdatabaseapi.integration;

import com.codecool.filmdatabaseapi.model.Director;
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
public class DirectorIntegrationTest {

    @LocalServerPort
    private int port;

    private String baseUrl;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @BeforeEach
    public void setUp() {
        this.baseUrl = "http://localhost:" + port + "/director";
    }

    @Test
    public void addNewDirector_emptyDatabase_returnsSameDirector() {
        Director testDirector = new Director("Stanley Kubrick");
        Director result = testRestTemplate.postForObject(baseUrl, testDirector, Director.class);
        assertEquals(testDirector, result);
    }

    @Test
    public void getDirectors_emptyDatabase_returnsEmptyList() {
        List<Director> testDirectors = List.of(testRestTemplate.getForObject(baseUrl, Director[].class));
        assertEquals(0, testDirectors.size());
    }

    @Test
    public void getDirectors_notEmptyDatabase_returnsDirectorList() {
        List<Director> testDirectorList = new ArrayList<>();
        testDirectorList.add(new Director("Stanley Kubrick"));
        testDirectorList.add(new Director("Martin Scorsese"));

        for (Director director: testDirectorList) {
            testRestTemplate.postForObject(baseUrl, director, Director.class);
        }

        List<Director> result = List.of(testRestTemplate.getForObject(baseUrl, Director[].class));
        assertEquals(testDirectorList.size(), result.size());

        for(int i = 0; i< testDirectorList.size(); i++) {
            assertEquals(testDirectorList.get(i), result.get(i));
        }
    }

    @Test
    public void updateDirectorById_WithOneDirectorPosted_returnsUpdatedDirector() {
        Director testDirector = new Director("Stanley Kubrick");
        testDirector = testRestTemplate.postForObject(baseUrl, testDirector, Director.class);

        testDirector.setName("Updated name");
        testRestTemplate.put(baseUrl + "/" + testDirector.getId(), testDirector);

        Director result = testRestTemplate.getForObject(baseUrl, Director[].class)[0];

        assertEquals(testDirector.getId(), result.getId());
        assertEquals(testDirector, result);
    }

    @Test
    public void deleteDirectorById_WithMultiplePostedFilms_returnsRemainingDirectors() {
        Director director1 = new Director("Stanley Kubrick");
        Director director2 = new Director("Martin Scorsese");
        Director director3 = new Director("Akira Kurosawa");
        List<Director> testDirectorList = new ArrayList<>();
        testDirectorList.add(director1);
        testDirectorList.add(director2);
        testDirectorList.add(director3);

        testDirectorList.forEach(director -> director.setId(testRestTemplate.postForObject(baseUrl, director, Director.class).getId()));

        testRestTemplate.delete(baseUrl + "/" + director2.getId());
        testDirectorList.remove(director2);

        List<Director> remainingDirectors = List.of(testRestTemplate.getForObject(baseUrl, Director[].class));

        assertEquals(testDirectorList.size(), remainingDirectors.size());
        for(int i = 0; i < testDirectorList.size(); i++) {
            assertEquals(testDirectorList.get(i).getName(), remainingDirectors.get(i).getName());
        }

    }
}
