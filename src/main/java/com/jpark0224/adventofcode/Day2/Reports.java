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
}
