package com.example.testing.examples.domain;

import com.example.testing.examples.infrastructure.launcher.FeatureLauncher;
import com.example.testing.examples.model.JokeModel;
import com.example.testing.examples.source.JokesSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public final class JokesByCategoryService {

    private static final List<String> CATEGORIES = List.of("programming", "general");

    private final FeatureLauncher featureLauncher;
    private final JokesSource newJokesSource;

    @Autowired
    public JokesByCategoryService(FeatureLauncher featureLauncher,
                                  @Qualifier("newJokesSource") JokesSource newJokesSource) {
        this.featureLauncher = featureLauncher;
        this.newJokesSource = newJokesSource;
    }

    public List<JokeModel> getByCategory(String category) {
        if (category != null && CATEGORIES.contains(category) && featureLauncher.isJokesV2SourceEnabled()) {
            return newJokesSource.getJokeByType(category);
        } else {
            return newJokesSource.getJokesList();
        }
    }


}
