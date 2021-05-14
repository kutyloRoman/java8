package com.epam.cdp.m2.hw2.aggregator;

import java.util.List;
import java.util.concurrent.RecursiveTask;

public class SumTask extends RecursiveTask<Integer> {
  private static final int LIST_THRESHOLD = 5;

  private List<Integer> data;

  public SumTask(List<Integer> data) {
    this.data = data;
  }

  @Override
  protected Integer compute() {

    if (data.size() <= LIST_THRESHOLD) {
      return computeSum();
    } else {
      int middle = data.size() / 2;
      SumTask firstSubtask = new SumTask(data.subList(0, middle));
      SumTask secondSubtask = new SumTask(data.subList(middle, data.size()));

      firstSubtask.fork();

      return secondSubtask.compute() + firstSubtask.join();
    }
  }

  private int computeSum() {
    int sum = 0;
    for (Integer l : data) {
      sum += l;
    }
    return sum;
  }
}
