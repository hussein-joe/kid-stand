package com.hussein.samples.kidstand.core;

import com.google.common.base.Preconditions;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public final class GameStateBuilder {
    private int totalKids;
    private int idStart;

    public GameStateBuilder(final int totalKids) {
        Preconditions.checkArgument(totalKids > 0, "Total kids has to be greater than 0");
        this.totalKids = totalKids;
        this.idStart = 0;
    }

    public GameStateBuilder kidsIdStartsFrom(final int kidsIdStart) {
        Preconditions.checkArgument(totalKids >= 0, "Total kids has to be greater than or equal to 0");
        this.idStart = kidsIdStart;
        return this;
    }

    public GameState buid() {
        return new GameState(totalKids, idStart, kidProfiles());
    }

    private List<KidProfile> kidProfiles() {
        return IntStream.range(idStart, idStart+totalKids)
                .boxed()
                .map(KidProfile::new)
                .collect(Collectors.toList());
    }
}
