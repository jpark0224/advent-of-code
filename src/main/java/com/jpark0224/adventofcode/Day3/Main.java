package com.jpark0224.adventofcode.Day3;


import java.util.List;

public class Main {
    static int totalDistance = 0;

    public static void main(String[] args) {
        Multiplication instructionsPart2 = new Multiplication();

        instructionsPart2.parseInputPart2();
        List<String> filteredInstructions = instructionsPart2.filterInstructions();
        System.out.println(instructionsPart2.multiplyAndAdd(filteredInstructions));
    }
}
