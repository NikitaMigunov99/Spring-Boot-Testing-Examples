package com.example.testing.examples.infrastructure.analytics;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class AnalyticsGateway {

    private static final Logger LOGGER = LoggerFactory.getLogger(AnalyticsGateway.class);

    public void getJokesEvent(JokesEventType eventType) {
        LOGGER.info("jokes event with type " + eventType);
    }

    public void newJokesSourceEvent() {
        LOGGER.info("new jokes source event");
    }

    public void errorEvent(Exception exception) {
        LOGGER.info("Error event with exception " + exception);
    }

}
