import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

//import Percolation;

public class PercolationStats {
  static int n;
  static int trials;

  double[] result;
  double mean;
  double stddev;
  double low;
  double high;

  public PercolationStats(int n, int trials) {// perform trials independent experiments on an n-by-n grid
    if (n <= 0 || trials <= 0)
      throw new IllegalArgumentException("Arguments out of bound");

    Percolation percolate = new Percolation(n);
    System.out.printf("making new instance\n");

    this.result = new double[trials];

    for (int i = 0; i < trials; i++) {
      while (!percolate.percolates()) {
        //        System.out.printf("Generate Random Numbers\n");
        int row    = StdRandom.uniform(1, n + 1);
        int column = StdRandom.uniform(1, n + 1);
//        if (percolate.isOpen(row, column)) {
//          System.out.printf("isOpen \n");
//          continue;
//        }
        percolate.open(row, column);
      }
      System.out.printf("Percolated\n");
      this.result[i] = percolate.numberOfOpenSites() / (n * n);
      System.out.printf("result[%d] = %f\n", i, result[i]);
    }
  }

  public double mean() {// sample mean of percolation threshold
    mean = StdStats.mean(result);
    return mean;
  }

  public double stddev() {// sample standard deviation of percolation threshold
    stddev = StdStats.stddev(result);
    return stddev;
  }

  public double confidenceLo() {// low  endpoint of 95% confidence interval
    low = mean - 1.96 * stddev / Math.sqrt(trials);
    return low;
  }

  public double confidenceHi() {// high endpoint of 95% confidence interval
    high = mean = -1.96 * stddev / Math.sqrt(trials);
    return high;
  }

  public static void main(String[] args) {// test client (described below)
    n      = Integer.parseInt(args[0]);
    trials = Integer.parseInt(args[1]);

    PercolationStats pls = new PercolationStats(n, trials);
    // mean                    = 0.5929934999999997
    // stddev                  = 0.00876990421552567
    // 95% confidence interval = [0.5912745987737567, 0.5947124012262428]
    System.out.printf("mean %10.3f\n", pls.mean());
    System.out.printf("stddev %10.3f\n", pls.stddev());
    System.out.printf("95% confidence interval = [%10.3f, %10.3f]\n", pls.confidenceLo(), pls.confidenceHi());
  }
}
