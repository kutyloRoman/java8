package com.epam.cdp.m2.hw2.aggregator;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Java8ParallelAggregator implements Aggregator {

  @Override
  public int sum(List<Integer> numbers) {
    return numbers.parallelStream().reduce(0, Integer::sum);
  }

  @Override
  public List<Pair<String, Long>> getMostFrequentWords(List<String> words, long limit) {
    List<Pair<String, Long>> frequency = new ArrayList<>();

    words.parallelStream()
        .forEach(w -> frequency.add(new Pair<>(w, (long) Collections.frequency(words, w))));

    return sortFrequencyArray(frequency, limit);
  }

  private List<Pair<String, Long>> sortFrequencyArray(
      List<Pair<String, Long>> frequency, long limit) {

    return frequency.parallelStream()
        .distinct()
        .sorted(getFrequencyComparator())
        .limit(limit)
        .collect(Collectors.toList());
  }

  private  Comparator<Pair<String, Long>> getFrequencyComparator(){
    return (p1, p2) -> {
      if (p1.getValue() == p2.getValue()) {
        return p1.getKey().compareTo(p2.getKey());
      } else if (p2.getValue() > p1.getValue()) {
        return 1;
      } else return -1;
    };
  }

  @Override
  public List<String> getDuplicates(List<String> words, long limit) {
    return words.parallelStream()
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
