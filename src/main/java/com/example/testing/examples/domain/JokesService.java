package com.example.testing.examples.domain;

import com.example.testing.examples.common.ServiceUnavailableException;
import com.example.testing.examples.infrastructure.analytics.AnalyticsGateway;
import com.example.testing.examples.infrastructure.analytics.JokesEventType;
import com.example.testing.examples.infrastructure.launcher.FeatureLauncher;
import com.example.testing.examples.model.JokeModel;
import com.example.testing.examples.source.JokesSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public final class JokesService {

    private final static String MESSAGE = "New service is unavailable";

    private final FeatureLauncher featureLauncher;
    private final AnalyticsGateway analyticsGateway;
    private final JokesSource newJokesSource;
    private final JokesSource oldJokesSource;

    @Autowired
    public JokesService(FeatureLauncher featureLauncher,
                        AnalyticsGateway analyticsGateway,
                        @Qualifier("newJokesSource") JokesSource newJokesSource,
                        @Qualifier("oldJokesSource") JokesSource oldJokesSource) {
        this.featureLauncher = featureLauncher;
        this.analyticsGateway = analyticsGateway;
        this.newJokesSource = newJokesSource;
        this.oldJokesSource = oldJokesSource;
    }


    public JokeModel getJoke() throws ServiceUnavailableException {
        analyticsGateway.getJokesEvent(JokesEventType.ONE_JOKE);

        try {
            if (featureLauncher.isJokesV2SourceEnabled()) {
                analyticsGateway.newJokesSourceEvent();
                return newJokesSource.getJoke();
            } else {
                return oldJokesSource.getJoke();
            }
        } catch (Exception e) {
            analyticsGateway.errorEvent(e);
            throw new ServiceUnavailableException(MESSAGE);
        }
    }

    public List<JokeModel> getJokesList() throws ServiceUnavailableException {
        analyticsGateway.getJokesEvent(JokesEventType.MANY_JOKES);

        try {
            if (featureLauncher.isJokesV2SourceEnabled()) {
                analyticsGateway.newJokesSourceEvent();
                return newJokesSource.getJokesList();
            } else {
                return oldJokesSource.getJokesList();
            }
        } catch (Exception e) {
            analyticsGateway.errorEvent(e);
            throw new ServiceUnavailableException(MESSAGE);
        }
    }

}
