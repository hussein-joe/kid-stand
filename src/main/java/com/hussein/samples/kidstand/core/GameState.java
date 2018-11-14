package com.hussein.samples.kidstand.core;

import lombok.*;

import java.util.Collections;
import java.util.List;

@Value
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Builder(toBuilder = true)
public final class GameState {
    @NonNull
    private final int totalKids;
    @Builder.Default
    private int currentSequence;
    //The ID of first kid
    @NonNull
    private final int kidsIdStart;
    @NonNull
    private final List<KidProfile> currentPlayingKids;
    @Builder.Default
    private final List<KidProfile> eliminatedKids;

    //Better to copy the received list, for this class to be completely immutable
    GameState(int totalKids, int kidsIdStart, List<KidProfile> currentPlayingKids) {
        this(totalKids, 0, kidsIdStart, currentPlayingKids, Collections.emptyList());
    }

    public int remainingKidsInTheGame() {
        return currentPlayingKids.size();
    }

    public List<KidProfile> getCurrentPlayingKids() {
        if ( this.currentPlayingKids!= null && !this.currentPlayingKids.isEmpty() ) {
            return Collections.unmodifiableList(currentPlayingKids);
        }
        return Collections.emptyList();
    }

    public List<KidProfile> getEliminatedKids() {
        if ( this.eliminatedKids != null && !this.eliminatedKids.isEmpty() ) {
            return Collections.unmodifiableList(eliminatedKids);
        }
        return Collections.emptyList();
    }
}
