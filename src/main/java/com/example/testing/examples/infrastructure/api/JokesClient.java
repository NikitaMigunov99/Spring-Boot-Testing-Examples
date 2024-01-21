package com.example.testing.examples.infrastructure.api;

import com.example.testing.examples.model.JokeModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(value = "jokes", url = "https://official-joke-api.appspot.com")
public interface JokesClient {

    @GetMapping(value = "/random_joke")
    JokeModel getJoke();

    @GetMapping(value = "/jokes/{type}/ten")
    List<JokeModel> getJokeByType(@PathVariable("type") String type);

    @GetMapping(value = "/random_ten")
    List<JokeModel> getJokesList();
}

