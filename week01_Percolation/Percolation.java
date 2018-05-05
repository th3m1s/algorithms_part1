import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
  int n;
  boolean[] sites;
  WeightedQuickUnionUF qu_sites;

  public Percolation(int n) {// create n-by-n grid, with all sites blocked

    this.sites = new boolean[n * n];

    for (int i = 0; i < n * n; i++) {
      sites[i] = False;//False:blocked,True:open
    }

    qu_sites = new WeightedQuickUnionUF(n * n + 2);

    //connect virtual top site
    for (int i = 1; i <= n; i++) {
      qu_sites.union(0, i);
    }
    //connect virtual bottom site
    for (int i = 1; i <= n; i++) {
      qu_sites.union(n * (n - 1) + 2 - 1 + i, n * n + 2 - 1);
    }
  }

  public void open(int row, int col) {// open site (row, col) if it is not open already
    if (!isOpen(row, col)) {
      sites[row + col * n] = 1;

      if (isOpen(row - 1, col)) {
        qu_sites.union(1 + row + col * n, 1 + row - 1 + col * n);
      }
      if (isOpen(row + 1, col)) {
        qu_sites.union(1 + row + col * n, 1 + row + 1 + col * n);
      }
      if (isOpen(row, col - 1)) {
        qu_sites.union(1 + row + col * n, 1 + row + (col - 1) * n);
      }
      if (isOpen(row, col + 1)) {
        qu_sites.union(1 + row + col * n, 1 + row + (col + 1) * n);
      }
    }
  }

  public boolean isOpen(int row, int col) {// is site (row, col) open?
    if (sites[row + col * n] == 1) {
      return true;
    } else {
      return false;
    }
  }

  public boolean isFull(int row, int col) {// is site (row, col) full?
    return qu_sites.connected(0, 1 + row + col * n);
  }

  public int numberOfOpenSites() {// number of open sites
    int numberOfOpen = 0;
    for (int i = 0; i < n * n; i++) {
      if (isOpen(i % n, i / n)) {//0:blocked,1:open
        numberOfOpen++;
      }
    }
    return numberOfOpen;
  }

  public boolean percolates() {// does the system percolate?
    return qu_sites.connected(0, n * n + 2);
  }

  public static void main(String[] args) {// test client (optional)
    test2();
  }

  private static void test2() {
    final Percolation p = new Percolation(3);
    System.out.println("p.isOpen(1, 2) = " + p.isOpen(1, 2));
    p.open(1, 2);
    System.out.println("p.isOpen(1, 2) = " + p.isOpen(1, 2));

    System.out.println("p.isOpen(2,2) = " + p.isOpen(2, 2));
    p.open(2, 2);
    System.out.println("p.isOpen(2,2) = " + p.isOpen(2, 2));
    System.out.println("p.isFull(2, 2) = " + p.isFull(2, 2));

    System.out.println("p.isOpen(3, 1) = " + p.isOpen(3, 2));
    p.open(3, 2);
    System.out.println("p.isOpen(3, 1) = " + p.isOpen(3, 2));
    p.isFull(3, 2);

    System.out.println("p.percolates() = " + p.percolates());
  }
}
