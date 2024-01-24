package com.example.testing.examples.domain;

import com.example.testing.examples.common.ServiceUnavailableException;
import com.example.testing.examples.model.JokeModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@SpringBootTest
@ActiveProfiles(value = "test") //option
public class JokesRealStateTest {

    @Autowired
    private JokesService jokesService;

    @Test
    public void realTest() throws ServiceUnavailableException {
        List<JokeModel> jokes = jokesService.getJokesList();
        Assertions.assertTrue(jokes.size() > 1);
    }
}
