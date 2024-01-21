package com.example.testing.examples.domain;

import com.example.testing.examples.common.ServiceUnavailableException;
import com.example.testing.examples.infrastructure.analytics.AnalyticsGateway;
import com.example.testing.examples.infrastructure.analytics.JokesEventType;
import com.example.testing.examples.infrastructure.launcher.FeatureLauncher;
import com.example.testing.examples.model.JokeModel;
import com.example.testing.examples.source.JokesSource;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class JokesServiceTest {

    private final FeatureLauncher featureLauncher = mock();
    private final AnalyticsGateway analyticsGateway = mock();
    private final JokesSource newJokesSource = mock();
    private final JokesSource oldJokesSource = mock();

    @Mock
    private JokeModel firstModel;
    @Mock
    private JokeModel secondModel;

    private final JokesService jokesService = new JokesService(featureLauncher, analyticsGateway, newJokesSource, oldJokesSource);

    @Test
    public void getJoke() throws ServiceUnavailableException {
        when(featureLauncher.isJokesV2SourceEnabled()).thenReturn(true);
        when(newJokesSource.getJoke()).thenReturn(firstModel);

        JokeModel model = jokesService.getJoke();

        assertThat(model).isEqualTo(firstModel);

        //Only for example!!!
        assertThat(model).isNotEqualTo(secondModel);

        verify(analyticsGateway, times(1)).getJokesEvent(JokesEventType.ONE_JOKE);
        verify(analyticsGateway, times(1)).newJokesSourceEvent();
        verify(featureLauncher).isJokesV2SourceEnabled();
        verify(newJokesSource).getJoke();

        verifyNoMoreInteractions(analyticsGateway, featureLauncher, newJokesSource);
        verifyNoInteractions(oldJokesSource);
    }

    @Test
    public void getJokes() throws ServiceUnavailableException {
        List<JokeModel> jokes =  Arrays.asList(firstModel, secondModel);

        when(featureLauncher.isJokesV2SourceEnabled()).thenReturn(true);
        when(newJokesSource.getJokesList()).thenReturn(jokes);

        assertThat(jokesService.getJokesList()).isEqualTo(jokes);

        verify(analyticsGateway, times(1)).getJokesEvent(JokesEventType.MANY_JOKES);
        verify(analyticsGateway, times(1)).newJokesSourceEvent();
        verify(featureLauncher).isJokesV2SourceEnabled();
        verify(newJokesSource).getJokesList();

        verifyNoMoreInteractions(analyticsGateway, featureLauncher, newJokesSource);
        verifyNoInteractions(oldJokesSource);
    }

    @Test
    public void getJokeOld() throws ServiceUnavailableException {
        when(featureLauncher.isJokesV2SourceEnabled()).thenReturn(false);
        when(oldJokesSource.getJoke()).thenReturn(firstModel);

        assertThat(jokesService.getJoke()).isEqualTo(firstModel);

        verify(analyticsGateway, times(1)).getJokesEvent(JokesEventType.ONE_JOKE);
        verify(featureLauncher).isJokesV2SourceEnabled();
        verify(oldJokesSource).getJoke();

        verifyNoMoreInteractions(analyticsGateway, featureLauncher, oldJokesSource);
        verifyNoInteractions(newJokesSource);
    }


    @Test
    public void getJokesOld() throws ServiceUnavailableException {
        List<JokeModel> jokes =  Arrays.asList(firstModel, secondModel);

        when(featureLauncher.isJokesV2SourceEnabled()).thenReturn(false);
        when(oldJokesSource.getJokesList()).thenReturn(jokes);

        assertThat(jokesService.getJokesList()).isEqualTo(jokes);

        verify(analyticsGateway, times(1)).getJokesEvent(JokesEventType.MANY_JOKES);
        verify(featureLauncher).isJokesV2SourceEnabled();
        verify(oldJokesSource).getJokesList();

        verifyNoMoreInteractions(analyticsGateway, featureLauncher, oldJokesSource);
        verifyNoInteractions(newJokesSource);
    }

    @Test
    public void getJokeException() {
        when(featureLauncher.isJokesV2SourceEnabled()).thenReturn(true);
        when(newJokesSource.getJoke()).thenThrow(RuntimeException.class);

        assertThatExceptionOfType(ServiceUnavailableException.class).isThrownBy(jokesService::getJoke);

        verify(analyticsGateway, times(1)).getJokesEvent(JokesEventType.ONE_JOKE);
        verify(analyticsGateway, times(1)).newJokesSourceEvent();
        verify(analyticsGateway).errorEvent(any(RuntimeException.class));
        verify(featureLauncher).isJokesV2SourceEnabled();
        verify(newJokesSource).getJoke();

        verifyNoMoreInteractions(analyticsGateway, featureLauncher, newJokesSource);
        verifyNoInteractions(oldJokesSource);
    }
}
