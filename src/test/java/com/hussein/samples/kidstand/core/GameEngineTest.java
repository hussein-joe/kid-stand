package com.hussein.samples.kidstand.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class GameEngineTest {
    private static final int TOTAL_KIDS = 5;

    private GameEngine target;
    @BeforeEach
    void setUp() {
        target = new GameEngine();
    }

    @Test
    void shouldThrowExceptionWhenStepsIsLessThanZero() {
        final GameState gameState = new GameStateBuilder(TOTAL_KIDS)
                .buid();

        assertThrows(IllegalArgumentException.class, () -> target.run(gameState, -1));
    }

    @Test
    void shouldThrowExceptionWhenStepsIsGreaterThanTotalKidsInTheGame() {
        final GameState gameState = new GameStateBuilder(TOTAL_KIDS)
                .buid();

        assertThrows(IllegalArgumentException.class, () -> target.run(gameState, gameState.getTotalKids()+1));
    }

    @Test
    void givenTotalKidsIs3ShouldReturnKidWithId3WhenStepsIs2() {
        final GameState gameState = new GameStateBuilder(3)
                .kidsIdStartsFrom(1)
                .buid();

        assertThat(target.run(gameState, 2).getCurrentPlayingKids())
                .containsOnly(new KidProfile(3));
    }

    @Test
    void givenTotalKidsIs5ShouldReturnKidWithId5WhenStepsIs4() {
        final GameState gameState = new GameStateBuilder(5)
                .kidsIdStartsFrom(1)
                .buid();

        assertThat(target.run(gameState, 3).getCurrentPlayingKids())
                .containsOnly(new KidProfile(4));
    }

    @Test
    void runShouldBeIdempotentWithNoSideEffectOrStateStoredInEngine() {
        GameEngine gameEngine1 = new GameEngine();
        GameEngine gameEngine2 = new GameEngine();
        final GameState gameState = new GameStateBuilder(5)
                .kidsIdStartsFrom(1)
                .buid();

        GameState resultState1 = gameEngine1.run(gameState, 3);
        GameState resultState2 = gameEngine2.run(gameState, 3);

        assertThat(resultState1).isEqualTo(resultState2);
    }

    @Test
    void name() {
        final GameState gameState = new GameStateBuilder(5000)
                .kidsIdStartsFrom(1)
                .buid();
        long start = System.currentTimeMillis();
        target.run(gameState, 3);
        System.out.println(String.format("It took %s", System.currentTimeMillis()-start));
    }
}