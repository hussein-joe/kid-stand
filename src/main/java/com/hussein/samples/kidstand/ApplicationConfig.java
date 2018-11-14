package com.hussein.samples.kidstand;

import com.hussein.samples.kidstand.core.GameEngine;
import com.hussein.samples.kidstand.service.KidStandingGameService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {
    @Bean
    public KidStandingGameService gameService() {
        return new KidStandingGameService(new GameEngine(), 1);
    }
}
