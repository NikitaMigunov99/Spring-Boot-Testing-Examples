package com.example.testing.examples.source;

import com.example.testing.examples.model.JokeModel;

import java.util.List;

public interface JokesSource {

    JokeModel getJoke();

    List<JokeModel> getJokeByType(String type);

    List<JokeModel> getJokesList();
}
