package com.epam.cdp.m2.hw2.aggregator;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Java8Aggregator implements Aggregator {

  @Override
  public int sum(List<Integer> numbers) {
    return numbers.stream().reduce(0, Integer::sum);
  }

  @Override
  public List<Pair<String, Long>> getMostFrequentWords(List<String> words, long limit) {
    List<Pair<String, Long>> frequency = new ArrayList<>();

    List<String> uniqueWords = words.stream().distinct().collect(Collectors.toList());

    uniqueWords.forEach(
        word -> frequency.add(new Pair<>(word, (long) Collections.frequency(words, word))));
    return sortFrequencyArray(frequency, limit);
  }

  private List<Pair<String, Long>> sortFrequencyArray(
      List<Pair<String, Long>> frequency, long limit) {
    return frequency.stream()
        .sorted(Comparator.comparingLong(Pair<String, Long>::getValue).reversed())
        .limit(limit)
        .collect(Collectors.toList());
  }

  @Override
  public List<String> getDuplicates(List<String> words, long limit) {
    return words.stream()
        .filter(w -> hasDuplicate(words, w))
        .map(String::toUpperCase)
        .distinct()
        .sorted(Comparator.comparingInt(String::length))
        .limit(limit)
        .collect(Collectors.toList());
  }

  private boolean hasDuplicate(List<String> words, String word) {
    long count = words.stream().filter(w -> w.equalsIgnoreCase(word)).count();
    return count > 1;
  }
}
