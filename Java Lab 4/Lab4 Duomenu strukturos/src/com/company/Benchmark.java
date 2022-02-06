package com.company;

import java.util.Random;

public class Benchmark  {
    private int params[];

    public Benchmark(int... par) {
        params = par;
    }

    public void RunBenchInsert(int k) {
        for (int par : params) {
            BTree tree = new BTree(k);
            Random rand = new Random();
            long start = System.nanoTime();
            for (int i = 0; i < par; i++) {
                int n = rand.nextInt(10000);
                tree.Insert(new Element(n));
            }
            long laps = System.nanoTime() - start;
            System.out.println(par + " - " + laps / 1000000.0 + "ms");
        }
    }

    public void RunBenchDelete(int k) {
        for (int par : params) {
            BTree tree = new BTree(k);
            Random rand = new Random();
            for (int i = 0; i < par; i++) {
                int n = rand.nextInt(10000);
                tree.Insert(new Element(n));
            }
            long start = System.nanoTime();
            for (int i = 0; i < par; i++) {
                int n = rand.nextInt(10000);
                tree.delete(new Element(n));
            }
            long laps = System.nanoTime() - start;
            System.out.println(par + " - " + laps / 1000000.0 + "ms");
        }
    }

    public void RunBenchSearch(int k) {
        for (int par : params) {
            BTree tree = new BTree(k);
            Random rand = new Random();
            for (int i = 0; i < par; i++) {
                int n = rand.nextInt(10000);
                tree.Insert(new Element(n));
            }
            long start = System.nanoTime();
            for (int i = 0; i < par; i++) {
                int n = rand.nextInt(10000);
                tree.search(new Element(n));
            }
            long laps = System.nanoTime() - start;
            System.out.println(par + " - " + laps / 1000000.0 + "ms");
        }
    }
}
