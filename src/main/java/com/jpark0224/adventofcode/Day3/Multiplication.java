package com.jpark0224.adventofcode.Day3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Multiplication {
    private List<String> instructions;
    private List<Integer> validIndices;
    private List<String> filteredInstructions;

    public Multiplication() {
        this.instructions = new ArrayList<>();
        this.filteredInstructions = new ArrayList<>();
    }
    
    public List<String> getInstructions() {
        return this.instructions;
    }

    public List<String> getFilteredInstructions() {
        return this.filteredInstructions;
    }

    public Stream<String> useRegexPart1(final String input) {
        // Compile regular expression
        String regex = "mul\\(\\d{1,3}+,\\d{1,3}+\\)";
        final Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(input)
                .results()                       // Stream<MatchResult>
                .map(MatchResult::group);          // Stream<String>
    }

    public void parseInputPart1() {
        String filePath = "src/input/Day3.txt";

        try (Stream<String> stream = Files.lines(Paths.get(filePath))) {
            instructions = stream.map(line -> useRegexPart1(line)
                    // Collect into a list
                    .collect(Collectors.toList()))
                    .flatMap(List::stream)
                    // Collect all lists into a nested list
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Stream<String> useRegexPart2(final String input) {
        // Compile regular expression
        String regexMul = "mul\\(\\d{1,3}+,\\d{1,3}+\\)|do\\(\\)|don't\\(\\)";
        final Pattern patternMul = Pattern.compile(regexMul);
        return patternMul.matcher(input)
                .results()                       // Stream<MatchResult>
                .map(MatchResult::group);          // Stream<String>
    }

    public void parseInputPart2() {
        String filePath = "src/input/Day3.txt";

        try (Stream<String> stream = Files.lines(Paths.get(filePath))) {
            instructions = stream.map(line -> useRegexPart2(line)
                            // Collect into a list
                            .collect(Collectors.toList()))
                    .flatMap(List::stream)
                    // Collect all lists into a nested list
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int multiplyAndAdd(List<String> list) {
        String regex = "\\d+";
        Pattern pattern = Pattern.compile(regex);
        int sum = 0;

        for (String instruction:list) {
            int result = 1;
            Matcher matcher = pattern.matcher(instruction);
            while (matcher.find()) {
             result *= Integer.parseInt(matcher.group());
            }
            sum += result;
        }

        return sum;
    }

    public List<List<Integer>> getInvalidRanges() {
        List<List<Integer>> invalidRanges = new ArrayList<>();
        int dontIndex = -1;

        for (int i = 0; i < instructions.size(); i++) {
            if (Objects.equals(instructions.get(i), "don't()")) {
                if (dontIndex == -1) {
                    dontIndex = i;
                }
            } else if ((Objects.equals(instructions.get(i), "do()") || i == instructions.size() - 1) && dontIndex > -1) {
                List<Integer> pair = new ArrayList<>();
                pair.add(dontIndex);
                pair.add(i);
                invalidRanges.add(pair);
                dontIndex = -1;
            }
        }

        return invalidRanges;
    }

    public List<String> filterInstructions() {
        int currentRangeIndex = 0;
        int rangeStart = -1;
        int rangeEnd = -1;
        List<List<Integer>> invalidRanges = getInvalidRanges();
        for (int i = 0; i < instructions.size(); i++) {
            if (currentRangeIndex < invalidRanges.size() && i > rangeEnd) {
                rangeStart = invalidRanges.get(currentRangeIndex).get(0);
                rangeEnd = invalidRanges.get(currentRangeIndex).get(1);
                currentRangeIndex++;
            }

            if (i >= rangeStart && i <= rangeEnd) {
                continue;
            }

            if (!Objects.equals(instructions.get(i), "do()")) {
                filteredInstructions.add(instructions.get(i));
            }
        }



        return filteredInstructions;
    }
}
