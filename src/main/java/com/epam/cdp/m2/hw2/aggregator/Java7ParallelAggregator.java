package com.epam.cdp.m2.hw2.aggregator;

import javafx.util.Pair;

import java.util.List;
import java.util.concurrent.ForkJoinPool;

public class Java7ParallelAggregator implements Aggregator {

  @Override
  public int sum(List<Integer> numbers) {

    ForkJoinPool pool = new ForkJoinPool();
    SumTask sumTask = new SumTask(numbers);
    return pool.invoke(sumTask);
  }

  @Override
  public List<Pair<String, Long>> getMostFrequentWords(List<String> words, long limit) {
    throw new UnsupportedOperationException();
  }

  @Override
  public List<String> getDuplicates(List<String> words, long limit) {
    throw new UnsupportedOperationException();
  }
}
