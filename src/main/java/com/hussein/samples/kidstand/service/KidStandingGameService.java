package com.hussein.samples.kidstand.service;

import com.hussein.samples.kidstand.core.GameEngine;
import com.hussein.samples.kidstand.core.GameState;
import com.hussein.samples.kidstand.core.GameStateBuilder;
import com.hussein.samples.kidstand.core.KidProfile;
import com.hussein.samples.kidstand.model.GamePlayResult;

import java.util.List;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkArgument;

public class KidStandingGameService {
    private final GameEngine gameEngine;
    private final int kidIdStart;

    public KidStandingGameService(GameEngine gameEngine, int kidIdStart) {
        this.gameEngine = gameEngine;
        this.kidIdStart = kidIdStart;
    }

    /**
     *
     * @param totalKids this is n
     * @param steps this is k
     * @return
     */
    public GamePlayResult play(final int totalKids, final int steps) {
        checkArgument(totalKids > 0, "Total kids has to be greater than 0");
        checkArgument(steps > 0, "Steps (aka k) has to be greater than k");
        final GameState gameState = new GameStateBuilder(totalKids)
                .kidsIdStartsFrom(kidIdStart)
                .buid();

        GameState resultState = gameEngine.run(gameState, steps);
        return new GamePlayResult(winner(resultState), eliminatedKidIds(resultState));
    }

    private int winner(final GameState resultState) {
        return resultState.getCurrentPlayingKids().stream().findFirst()
                .orElseThrow(() -> new RuntimeException("Unexpected to not have a winner")).getId();
    }

    private List<Integer> eliminatedKidIds(final GameState resultState) {
        return resultState.getEliminatedKids().stream()
                .map(KidProfile::getId)
                .collect(Collectors.toList());
    }
}
