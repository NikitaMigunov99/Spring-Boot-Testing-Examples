package com.example.testing.examples.controller;

import com.example.testing.examples.common.ServiceUnavailableException;
import com.example.testing.examples.domain.JokesByCategoryService;
import com.example.testing.examples.domain.JokesService;
import com.example.testing.examples.model.JokeModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class JokesController {

    private final JokesService jokesService;
    private final JokesByCategoryService jokesByCategoryService;

    @Autowired
    public JokesController(JokesService jokesService,
                           JokesByCategoryService jokesByCategoryService) {
        this.jokesService = jokesService;
        this.jokesByCategoryService = jokesByCategoryService;
    }

    @GetMapping("/jokes")
    public ResponseEntity<List<JokeModel>> getJokes() throws ServiceUnavailableException {
        List<JokeModel> list = jokesService.getJokesList();
        return ResponseEntity.ok(list);

    }

    @GetMapping("/joke")
    public ResponseEntity<JokeModel> getJoke() throws ServiceUnavailableException {
        JokeModel joke = jokesService.getJoke();
        return ResponseEntity.ok(joke);
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<JokeModel>> getJokeByCategory(@PathVariable String category) {
        List<JokeModel> list = jokesByCategoryService.getByCategory(category);
        return ResponseEntity.ok(list);
    }
}
