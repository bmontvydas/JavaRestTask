package com.company;

public class Main {

    public static void main(String[] args) {
        BTree b = new BTree(3);
        b.Insert(new Element(55));
        b.Insert(new Element(65));
        b.Insert(new Element(75));

        Benchmark bench = new Benchmark(10000, 20000, 40000, 80000, 160000, 320000, 640000);
        bench.RunBenchInsert( 650);
        System.out.println("Search");
        bench.RunBenchSearch(650);
        System.out.println("Delete");
        bench.RunBenchSearch(650);

        b.display();

    }
}
