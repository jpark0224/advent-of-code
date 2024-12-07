package com.jpark0224.adventofcode.Day3;


import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    static int totalDistance = 0;

    public static void main(String[] args) {
        Multiplication instructions = new Multiplication();
        instructions.parseInput();
        System.out.println(instructions.getInstructions());
    }
}
