package com.example.testing_poc;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import okhttp3.mockwebserver.Dispatcher;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@CucumberContextConfiguration
public class JokeStepDefinitions {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JokeRepository jokeRepository;

    private MockWebServer mockWebServer;

    @Before
    public void setUpMockServer() throws IOException {
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

    @After
    public void tearDown() throws IOException {
        mockWebServer.shutdown();
    }

    @Given("the Joke API is running")
    public void theJokeAPIIsRunning() {
        System.setProperty("joke.api.base-url", mockWebServer.url("/").toString());
    }

    @When("I request a random joke with user {string}")
    public void iRequestARandomJokeWithUser(String user) throws Exception {
        MvcResult result = mockMvc.perform(get("/random")
                        .param("user", user)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn();

        // Save result for later verification
        ScenarioContext.put("mvcResult", result);
    }

    @Then("the response status should be {int}")
    public void theResponseStatusShouldBe(int status) throws Exception {
        MvcResult result = (MvcResult) ScenarioContext.get("mvcResult");
        mockMvc.perform(asyncDispatch(result))
                .andExpect(status().is(status));
    }

    @Then("the response should contain the joke {string}")
    public void theResponseShouldContainTheJoke(String joke) throws Exception {
        MvcResult result = (MvcResult) ScenarioContext.get("mvcResult");
        mockMvc.perform(asyncDispatch(result))
                .andExpect(jsonPath("$.joke").value(joke));
    }

    @Then("the joke should be saved in the database for user {string}")
    public void theJokeShouldBeSavedInTheDatabaseForUser(String user) {
        List<JokeHistoryEntry> entries = jokeRepository.findAll();
        Assertions.assertEquals(1, entries.size());
        JokeHistoryEntry entry = entries.get(0);
        Assertions.assertEquals("What did the Dorito farmer say to the other Dorito farmer? Cool Ranch!", entry.getJoke());
        Assertions.assertEquals(user, entry.getUser());
    }

    @Then("the error message should be {string}")
    public void theErrorMessageShouldBe(String errorMessage) throws Exception {
        MvcResult result = (MvcResult) ScenarioContext.get("mvcResult");
        mockMvc.perform(asyncDispatch(result))
                .andExpect(jsonPath("$.['getJoke.user']").value(errorMessage));
    }
}

