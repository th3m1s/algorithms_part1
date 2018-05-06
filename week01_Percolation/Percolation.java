import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
  int n;
  boolean[] sites;
  WeightedQuickUnionUF qu_sites;

  public Percolation(int n) {// create n-by-n grid, with all sites blocked
    if (n <= 0)
      throw new IllegalArgumentException("n is less than or equal to 0");//error hanndling

    this.n        = n;
    this.qu_sites = new WeightedQuickUnionUF(n * n + 2);//add two dummies(virtual top and bottom)
    this.sites    = new boolean[n * n];

    for (int i = 0; i < n * n; i++) {
      this.sites[i] = false;//False:blocked,True:open
    }

    //connect virtual top site
    for (int i = 0; i < n; i++) {
      this.qu_sites.union(n * n, i);
    }
    //connect virtual bottom site
    for (int i = (0 + n * n - n - 1); i < n; i++) {
      this.qu_sites.union(i, n * n + 1);
    }
  }

  private void validateIndex(int row, int col) {
    if (!isValid(row, col)) {
      throw new IndexOutOfBoundsException(String.format("N:%d row:%d col:%d", this.n, row, col));
    }
  }

  private int xy2flat(int row, int col) {
    return (row - 1) * this.n + col - 1;
  }

  private boolean isValid(int row, int col) {
    return 1 <= row && row <= this.n && 1 <= col && col <= this.n && xy2flat(row, col) < this.n * this.n;//n*n and n*n+1 are dummies
  }

  public void open(int row, int col) {// open site (row, col) if it is not open already
    if (isValid(row, col) && !isOpen(row, col)) {
      System.out.printf("Open site[%d, %d]\n", row, col);
      this.sites[xy2flat(row, col)] = true;

      if (row == 0) {
        System.out.printf("Connect Dummy Top\n");
        this.qu_sites.union(xy2flat(row, col), n * n);
      } else if (row == n) {
        System.out.printf("Connect Dummy Bottom\n");
        this.qu_sites.union(xy2flat(row, col), n * n + 1);
      }

      open_cross(row,col);
    }
  }

  public void open_cross(int row, int col) {
    if (isValid(row - 1, col) && isOpen(row - 1, col)) {
      System.out.printf("Connect up site\n");
      this.qu_sites.union(xy2flat(row, col), xy2flat(row - 1, col));
    }

    if (isValid(row + 1, col) && isOpen(row + 1, col)) {
      System.out.printf("Connect down site\n");
      this.qu_sites.union(xy2flat(row, col), xy2flat(row + 1, col));
    }

    if (isValid(row + 1, col - 1) && isOpen(row, col - 1)) {
      System.out.printf("Connect left site\n");
      this.qu_sites.union(xy2flat(row, col), xy2flat(row, col - 1));
    }

    if (isValid(row, col + 1) && isOpen(row, col + 1)) {
      System.out.printf("Connect right site\n");
      this.qu_sites.union(xy2flat(row, col), xy2flat(row, col + 1));
    }
  }

  public boolean isOpen(int row, int col) {// is site (row, col) open?
    try {
      validateIndex(row, col);
      return this.sites[xy2flat(row, col)];
    } catch (IndexOutOfBoundsException e) {
      System.out.println("Error " + e.getMessage());
      e.printStackTrace();
      return false;
    }
  }

  public boolean isFull(int row, int col) {// is site (row, col) full?
    try {
      validateIndex(row, col);
      return this.qu_sites.connected(0, xy2flat(row, col));
    } catch (IndexOutOfBoundsException e) {
      System.out.println("Error " + e.getMessage());
      e.printStackTrace();
      return false;
    }
  }

  public int numberOfOpenSites() {// number of open sites
    int numberOfOpen = 0;
    System.out.printf("Counting");
    for (int i = 1; i <= n; i++) {
      for (int j = 1; j <= n; j++) {
        if (isOpen(i, j)) {
          numberOfOpen++;
          System.out.printf(".");
        }
      }
    }
    System.out.printf("\n");
    return numberOfOpen;
  }

  public boolean percolates() {// does the system percolate?
    return this.qu_sites.connected(n * n, n * n + 1);
  }

  public static void main(String[] args) {// test client (optional)
    test1();
    test2();
  }

  private static void test1() {
    System.out.printf("Test1\n");
    final Percolation p = new Percolation(3);

    System.out.println("p.isOpen(1, 2) = " + p.isOpen(1, 2));
    p.open(1, 2);
    System.out.println("p.isOpen(1, 2) = " + p.isOpen(1, 2));

    System.out.println("p.isOpen(2,2) = " + p.isOpen(2, 2));
    p.open(2, 2);
    System.out.println("p.isOpen(2,2) = " + p.isOpen(2, 2));
    System.out.println("p.isFull(2, 2) = " + p.isFull(2, 2));

    System.out.println("p.isOpen(3, 1) = " + p.isOpen(3, 1));
    p.open(3, 1);
    System.out.println("p.isFull(3, 1) = " + p.isFull(3, 1));
    p.isFull(3, 1);

    System.out.println("p.percolates() = " + p.percolates());
    System.out.println("p.numberOfOpenSites() = " + p.numberOfOpenSites());
  }


  private static void test2() {
    System.out.printf("Test2\n");
    final Percolation p = new Percolation(3);

    System.out.println("p.isOpen(1, 2) = " + p.isOpen(1, 2));
    p.open(1, 2);
    System.out.println("p.isOpen(1, 2) = " + p.isOpen(1, 2));

    System.out.println("p.isOpen(2,2) = " + p.isOpen(2, 2));
    p.open(2, 2);
    System.out.println("p.isOpen(2,2) = " + p.isOpen(2, 2));
    System.out.println("p.isFull(2, 2) = " + p.isFull(2, 2));

    System.out.println("p.isOpen(3, 2) = " + p.isOpen(3, 2));
    p.open(3, 2);
    System.out.println("p.isOpen(3, 2) = " + p.isOpen(3, 2));
    p.isFull(3, 2);

    System.out.println("p.percolates() = " + p.percolates());
    System.out.println("p.numberOfOpenSites() = " + p.numberOfOpenSites());
  }
}
