package main;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

public class Main {

  private static final HashMap<Character, Integer> priorities = new HashMap<>();

  public static void main(String[] args) {

    setPriorities();

    File myObj = new File("src/input/input.txt");

    try (Scanner myReader = new Scanner(myObj)) {
      ArrayList<String> lines = new ArrayList<>();

      while (myReader.hasNextLine()) {
        String data = myReader.nextLine();
        lines.add(data);
      }

      int sumPartOne = sumPartOne(lines);
      int sumPartTwo = sumPartTwo(lines);

      System.out.println("Sum of priorities (part one): " + sumPartOne);
      System.out.println("Sum of priorities (part two): " + sumPartTwo);

    } catch (Exception e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }

  }

  private static int sumPartOne(ArrayList<String> lines) throws IOException {
    int sumOfPriorities = 0;

    for (String line : lines) {
      char repeatedItem = getRepeat(line);
      sumOfPriorities += priorities.get(repeatedItem);
    }
    return sumOfPriorities;

  }

  private static int sumPartTwo(ArrayList<String> lines) throws IOException {
    int sumOfPriorities = 0;

    ArrayList<ArrayList<String>> groups = getGroups(lines);

    for (ArrayList<String> group : groups) {
      sumOfPriorities += getGroupPriority(group);
    }

    return sumOfPriorities;
  }

  private static char getRepeat(String line) throws IOException {
    int halfwayPoint = line.length() / 2;
    HashSet<Character> usedChars = new HashSet<>();
    for (int i = 0; i < line.length(); i++) {
      char currChar = line.charAt(i);
      if (i < halfwayPoint) {
        usedChars.add(currChar);
      } else if (usedChars.contains(currChar)) {
        return currChar;
      }
    }
    throw new IOException("Bad line: " + line);
  }

  private static void setPriorities() {
    int priority = 1;
    for (char alphabet = 'a'; alphabet <= 'z'; alphabet++) {
      priorities.put(alphabet, priority);
      priority++;
    }
    priority = 27;
    for (char alphabet = 'A'; alphabet <= 'Z'; alphabet++) {
      priorities.put(alphabet, priority);
      priority++;
    }
  }

  private static ArrayList<ArrayList<String>> getGroups(ArrayList<String> lines) {
    ArrayList<ArrayList<String>> groups = new ArrayList<>();

    for (int i = 0; i < lines.size(); i += 3) {
      ArrayList<String> group = new ArrayList<>();
      group.add(lines.get(i));
      group.add(lines.get(i + 1));
      group.add(lines.get(i + 2));
      groups.add(group);
    }
    return groups;
  }

  private static int getGroupPriority(ArrayList<String> group) throws IOException {

    for (char alphabet = 'a'; alphabet <= 'z'; alphabet++) {
      String item = Character.toString(alphabet);
      if (groupShares(group, item)) {
        return priorities.get(alphabet);
      }
    }

    for (char alphabet = 'A'; alphabet <= 'Z'; alphabet++) {
      String item = Character.toString(alphabet);
      if (groupShares(group, item)) {
        return priorities.get(alphabet);
      }
    }

    throw new IOException("No shared item");
  }

  private static boolean groupShares(ArrayList<String> group, String item) {
    String first = group.get(0);
    String second = group.get(1);
    String third = group.get(2);

    return first.contains(item) && second.contains(item) && third.contains(item);

  }

}
