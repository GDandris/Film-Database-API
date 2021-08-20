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
    public void addNewDirector_emptyDatabase_shouldReturnSameDirector() {
        Director testDirector = new Director(null, "Stanley Kubrick");
        Director result = testRestTemplate.postForObject(baseUrl, testDirector, Director.class);
        assertEquals(testDirector.getName(), result.getName());
    }

}
