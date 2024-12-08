package com.jpark0224.adventofcode.Day3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Multiplication {
    private List<String> instructions;

    public Multiplication() {
        this.instructions = new ArrayList<>();
    }
    
    public List<String> getInstructions() {
        return this.instructions;
    }

    public Stream<String> useRegex(final String input) {
        // Compile regular expression
        String regex = "mul\\(\\d{1,3}+,\\d{1,3}+\\)";
        final Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(input)
                .results()                       // Stream<MatchResult>
                .map(MatchResult::group);          // Stream<String>
    }

    public void parseInput() {
        String filePath = "src/input/Day3.txt";

        try (Stream<String> stream = Files.lines(Paths.get(filePath))) {
            instructions = stream.map(line -> useRegex(line)
                    // Collect into a list
                    .collect(Collectors.toList()))
                    .flatMap(List::stream)
                    // Collect all lists into a nested list
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Part 1
    public int multiplyAndAdd() {
        String regex = "\\d+";
        Pattern pattern = Pattern.compile(regex);
        int sum = 0;

        for (String instructions:instructions) {
            int result = 1;
            Matcher matcher = pattern.matcher(instructions);
            while (matcher.find()) {
             result *= Integer.parseInt(matcher.group());
            }
            sum += result;
        }

        return sum;
    }
}
