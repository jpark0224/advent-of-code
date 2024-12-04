package com.jpark0224.adventofcode.Day1;

import java.util.List;

public class Main {
    static int totalDistance = 0;

    public static void main(String[] args) {
        LocationLists locationLists = new LocationLists();
        locationLists.parseAndSortInput();

        System.out.println(locationLists.calculateTotalDistance());
        System.out.println(locationLists.calculateSimilarityScore());
    }
}
