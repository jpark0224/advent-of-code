package com.jpark0224.adventofcode.Day2;


public class Main {
    static int totalDistance = 0;

    public static void main(String[] args) {
        Reports reports = new Reports();
        reports.parseInput();
        System.out.println("Part 1: " + reports.calculateTotalSafeReports());
        System.out.println("Part 2: " + reports.calculateTotalSafeReportsWithProblemDampener());
    }
}
