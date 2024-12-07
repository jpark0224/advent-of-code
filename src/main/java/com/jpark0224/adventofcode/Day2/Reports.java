package com.jpark0224.adventofcode.Day2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Reports {
    private List<List<Integer>> reports;

    public Reports() {
        this.reports = new ArrayList<>();
    }

    public void parseInput() {
        String filePath = "src/input/Day2.txt";

        try (Stream<String> stream = Files.lines(Paths.get(filePath))) {
            // Split line into separate strings
            // Why Stream.of(line.split(" "))?
            // line.split(" ") returns an array of String objects. .map is a method on streams, not arrays.
            // Stream.of() converts an array into a stream which enables the use of map().
            // Arrays.stream() also works.
            reports = stream.map(line -> Stream.of(line.split(" ")).
                    // Convert each string to an integer
                    map(Integer::parseInt)
                    // Collect into a list
                    .collect(Collectors.toList()))
                    // Collect all lists into a nested list
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int calculateTotalSafeReports() {
        int totalSafe = 0;
        boolean asc = false;
        boolean desc = false;

        for (List<Integer> report:reports) {
            asc = false;
            desc = false;
            for (int i = 0; i < report.size() - 1; i++) {
                if (Objects.equals(report.get(i), report.get(i + 1))) {
                    break;
                } else if (report.get(i) < report.get(i + 1)) {
                    if (report.get(i) + 3 < report.get(i + 1)) {
                        break;
                    } else {
                        if (desc) {
                            break;
                        } else {
                            if (i == 0) {
                                asc = true;
                            } else if (i == report.size() - 2) {
                                totalSafe++;
                            }
                        }
                    }
                } else if (report.get(i) > report.get(i + 1)) {
                    if (report.get(i) - 3 > report.get(i + 1)) {
                        break;
                    } else {
                        if (asc) {
                            break;
                        } else {
                            if (i == 0) {
                                desc = true;
                            } else if (i == report.size() - 2) {
                                totalSafe++;
                            }
                        }
                    }
                }
            }
        }
        return totalSafe;
    }

    public boolean hasSafeDifference(int i, int j) {
        return Math.abs(i - j) <= 3;
    }

    public boolean isSafeThree(List<Integer> list, boolean isAscending, boolean isDescending) {
        boolean isAscendingThree = list.get(0) < list.get(1) && list.get(1) < list.get(2);
        boolean isDescendingThree = list.get(0) > list.get(1) && list.get(1) > list.get(2);

        boolean allSafeDifference = hasSafeDifference(list.get(0), list.get(1)) && hasSafeDifference(list.get(1), list.get(2));

        if (isAscending && isAscendingThree) {
            return allSafeDifference;
        } else if (isDescending && isDescendingThree) {
            return allSafeDifference;
        } else if (!isAscending && !isDescending && (isAscendingThree || isDescendingThree)) {
            return allSafeDifference;
        }

        return false;
    }

    private List<Integer> removeIndex(List<Integer> list, int index) {
        // create a copy
        List<Integer> tempList = new ArrayList<>(list);
        tempList.remove(index);
        return tempList;
    }

    public int findBadLevel(List<Integer> list, boolean isAscending, boolean isDescending) {
        // figure out which one is bad
        // check if i is the bad one
        for (int i = 0; i < list.size() - 2; i++) {
            // test removing index i
            if (isSafeThree(removeIndex(list, i), isAscending, isDescending)) {
                return i;
            }
            if (isSafeThree(removeIndex(list, i + 1), isAscending, isDescending)) {
                return i + 1;
            }
            if (isSafeThree(removeIndex(list, i + 2), isAscending, isDescending)) {
                return i + 2;
            }
        }
        return -1;
    }

    public int calculateTotalSafeReportsWithProblemDampener() {
        int totalSafe = 0;
        int badLevelIndex;
        boolean badLevelRemoved;
        boolean isAscending;
        boolean isDescending;

        for (List<Integer> report : reports) {
            badLevelIndex = -1;
            badLevelRemoved = false;
            isAscending = false;
            isDescending = false;
            for (int i = 0; i < report.size() - 2; i++) {
                System.out.println(report);
                List<Integer> tempListOfThree = new ArrayList<>(report.subList(i, i + 3));
                if (!isSafeThree(tempListOfThree, isAscending, isDescending)) {
                    // figure out which one is bad
                    if (i == report.size() - 3) {
                        List<Integer> tempListOfThreeForLast = new ArrayList<>(report.subList(i - 1, i + 3));
                        badLevelIndex = findBadLevel(tempListOfThreeForLast, isAscending, isDescending);
                    } else {
                        List<Integer> tempListOfFour = new ArrayList<>(report.subList(i, i + 4));
                        badLevelIndex = findBadLevel(tempListOfFour, isAscending, isDescending);
                    }

                    // if bad level is identified
                    if (badLevelIndex > -1) {
                        if (badLevelRemoved) {
                            System.out.println("Bad level found again");
                            break;
                        } else {
                            if (i == report.size() - 3) {
                                totalSafe++;
                                System.out.println("added to totalSafe. Total: " + totalSafe);
                            }
                            System.out.println("when i = " + i + ", bad level index: " + badLevelIndex);
                            report.remove(badLevelIndex);
                            badLevelRemoved = true;
                            System.out.println("badLevel removed");
                        }
                    } else {
                        System.out.println("can't make it safe by getting rid of a level.");
                        break;
                    }
                } else {
                    System.out.println("when i = " + i + ", three consecutive levels are safe.");
                }

                if (i == 0) {
                    if (report.get(0) < report.get(1)) {
                        System.out.println("this report is ascending.");
                        isAscending = true;
                    } else {
                        isDescending = true;
                    }
                }

                if (i == report.size() - 3) {
                    totalSafe++;
                    System.out.println("added to totalSafe. Total: " + totalSafe);
                }
            }
        }
        return totalSafe;
    }
}
