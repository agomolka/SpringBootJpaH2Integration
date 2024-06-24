package com.example.testing_poc;

import okhttp3.mockwebserver.Dispatcher;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class JokeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JokeRepository jokeRepository;

    private static MockWebServer mockWebServer;

    @BeforeAll
    static void setUpMockServer() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.setDispatcher(new Dispatcher() {
            @Override
            public MockResponse dispatch(RecordedRequest request) {
                if ("/random_joke".equals(request.getPath())) {
                    String json = """
                    {
                        "type": "general",
                        "setup": "What did the Dorito farmer say to the other Dorito farmer?",
                        "punchline": "Cool Ranch!",
                        "id": 173
                    }
                    """;
                    return new MockResponse()
                            .setBody(json)
                            .addHeader("Content-Type", "application/json");
                }
                return new MockResponse().setResponseCode(404);
            }
        });
        mockWebServer.start();
    }

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("joke.api.base-url", () -> mockWebServer.url("/").toString());
    }

    @AfterAll
    static void tearDown() throws Exception {
        mockWebServer.shutdown();
    }

    @Test
    public void testGetJoke() throws Exception {
        mockMvc.perform(get("/random")
                        .param("user", "Richard")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.joke").value("What did the Dorito farmer say to the other Dorito farmer? Cool Ranch!"));

        List<JokeHistoryEntry> entries = jokeRepository.findAll();
        assertEquals(entries.size(), 1);
        JokeHistoryEntry entry = entries.get(0);
        assertEquals(entry.getJoke(), "What did the Dorito farmer say to the other Dorito farmer? Cool Ranch!");
        assertEquals(entry.getUser(), "Richard");
    }
}