package com.example.testing.examples.source;

import com.example.testing.examples.model.JokeModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component("oldJokesSource")
public class RestTemplateJokesSource implements JokesSource {

    private static final String URL = "https://official-joke-api.appspot.com/{path}";
    private static final String ONE = "random_joke";
    private static final String TEN = "random_ten";

    private final RestTemplate template;

    @Autowired
    public RestTemplateJokesSource(RestTemplate template) {
        this.template = template;
    }

    @Override
    public JokeModel getJoke() {
        return template.getForObject(URL, JokeModel.class, ONE);
    }

    @Override
    public List<JokeModel> getJokeByType(String type) {
        String url = "https://official-joke-api.appspot.com/jokes/" + type + "/random";
        JokeModel[] jokes = template.getForEntity(url, JokeModel[].class).getBody();
        if (jokes != null) {
            return Arrays.asList(jokes);
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public List<JokeModel> getJokesList() {
        JokeModel[] jokes = template.getForEntity(URL, JokeModel[].class, TEN).getBody();
        if (jokes != null) {
            return Arrays.asList(jokes);
        } else {
            return new ArrayList<>();
        }
    }
}
