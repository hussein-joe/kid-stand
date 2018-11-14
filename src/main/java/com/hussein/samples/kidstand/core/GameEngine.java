package com.hussein.samples.kidstand.core;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static java.util.Objects.requireNonNull;

/**
 * In this class I chose to implement it in a more functional way to support the following
 * Idempotency: calling run many times with the same input would result in the same output
 * No side-effect: there is no side effect by calling run, as there is no state stored inside the engine class.
 *
 * In this approach I do a lot of copying when removing kids from current list to eliminated one, this might cause stack overflow error.
 * Another alternative approach was to have one list, mark eliminated kids profile, store the round cycle that each kid
 * was removed in. At the end of the game we would have only one kid not market, sort the marked kids by stored round cycle
 * to get the order of eliminating the kids from the game.
 */
public final class GameEngine {

    public GameState run(final GameState state, final int steps) {
        requireNonNull(state);
        checkArgument(steps > 0);
        checkArgument(steps < state.getTotalKids());

        return doRun(state, steps);
    }

    //Recursion with one stopping condition.
    private GameState doRun(final GameState currentRoundState, final int kidsToBeEliminated) {
        final int remainingKidsInTheGame = currentRoundState.remainingKidsInTheGame();
        if (remainingKidsInTheGame == 1) {
            return currentRoundState;
        }

        int indexToBeEliminated = currentRoundState.getCurrentSequence() + kidsToBeEliminated;

        //This is like a circular list, if it is required to remove element 3 of list with size 2
        //it should remove the element at index zero. It works as if it
        indexToBeEliminated = (indexToBeEliminated - 1) % remainingKidsInTheGame;
        List<KidProfile> currentPlayingKids = new ArrayList<>(currentRoundState.getCurrentPlayingKids());
        List<KidProfile> eliminatedKids = new ArrayList<>(currentRoundState.getEliminatedKids());

        KidProfile elminiatedKid = currentPlayingKids.remove(indexToBeEliminated);
        eliminatedKids.add(elminiatedKid);

        GameState nextRoundState = currentRoundState.toBuilder()
                .currentPlayingKids(currentPlayingKids)
                .currentSequence(indexToBeEliminated)
                .eliminatedKids(eliminatedKids)
                .build();

        return doRun(nextRoundState, kidsToBeEliminated);
    }
}
