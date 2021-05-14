package com.epam.cdp.m2.hw2.aggregator;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

public class Java7Aggregator implements Aggregator {

  @Override
  public int sum(List<Integer> numbers) {
    int sum = 0;

    for (Integer number : numbers) {
      sum += number;
    }
    return sum;
  }

  @Override
  public List<Pair<String, Long>> getMostFrequentWords(List<String> words, long limit) {
    List<Pair<String, Long>> frequency = new ArrayList<>();

    List<String> uniqueWords = new ArrayList<>(new HashSet<>(words));

    for (String word : uniqueWords) {
      frequency.add(new Pair<>(word, (long) Collections.frequency(words, word)));
    }

    frequency.sort(getFrequencyComparator());

    List<Pair<String, Long>> limitedFrequency = new ArrayList<>();

    for (Pair<String, Long> pair : frequency) {
      if (limitedFrequency.size() >= limit) {
        break;
      }
      limitedFrequency.add(pair);
    }

    return limitedFrequency;
  }

  @Override
  public List<String> getDuplicates(List<String> words, long limit) {
    List<String> wordsInUpperCase = new ArrayList<>();

    for (String word : words) {
      wordsInUpperCase.add(word.toUpperCase());
    }

    List<String> uniqueWords = new ArrayList<>(new HashSet<>(wordsInUpperCase));
    List<String> wordsWithDuplicates = new ArrayList<>();

    for (String uniqueWord : uniqueWords) {
      if (Collections.frequency(wordsInUpperCase, uniqueWord) > 1) {
        wordsWithDuplicates.add(uniqueWord);
      }
    }

    wordsWithDuplicates.sort(getDuplicateComparator());

    List<String> limitedDuplicates = new ArrayList<>();

    for (String wordsWithDuplicate : wordsWithDuplicates) {
      limitedDuplicates.add(wordsWithDuplicate);
      if (limitedDuplicates.size() >= limit) {
        break;
      }
    }

    return limitedDuplicates;
  }

  private Comparator<Pair<String, Long>> getFrequencyComparator() {
    return (p1, p2) -> {
      if (p1.getValue() == p2.getValue()) {
        return p1.getKey().compareTo(p2.getKey());
      } else if (p2.getValue() > p1.getValue()) {
        return 1;
      } else return -1;
    };
  }

  private Comparator<String> getDuplicateComparator() {
    return (s1, s2) -> {
      if (s1.length() == s2.length()) {
        return s1.compareTo(s2);
      } else if (s1.length() > s2.length()) {
        return 1;
      } else return -1;
    };
  }
}
