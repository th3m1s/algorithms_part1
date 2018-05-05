import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

import Percolation;

public class PercolationStats {
  int n;
  int trials;

  double[] result;
  double mean;
  double stddev;
  double low;
  double high;

  public PercolationStats(int n, int trials) {// perform trials independent experiments on an n-by-n grid
    Percolation percolate = new Percolation(n);

    double[] result = new double[trials];

    StdRandom random = new StdRandom(n);

    for (int i = 0; i < trials; i++) {
      while (!percolate.percolates()) {
        percolate.open(random, random);
        result[i] = percolate.numberOfOpenSites() / (n * n);
      }
    }
  }

  public double mean() {// sample mean of percolation threshold
    mean = StdStats.sum(result) / trials;
    return mean;
  }

  public double stddev() {// sample standard deviation of percolation threshold
    stddev = StdStats.stddev(result);
  }

  public double confidenceLo() {// low  endpoint of 95% confidence interval
    low = mean - 1.96 * stddev / sqrt(trials);
    return low;
  }

  public double confidenceHi() {// high endpoint of 95% confidence interval
    high = mean = -1.96 * stddev / sqrt(trials);
    return high;
  }

  public static void main(String[] args) {// test client (described below)
    n      = args[0];
    trials = args[1];

    PercolationStats(n, trials);
    // mean                    = 0.5929934999999997
    // stddev                  = 0.00876990421552567
    // 95% confidence interval = [0.5912745987737567, 0.5947124012262428]
    StdOut.printf("mean %10.3f\n", mean);
    StdOut.printf("stddev %10.3f\n", stddev);
    StdOut.printf("95% confidence interval = [%10.3f, %10.3f]\n", low, high);
  }
}
