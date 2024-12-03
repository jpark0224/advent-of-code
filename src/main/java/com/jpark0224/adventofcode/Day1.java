package com.jpark0224.adventofcode;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;
import java.util.ArrayList;

public class Day1 {
    public static void parseInput() {
        String filePath = "src/input/Day1.txt";

        List<Integer> leftList = new ArrayList<>();
        List<Integer> rightList = new ArrayList<>();

        try (Stream<String> stream = Files.lines(Paths.get(filePath))) {
            stream.map(line -> line.split("   ")).forEach(pairs -> {
                leftList.add(Integer.parseInt(pairs[0]));
                rightList.add(Integer.parseInt(pairs[1]));
            });

            System.out.println(leftList)
            System.out.println(rightList)
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        parseInput();
    }


}