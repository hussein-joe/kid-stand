package com.hussein.samples.kidstand.model;

import lombok.Value;

import java.util.List;

@Value
public class GamePlayResult {
    private final int winningKidId;
    private final List<Integer> eliminatedKidIds;
}
