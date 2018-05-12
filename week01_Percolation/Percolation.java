import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
  private final int n;

  // positional pointers to virtual top and bottom of UF data structure
  private final int vTop, vBottom;

  private WeightedQuickUnionUF quSites;
  private boolean[] sites;

  public Percolation(int n) { // create n-by-n grid, with all sites blocked
    if (n <= 0)
      throw new IllegalArgumentException("n is less than or equal to 0"); // error hanndling

    this.n = n;
    this.sites = new boolean[n * n];
    this.quSites = new WeightedQuickUnionUF(n * n + 2); // add two dummies(virtual top and bottom)

    this.vTop = n * n;
    this.vBottom = n * n + 1;

    for (int i = 0; i < n * n; i++) {
      this.sites[i] = false; // False:blocked,True:open
    }

    for (int i = 0; i < n; i++) {
      // connect virtual top site
      this.quSites.union(i, vTop);
      // connect virtual bottom site
      this.quSites.union(n== 1 ? 0 : xy2flat(this.n, i), vBottom);
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

  private boolean isValid(int row, int col) { // n*n and n*n+1 are dummies
    return 1 <= row
        && row <= this.n
        && 1 <= col
        && col <= this.n
        && 0 <= xy2flat(row, col)
        && xy2flat(row, col) < this.n * this.n;
  }

  public void open(int row, int col) { // open site (row, col) if it is not open already
    if (isValid(row, col) && !isOpen(row, col)) {
      // System.out.printf("Open site[%d, %d]\n", row, col);
      this.sites[xy2flat(row, col)] = true;
      openCross(row, col);
    }
  }

  private void openCross(int row, int col) {
    if (1 < row && isOpen(row - 1, col)) {
      // System.out.printf("Connect up site\n");
      this.quSites.union(xy2flat(row, col), xy2flat(row - 1, col));
    }

    if (row < this.n && isOpen(row + 1, col)) {
      // System.out.printf("Connect down site\n");
      this.quSites.union(xy2flat(row, col), xy2flat(row + 1, col));
    }

    if (1 < col && isOpen(row, col - 1)) {
      // System.out.printf("Connect left site\n");
      this.quSites.union(xy2flat(row, col), xy2flat(row, col - 1));
    }

    if (col < this.n && isOpen(row, col + 1)) {
      // System.out.printf("Connect right site\n");
      this.quSites.union(xy2flat(row, col), xy2flat(row, col + 1));
    }
  }

  public boolean isOpen(int row, int col) { // is site (row, col) open?
    return this.sites[xy2flat(row, col)];
  }

  public boolean isFull(int row, int col) { // is site (row, col) full?
    validateIndex(row, col);
    return isOpen(row, col) && this.quSites.connected(vTop, xy2flat(row, col));
  }

  public int numberOfOpenSites() { // number of open sites
    int numberOfOpen = 0;
    // System.out.printf("Counting");
    for (int i = 0; i < n * n; i++) {
      if (this.sites[i]) {
        numberOfOpen++;
        // System.out.printf(".");
      }
    }
    // System.out.printf("\n");
    return numberOfOpen;
  }

  public boolean percolates() { // does the system percolate?
      return this.quSites.connected(vTop, vBottom);
  }

  public static void main(String[] args) { // test client (optional)
    test1();
    test2();
    test3();
    test4();
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

    System.out.println("p.isOpen(1, 2) ? " + p.isOpen(1, 2));
    p.open(1, 2);
    System.out.println("p.isOpen(1, 2) = " + p.isOpen(1, 2));

    System.out.println("p.isOpen(2,2) ? " + p.isOpen(2, 2));
    p.open(2, 2);
    System.out.println("p.isOpen(2,2) = " + p.isOpen(2, 2));
    System.out.println("p.isFull(2, 2) = " + p.isFull(2, 2));

    System.out.println("p.isOpen(3, 2) ? " + p.isOpen(3, 2));
    p.open(3, 2);
    System.out.println("p.isOpen(3, 2) = " + p.isOpen(3, 2));
    p.isFull(3, 2);

    System.out.println("p.percolates() = " + p.percolates());
    System.out.println("p.numberOfOpenSites() = " + p.numberOfOpenSites());
  }

  private static void test3() {
    System.out.printf("Test3\n");
    final Percolation p = new Percolation(2);

    System.out.println("p.isOpen(1, 2) ? " + p.isOpen(1, 2));
    p.open(1, 2);
    System.out.println("p.isOpen(1, 2) = " + p.isOpen(1, 2));

    System.out.println("p.isOpen(2,2) ? " + p.isOpen(2, 2));
    p.open(2, 2);
    System.out.println("p.isOpen(2,2) = " + p.isOpen(2, 2));
    System.out.println("p.isFull(2, 2) = " + p.isFull(2, 2));

    System.out.println("p.isOpen(2, 1) ? " + p.isOpen(2, 1));
    p.open(2, 1);
    System.out.println("p.isFull(2, 1) = " + p.isFull(2, 1));

    System.out.println("p.percolates() = " + p.percolates());
    System.out.println("p.numberOfOpenSites() = " + p.numberOfOpenSites());
  }

  private static void test4() {
    System.out.printf("Test4\n");
    final Percolation p = new Percolation(1);

    System.out.println("p.isOpen(1, 1) ? " + p.isOpen(1, 1));
    p.open(1, 1);
    System.out.println("p.isOpen(1, 1) = " + p.isOpen(1, 1));

    System.out.println("p.isFull(1, 1) ? " + p.isFull(1, 1));

    System.out.println("p.percolates() = " + p.percolates());
    System.out.println("p.numberOfOpenSites() = " + p.numberOfOpenSites());
  }
}
