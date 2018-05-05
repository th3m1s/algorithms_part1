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
    for (int i = (0 + n * n - n-1); i < n; i++) {
      this.qu_sites.union(i, n * n + 1);
    }
  }

  private void validateIndex(int x, int y) {
    if (!isValid(x, y)) {
      throw new IndexOutOfBoundsException(String.format("N:%d x:%d y:%d", this.n, x, y));
    }
  }

  private boolean isValid(int x, int y) {
    return x >= 1 && x <= this.n && y >= 1 && y <= this.n && x+y*this.n-1 < n*n;//n*n and n*n+2-1 are dummies
  }

  public void open(int row, int col) {// open site (row, col) if it is not open already
    if (isValid(row, col) && !isOpen(row, col)) {
      System.out.printf("Open site[%d, %d]\n", row, col);
      this.sites[row + col * n - 1] = true;

      if (col == 0) {
        System.out.printf("Open up site\n");
        System.out.printf("Connect Dummy Top\n");
        this.qu_sites.union(row + col * n - 1, n * n);
      } else if (col == n) {
        System.out.printf("Open down site\n");
        System.out.printf("Connect Dummy Bottom\n");
        this.qu_sites.union(row + col * n - 1, n * n + 1);
      } else {
        if (isValid(row - 1, col) && isOpen(row - 1, col)) {
          System.out.printf("Open down site\n");
          this.qu_sites.union(row + col * n - 1, row - 1 + col * n - 1);
        }
        if (isValid(row + 1, col) && isOpen(row + 1, col)) {
          System.out.printf("Open up site\n");
          this.qu_sites.union(row + col * n - 1, row + 1 + col * n - 1);
        }
      }
      if (isValid(row, col - 1) && isOpen(row, col - 1)) {
        System.out.printf("Open left site\n");
        this.qu_sites.union(row + col * n - 1, row + (col - 1) * n - 1);
      }
      if (isValid(row, col + 1) && isOpen(row, col + 1)) {
        System.out.printf("Open right site\n");
        this.qu_sites.union(row + col * n - 1, row + (col + 1) * n - 1);
      }
    }
  }

  public boolean isOpen(int row, int col) {// is site (row, col) open?
    if (isValid(row, col)) {
      try {
        return this.sites[row + col * n - 1];
      } catch (IndexOutOfBoundsException e) {
        System.out.println("Error " + e.getMessage());
        //      e.printStackTrace();
      }
    }
    return false;
  }

  public boolean isFull(int row, int col) {// is site (row, col) full?
    try {
      return this.qu_sites.connected(0, 1 + row + col * n);
    } catch (IndexOutOfBoundsException e) {
      System.out.println("Error " + e.getMessage());
      //      e.printStackTrace();
      return false;
    }
  }

  public int numberOfOpenSites() {// number of open sites
    int numberOfOpen = 0;
    for (int i = 0; i < n; i++) {
      for(int j=0;j<n;j++){
        if(isOpen(i,j)){
          numberOfOpen++;
          System.out.printf("Count ....");
        }
      }
      
    }
    return numberOfOpen;
  }

  public boolean percolates() {// does the system percolate?
    return this.qu_sites.connected(n*n, n * n + 1);
  }

  // public static void main(String[] args) {// test client (optional)
  //   test2();
  // }

  // private static void test2() {
  //   final Percolation p = new Percolation(3);
  //   System.out.println("p.isOpen(1, 2) = " + p.isOpen(1, 2));
  //   p.open(1, 2);
  //   System.out.println("p.isOpen(1, 2) = " + p.isOpen(1, 2));

  //   System.out.println("p.isOpen(2,2) = " + p.isOpen(2, 2));
  //   p.open(2, 2);
  //   System.out.println("p.isOpen(2,2) = " + p.isOpen(2, 2));
  //   System.out.println("p.isFull(2, 2) = " + p.isFull(2, 2));

  //   System.out.println("p.isOpen(3, 1) = " + p.isOpen(3, 2));
  //   p.open(3, 2);
  //   System.out.println("p.isOpen(3, 1) = " + p.isOpen(3, 2));
  //   p.isFull(3, 2);

  //   System.out.println("p.percolates() = " + p.percolates());
  // }
}
