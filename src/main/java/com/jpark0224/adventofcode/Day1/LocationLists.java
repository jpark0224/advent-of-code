package com.jpark0224.adventofcode.Day1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

public class LocationLists {
    private final List<Integer> leftList;
    private final List<Integer> rightList;

    public LocationLists() {
        this.leftList = new ArrayList<>();
        this.rightList = new ArrayList<>();
    }

    public void parseAndSortInput() {
        String filePath = "src/input/Day1.txt";

        try (Stream<String> stream = Files.lines(Paths.get(filePath))) {
            stream.map(line -> line.split(" {3}")).forEach(pairs -> {
                leftList.add(Integer.parseInt(pairs[0]));
                rightList.add(Integer.parseInt(pairs[1]));

                Collections.sort(leftList);
                Collections.sort(rightList);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int calculateTotalDistance() {
        int totalDistance = 0;
        for (int i = 0; i < Math.min(leftList.size(), rightList.size()); i++) {
            totalDistance += Math.abs(leftList.get(i) - rightList.get(i));
        }
        return totalDistance;
    }

    public int calculateSimilarityScore() {
        int similarityScore = 0;
        Map<Integer, Integer> countMap = new HashMap<>();

        for (Integer leftLocation:leftList) {
            for (Integer rightLocation:rightList) {
                if (Objects.equals(leftLocation, rightLocation)) {
                    countMap.put(leftLocation, countMap.getOrDefault(leftLocation, 0) + 1);
                }
            }
        }

        for (Map.Entry<Integer, Integer> entry:countMap.entrySet()) {
            similarityScore += entry.getKey() * entry.getValue();
        }

        return similarityScore;
    }
}