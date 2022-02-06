package com.company;

public class Element implements DynamicSetElement, Comparable
{
    public int k;
    public Element(int g)
    {
        k = g;
    }
    @Override
    public void setKey(Comparable key) {
        k = (int)key;
    }

    @Override
    public Comparable getKey() {
        return k;
    }

    @Override
    public int compareTo(Object e) {
        return (int)e - k;
    }

}
