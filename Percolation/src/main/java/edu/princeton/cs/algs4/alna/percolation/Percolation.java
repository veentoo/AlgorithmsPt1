package edu.princeton.cs.algs4.alna.percolation;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private int N;
    private int NN;
    private byte[] status;
    private boolean isOpen, isEdge, isFilled;

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
        int upperPos = getNeighbourPosition(i, j, -1, 0);
        if (upperPos > -1) {
            uf.union(pos, upperPos);
        }
        int rightPos = getNeighbourPosition(i, j, 0, 1);
        if (rightPos > -1) {
            uf.union(pos, rightPos);
        }
        int bottomPos = getNeighbourPosition(i, j, 1, 0);
        if (bottomPos > -1) {
            uf.union(pos, bottomPos);
        }
        int leftPos = getNeighbourPosition(i, j, 0, -1);
        if (leftPos > -1) {
            uf.union(pos, leftPos);
        }
        status[pos] = 0x01;
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
        if ((status[pos] & 0x01) == 1)
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
        if ((toInnerIdx(i)+di < 0) || (toInnerIdx(j)+dj < 0)) {
            System.out.println("no neighbour ("+di+","+dj+") for " + intArrayToString(i, j));
            return -1;
        }
        int dPosition = get1DPosition(i + di, j + dj);
        System.out.println("neighbour (" + di + "," + dj + ") for "
                + intArrayToString(i, j) + " is " + dPosition);
        return dPosition;
    }

    private int get1DPosition(int i, int j) {
        return toInnerIdx(i)* N + toInnerIdx(j);
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