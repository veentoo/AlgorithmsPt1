package edu.princeton.cs.algs4.alna.percolation.test;

import edu.princeton.cs.algs4.alna.percolation.Percolation;
import org.junit.Test;

import static org.junit.Assert.*;

public class PercolationTest {

    private int N;

    public PercolationTest() {
    }

    @Test
    public void testIsOpen() {
        System.out.println("Start test: "
                + Thread.currentThread().getStackTrace()[1].getMethodName());
        Percolation p = new Percolation(3);

        p.open(1, 1);
        assertTrue(p.isOpen(1, 1));

        p.open(1, 3);
        assertTrue(p.isOpen(1, 3));

        p.open(1, 2);
        assertTrue(p.isOpen(1, 3));
    }
}
