package com.hussein.samples.kidstand.service;

import com.hussein.samples.kidstand.core.GameEngine;
import com.hussein.samples.kidstand.core.GameState;
import com.hussein.samples.kidstand.core.GameStateBuilder;
import com.hussein.samples.kidstand.core.KidProfile;
import com.hussein.samples.kidstand.model.GamePlayResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class KidStandingGameServiceTest {

    private static final int KID_ID_START = 1;
    @Mock
    private GameEngine gameEngine;
    private int k = 2;
    private int n = 4;
    private KidStandingGameService target;

    @BeforeEach
    void setUp() {
        target = new KidStandingGameService(gameEngine, KID_ID_START);

    }

    @Test
    void shouldDelegateToGameEngine() {
        GameState resultState = GameState.builder()
                .currentPlayingKids(singletonList(new KidProfile(555)))
                .eliminatedKids(Arrays.asList(new KidProfile(10), new KidProfile(20)))
                .build();
        when(gameEngine.run(buildInitialState(n), k)).thenReturn(resultState);

        GamePlayResult actualResult = target.play(n, k);

        assertThat(actualResult.getWinningKidId()).isEqualTo(555);
        assertThat(actualResult.getEliminatedKidIds()).containsOnly(10, 20);
    }

    @Test
    void shouldThrowExceptionWhenThereIsNoWinner() {
        GameState resultState = GameState.builder()
                .currentPlayingKids(emptyList())
                .eliminatedKids(emptyList())
                .build();
        when(gameEngine.run(buildInitialState(n), k)).thenReturn(resultState);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> target.play(n, k));
        assertThat(exception.getMessage()).contains("Unexpected to not have a winner");
    }

    private GameState buildInitialState(final int totalKids) {
        return new GameStateBuilder(totalKids)
                .kidsIdStartsFrom(KID_ID_START)
                .buid();
    }
}