package com.example.testing.examples.domain;

import com.example.testing.examples.infrastructure.launcher.FeatureLauncher;
import com.example.testing.examples.model.JokeModel;
import com.example.testing.examples.source.JokesSource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class JokesByCategoryServiceTest {

    private final JokesSource newJokesSource = mock();
    private final FeatureLauncher featureLauncher = mock();
    @Mock
    private JokeModel firstModel;
    @Mock
    private JokeModel secondModel;

    private final List<JokeModel> jokes = Arrays.asList(firstModel, secondModel);

    private final JokesByCategoryService jokesService = new JokesByCategoryService(featureLauncher, newJokesSource);

    @BeforeEach
    public void setUp() {
        when(featureLauncher.isJokesV2SourceEnabled()).thenReturn(true);
    }


    @ParameterizedTest
    @ValueSource(strings = {"programming", "general"})
    public void getJokeByCategory(String category) {
        when(newJokesSource.getJokeByType(category)).thenReturn(jokes);

        List<JokeModel> list = jokesService.getByCategory(category);

        Assertions.assertEquals(list, jokes);
        verify(newJokesSource).getJokeByType(category);
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"fake_category"})
    public void getJoke(String category) {
        when(newJokesSource.getJokesList()).thenReturn(jokes);

        List<JokeModel> list = jokesService.getByCategory(category);

        Assertions.assertEquals(list, jokes);
        verify(newJokesSource).getJokesList();
    }

    @ParameterizedTest
    @MethodSource("getArguments")
    public void getJoke(String category, boolean isEnabled) {
        when(newJokesSource.getJokesList()).thenReturn(jokes);
        when(featureLauncher.isJokesV2SourceEnabled()).thenReturn(isEnabled);

        List<JokeModel> list = jokesService.getByCategory(category);

        Assertions.assertEquals(list, jokes);
        verify(newJokesSource).getJokesList();
    }

    @ParameterizedTest
    @CsvSource(value = {
            "fake_category,true",
            "programming,false",
            "fake_category,false"}
    )
    public void getJokeWithCsv(String category, boolean isEnabled) {
        when(newJokesSource.getJokesList()).thenReturn(jokes);
        when(featureLauncher.isJokesV2SourceEnabled()).thenReturn(isEnabled);

        List<JokeModel> list = jokesService.getByCategory(category);

        Assertions.assertEquals(list, jokes);
        verify(newJokesSource).getJokesList();
    }

    private static Stream<Arguments> getArguments() {
        return Stream.of(
                Arguments.of("fake_category", true),
                Arguments.of("programming", false),
                Arguments.of("fake_category", false)
        );
    }
}
