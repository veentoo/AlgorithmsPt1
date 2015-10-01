package edu.princeton.cs.algs4.alna.percolation;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private int N;
    private int NN;
    private boolean isOpen, isTop, isBottom;

    /**
     * 0b100 - open
     * 0b001 - connected to top<br>
     * 0b010 - connected to bottom
     */
    private byte[] status;

    private WeightedQuickUnionUF uf;

    /**
     * Create N-by-N grid, with all sites blocked
     *
     * @param N
     */
    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException();
        }
        this.N = N;
        this.NN = N*N;
        this.status = new byte[NN];
        this.uf = new WeightedQuickUnionUF(NN);
    }

    /**
     * Open site (row i, column j) if it is not open already
     *
     * @param i
     * @param j
     */
    public void open(int i, int j) {
        validate(i, j);
        int pos = get1DPosition(i, j);
        System.out.println("pos is " + pos);
        byte posStatus = getStatus(pos);
        status[pos] = posStatus;
        int upperPos = getNeighbourPosition(i, j, -1, 0);
        if (upperPos > -1 && status[upperPos] != 0b000) {
            makeUnion(pos, upperPos);
        }
        int rightPos = getNeighbourPosition(i, j, 0, 1);
        if (rightPos > -1 && status[rightPos] != 0b000) {
            makeUnion(pos, rightPos);
        }
        int bottomPos = getNeighbourPosition(i, j, 1, 0);
        if (bottomPos > -1 && status[bottomPos] != 0b000) {
            makeUnion(pos, bottomPos);
        }
        int leftPos = getNeighbourPosition(i, j, 0, -1);
        if (leftPos > -1 && status[leftPos] != 0b000) {
            makeUnion(pos, leftPos);
        }
    }

    /**
     * Is site (row i, column j) open?
     *
     * @param i
     * @param j
     * @return
     */
    public boolean isOpen(int i, int j) {
        validate(i, j);
        int pos = get1DPosition(i, j);
        if (status[pos] > 0b000)
            return true;
        else
            return false;
    }

    /**
     * Is site (row i, column j) full?
     *
     * @param i
     * @param j
     * @return
     */
    public boolean isFull(int i, int j) {
        validate(i, j);
        return false;
    }

    /**
     * Does the system percolate?
     *
     * @return
     */
    public boolean percolates() {
        return false;
    }

    private void validate(int i, int j) {
        if (i < 1 || i > N || j < 1 || j > N) {
            throw new IndexOutOfBoundsException();
        }
    }

    private boolean isEdge(int pos) {
        if (pos < 0)
            throw new IndexOutOfBoundsException();

        if (pos < N || pos > NN - N) {
            return true;
        } else {
            return false;
        }
    }

    private int toInnerIdx(int k) {
        return k - 1;
    }

    private int getNeighbourPosition(int i, int j, int di, int dj) {
        if ((toInnerIdx(i)+di < 0) || (toInnerIdx(j)+dj < 0) || i + di > N || j + dj > N) {
            System.out.println("no neighbour ("+di+","+dj+") for " + intArrayToString(i, j));
            return -1;
        }
        int dPosition = get1DPosition(i + di, j + dj);
        System.out.println("neighbour (" + di + "," + dj + ") for "
                + intArrayToString(i, j) + " is " + dPosition);
        return dPosition;
    }

    /**
     * 0..N-1
     * @param i
     * @param j
     * @return
     */
    private int get1DPosition(int i, int j) {
        return toInnerIdx(i)* N + toInnerIdx(j);
    }

    private void makeUnion(int pos, int destPos) {
        int root = uf.find(destPos);
        byte rootStatus = status[root];
        byte posStatus = status[pos];
        System.out.println("Statuses: " + posStatus + " " + rootStatus);
        status[root] = (byte)(rootStatus | posStatus);  // update root with pos status

        System.out.println("Attach " + pos + " to " + destPos);
        uf.union(pos, destPos);
    }

    private byte getStatus(int pos) {
        return  (byte)(0b000 | isOnTop(pos) | isOnBottom(pos));
    }

    private byte isOnTop(int pos) {
        if (pos < N)
            return 0b001;
        else
            return 0b100;
    }

    private byte isOnBottom(int pos) {
        if (pos > NN - N)
            return 0b010;
        else
            return 0b100;
    }

    private String intArrayToString(int ... args) {
        String str = "[";
        for (int i = 0; i < args.length; i++) {
            str += args[i] + ", ";
        }
        return str.substring(0, str.length() - 2) + "]";
    }

    public static void main(String[] args) {
    }
}