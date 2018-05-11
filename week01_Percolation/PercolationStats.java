import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

// import Percolation;

public class PercolationStats {
  private static int n;
  private static int trials;

  private double[] result;

  public PercolationStats(
      int n, int trials) { // perform trials independent experiments on an n-by-n grid
    if (n <= 0 || trials <= 0) throw new IllegalArgumentException("Arguments out of bound");

    this.result = new double[trials];

    for (int i = 0; i < trials; i++) {

      Percolation percolate = new Percolation(n);

      while (!percolate.percolates()) {
        //        System.out.printf("Generate Random Numbers\n");
        int row = StdRandom.uniform(1, n + 1);
        int col = StdRandom.uniform(1, n + 1);

        if (percolate.isOpen(row, col)) {
          // System.out.printf("[%d, %d]isOpen\n", row, col);
          continue;
        }
        percolate.open(row, col);
      }
      // System.out.printf("Percolated\n");
      this.result[i] = (double) percolate.numberOfOpenSites() / (n * n);
      // System.out.printf("result[%d] = %f\n", i, this.result[i]);
    }
  }

  public double mean() { // sample mean of percolation threshold
    return StdStats.mean(this.result);
  }

  public double stddev() { // sample standard deviation of percolation threshold
    return StdStats.stddev(this.result);
  }

  private double confidence() {
    return 1.96 * stddev() / Math.sqrt(this.result.length);
  }

  public double confidenceLo() { // low  endpoint of 95% confidence interval
    return mean() - confidence();
  }

  public double confidenceHi() { // high endpoint of 95% confidence interval
    return mean() + confidence();
  }

  public static void main(String[] args) { // test client (described below)
    n = Integer.parseInt(args[0]);
    trials = Integer.parseInt(args[1]);

    PercolationStats pls = new PercolationStats(n, trials);
    // mean                    = 0.5929934999999997
    // stddev                  = 0.00876990421552567
    // 95% confidence interval = [0.5912745987737567, 0.5947124012262428]
    System.out.printf("mean %f\n", pls.mean());
    System.out.printf("stddev %f\n", pls.stddev());
    System.out.printf(
        "95%% confidence interval [%f, %f]\n", pls.confidenceLo(), pls.confidenceHi());
  }
}
