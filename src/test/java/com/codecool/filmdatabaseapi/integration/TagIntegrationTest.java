package com.codecool.filmdatabaseapi.integration;

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

}
