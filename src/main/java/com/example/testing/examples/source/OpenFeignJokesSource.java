package com.example.testing.examples.source;

import com.example.testing.examples.infrastructure.api.JokesClient;
import com.example.testing.examples.model.JokeModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("newJokesSource")
public class OpenFeignJokesSource implements JokesSource {

    private final JokesClient client;

    @Autowired
    public OpenFeignJokesSource(JokesClient client) {
        this.client = client;
    }

    @Override
    public JokeModel getJoke() {
        return client.getJoke();
    }

    @Override
    public List<JokeModel> getJokeByType(String type) {
        return client.getJokeByType(type);
    }

    @Override
    public List<JokeModel> getJokesList() {
        return client.getJokesList();
    }
}
