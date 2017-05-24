package com.avanzarit.apps.vendormgmt;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;

/**
 * This class implements {@link ApplicationEventPublisherAware}.
 * Spring Framework will notify this class with the availability of {@link ApplicationEventPublisher}
 * once the spring context is initialized
 */
public class EventPublisher implements ApplicationEventPublisherAware {
    private ApplicationEventPublisher publisher;



    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.publisher = applicationEventPublisher;
    }
}