package main;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {

  static final List<Pair> pairs = new ArrayList<>();

  static class Pair {
    Range first;
    Range second;
    public Pair(Range first, Range second) {
      this.first = first;
      this.second = second;
    }

    public boolean contains() {
      boolean firstContains = first.contains(second);
      boolean secondContains = second.contains(first);
      return firstContains || secondContains;
    }

    public boolean overlaps() {
      boolean firstOverlaps = first.overlaps(second);
      boolean secondOverlaps = second.overlaps(first);
      return firstOverlaps || secondOverlaps;
    }

  }

  static class Range {
    protected int start;
    protected int end;
    public Range(int start, int end) {
      this.start = start;
      this.end = end;
    }
    public boolean contains(Range other) {
      return start <= other.start && end >= other.end;
    }
    public boolean overlaps(Range other) {
      return this.contains(other) || other.isInRange(start) || other.isInRange(end);
    }
    public boolean isInRange(int number) {
      return start <= number && end >= number;
    }
  }


  public static void main(String[] args) {

    File myObj = new File("src/input/input.txt");

    try (Scanner myReader = new Scanner(myObj)) {
      ArrayList<String> lines = new ArrayList<>();

      while (myReader.hasNextLine()) {
        String data = myReader.nextLine();
        lines.add(data);
      }

      addPairs(lines);
      int containedPairs = getContainedPairs();

      System.out.println("Part one: " + containedPairs);

      int overlappingPairs = getOverlappingPairs();

      System.out.println("Part two: " + overlappingPairs);

    } catch (Exception e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }

  }

  private static void addPairs(ArrayList<String> lines) {

    ArrayList<List<String>> pairsOfStrings = new ArrayList<>();

    for (String line : lines) {
      List<String> pair = Arrays.asList(line.split(","));
      pairsOfStrings.add(pair);
    }

    for (List<String> pair : pairsOfStrings) {
      Range first = getRange(pair.get(0));
      Range second = getRange(pair.get(1));
      pairs.add(new Pair(first, second));
    }

  }

  private static Range getRange(String s) {
    List<String> numbers = Arrays.asList(s.split("-"));
    int first = Integer.parseInt(numbers.get(0));
    int second = Integer.parseInt(numbers.get(1));
    return new Range(first, second);
  }

  private static int getContainedPairs() {
    int overlaps = 0;
    for (Pair pair : pairs) {
      if (pair.contains()) {
        overlaps++;
      }
    }
    return overlaps;
  }

  private static int getOverlappingPairs() {
    int overlaps = 0;
    for (Pair pair : pairs) {
      if (pair.overlaps()) {
        overlaps++;
      }
    }
    return overlaps;
  }

}
