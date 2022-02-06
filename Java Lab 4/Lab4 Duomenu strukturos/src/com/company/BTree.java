package com.company;


public class BTree implements DynamicSetElement {
    //minimum degree;
    private int t;
    // The maximum number of keys in any node;
    private int maxKeys;
    // The root of the B-tree;
    private BNode root;

    public BTree(int t) {
        this.t = t;
        maxKeys = 2 * t - 1;
        root = new BNode(0, true); // root is a leaf
    }
    //It should return node but for testing, it will make it int
    public Object search(Element k)
    {
        return root.BTreeSearch(k);
    }
    public void Insert(Comparable o) {
        DynamicSetElement key = DynamicSetElement.Helper.cast(o);
        BNode r = root;
        if (r.n == 2*t-1) {
            BNode s = new BNode(0, false);
            root = s;
            s.child[0] = r;
            SplitChild(s, 0, r);
            InsertNonfull(s, key);
        } else {
            InsertNonfull(r, key);
        }
    }
    private void SplitChild(BNode x, int pos, BNode y) {
        BNode z = new BNode(0, true);
        z.leaf = y.leaf;
        z.n = t - 1;
        for (int j = 0; j < t - 1; j++) {
            z.keys[j] = y.keys[j + t];
        }
        if (!y.leaf) {
            for (int j = 0; j < t; j++) {
                z.child[j] = y.child[j + t];
            }
        }
        y.n = t - 1;
        for (int j = x.n; j >= pos + 1; j--) {
            x.child[j + 1] = x.child[j];
        }
        x.child[pos + 1] = z;

        for (int j = x.n - 1; j >= pos; j--) {
            x.keys[j + 1] = x.keys[j];
        }
        x.keys[pos] = y.keys[t - 1];
        x.n = x.n + 1;
    }

    private void InsertNonfull(BNode x, DynamicSetElement k) {
        Comparable kKey = k.getKey();
        if (x.leaf) {
            int i = 0;
            for (i = x.n - 1; i >= 0 && x.keys[i].compareTo(kKey) > 0; i--) {
                x.keys[i + 1] = x.keys[i];
            }
            x.keys[i + 1] = k;
            x.n = x.n + 1;
        } else {
            int i = 0;
            for (i = x.n - 1; i >= 0 && x.keys[i].compareTo(kKey) > 0; i--) {
            }
            ;
            i++;
            BNode tmp = x.child[i];
            if (tmp.n == 2 * t - 1) {
                SplitChild(x, i, tmp);
                if (x.keys[i].compareTo(kKey) < 0) {
                    i++;
                }
            }
            InsertNonfull(x.child[i], k);
        }
    }
    public void delete(Object key) {
        root.delete((Element) key);

        if (!root.leaf && root.n == 0)
            root = root.child[0];
    }

    public void display() {
        display(root);
    }

    // Display the tree
    private void display(BNode x) {
        System.out.print("|");
        assert (x == null);
        for (int i = 0; i < x.n; i++) {
            System.out.print(x.keys[i].getKey() + " ");
        }
        if (!x.leaf) {
            for (int i = 0; i < x.n + 1; i++) {
                display(x.child[i]);
            }
        }
    }

    public int findMaxChildren()
    {
        return findMaxChildren(root);
    }

    private int findMaxChildren(BNode x)
    {
        int temp = x.n;
        if (!x.leaf) {
            for (int i = 0; i < x.n + 1; i++) {
                int k = findMaxChildren(x.child[i]);
                if(temp < k)
                {
                    temp = k;
                }
            }
        }

        return temp;
    }

    public void setKey(Comparable key) {

    }

    public Comparable getKey() {
        return null;
    }

    public int compareTo(Object e) {
        return 0;
    }


    private class BNode<E> {
        // Number of keys stored in a node
        public int n;
        public DynamicSetElement[] keys;
        public BNode[] child;
        public boolean leaf;

        public BNode(int n, boolean leaf) {
            this.n = n;
            this.leaf = leaf;
            keys = new DynamicSetElement[maxKeys];
            if (leaf)
                child = null;
            else
                child = new BNode[maxKeys+1];
        }

        public Object BTreeSearch(Element k)
        {
            int i = 0;

            while (i < n && keys[i].compareTo(k.getKey()) < 0)
                i++;

            if (i < n && keys[i].compareTo(k.getKey()) == 0)
            {
                BNode temp = new BNode(this.n, this.leaf);
                temp.keys = this.keys;
                temp.child = this.child;
                return temp; // found it
            }

            if (leaf)
                return null;	// no child to search
            else {		// search child i
                return child[i].BTreeSearch(k);
            }
        }




        public void delete(Element k) {
            if (leaf)
                deleteFromLeaf(k);
            else {
                int i = 0;

                while (i < n && keys[i].compareTo(k.getKey()) < 0)
                    i++;
                if (i < n && keys[i].compareTo(k.getKey()) == 0)
                    deleteFromInternalNode(i);
                else {

                    BNode childTemp = child[i];
                    ensureFullEnough(i);
                    childTemp.delete(k);
                }
            }
        }

        private void ensureFullEnough(int i) {
            BNode childTemp = child[i];
            if (childTemp.n < t) {
                BNode leftSibling;
                Integer leftN;

                if (i > 0) {
                    leftSibling = child[i-1];
                    leftN = leftSibling.n;
                }
                else {
                    leftSibling = null;
                    leftN = 0;
                }

                if (leftN >= t) {
                    for (int j = childTemp.n-1; j >= 0; j--)
                        childTemp.keys[j+1] = childTemp.keys[j];
                    if (!childTemp.leaf)
                        for (int j = childTemp.n; j >= 0; j--)
                            childTemp.child[j+1] = childTemp.child[j];

                    // Move a key down from this node into child,from the left sibling into this node.
                    childTemp.keys[0] = keys[i-1];
                    keys[i-1] = leftSibling.keys[leftN-1];
                    leftSibling.keys[leftN-1] = null;


                    if (!childTemp.leaf) {
                        childTemp.child[0] = leftSibling.child[leftN]; //  move a pointer from the left sibling into child
                        leftSibling.child[leftN] = null;
                    }

                    leftSibling.n--;
                    childTemp.n++;
                }
                else {		// Same at the right
                    BNode rightSibling;
                    Integer rightN;

                    if (i < n) {
                        rightSibling = child[i+1];

                        rightN = rightSibling.n;
                    }
                    else {
                        rightSibling = null;
                        rightN = 0;
                    }

                    if (rightN >= t) {
                        childTemp.keys[childTemp.n] = keys[i];
                        keys[i] = rightSibling.keys[0];

                        if (!childTemp.leaf)
                            childTemp.child[childTemp.n] = rightSibling.child[0];

                        for (int j = 1; j < rightN; j++)
                            rightSibling.keys[j-1] = rightSibling.keys[j];
                        rightSibling.keys[rightN-1] = null;
                        if (!rightSibling.leaf) {
                            for (int j = 1; j <= rightN; j++)
                                rightSibling.child[j-1] = rightSibling.child[j];
                            rightSibling.child[rightN] = null;
                        }

                        rightSibling.n--;
                        childTemp.n++;

                    }
                    else {
                        if (leftN > 0) {
                            // Merge the left sibling into the child.
                            // Start by moving everything in the child
                            // right by t positions.
                            for (int j = childTemp.n-1; j >= 0; j--)
                                childTemp.keys[j+t] = childTemp.keys[j];
                            if (!childTemp.leaf)
                                for (int j = childTemp.n; j >= 0; j--)
                                    childTemp.child[j+t] = childTemp.child[j];

                            // Take everything from the left sibling.
                            for (int j = 0; j < leftN; j++) {
                                childTemp.keys[j] = leftSibling.keys[j];
                                leftSibling.keys[j] = null;
                            }
                            if (!childTemp.leaf)
                                for (int j = 0; j <= leftN; j++) {
                                    childTemp.child[j] = leftSibling.child[j];
                                    leftSibling.child[j] = null;
                                }

                            // Move a key down from this node into the child.
                            childTemp.keys[t-1] = keys[i-1];

                            childTemp.n += leftN + 1;

                            for (int j = i; j < n; j++) {
                                keys[j-1] = keys[j];
                                child[j-1] = child[j];
                            }
                            child[n-1] = child[n];
                            keys[n-1] = null;
                            child[n] = null;
                            n--;

                        }
                        else {
                            // Merge the right sibling into the child.
                            for (int j = 0; j < rightN; j++) {
                                childTemp.keys[j+childTemp.n+1] = rightSibling.keys[j];
                                rightSibling.keys[j] = null;
                            }
                            if (!childTemp.leaf)
                                for (int j = 0; j <= rightN; j++) {
                                    childTemp.child[j+childTemp.n+1] = rightSibling.child[j];
                                    rightSibling.child[j] = null;
                                }


                            childTemp.keys[t-1] = keys[i];

                            childTemp.n += rightN + 1;

                            for (int j = i+1; j < n; j++) {
                                keys[j-1] = keys[j];
                                child[j] = child[j+1];
                            }
                            keys[n-1] = null;
                            child[n] = null;
                            n--;

                        }
                    }
                }
            }
        }

        private void deleteFromLeaf(Element k) {
            int i = 0;
            while (i < n && keys[i].compareTo(k.getKey()) < 0)
                i++;
            if (i < n && keys[i].compareTo(k.getKey()) == 0) {
                for (int j = i + 1; j < n; j++)
                    keys[j - 1] = keys[j];
                keys[n - 1] = null;
                n--;
            }
        }

        private DynamicSetElement  findGreatestInSubtree() {
            if (leaf)
                return keys[n - 1];
            else {
                return child[n].findGreatestInSubtree();
            }
        }

        private DynamicSetElement  findSmallestInSubtree() {
            if (leaf)
                return keys[0];
            else {
                return child[0].findSmallestInSubtree();
            }
        }
            private void deleteFromInternalNode (int i)
            {
                DynamicSetElement  k = keys[i]; // key i
                BNode y = child[i];              // child preceding k
                if (y.n >= t) {              // does y have at least t keys?

                    DynamicSetElement kPrime = y.findGreatestInSubtree();
                    // findGreatestInSubtree
                    y.delete((Element) kPrime);
                    keys[i] = kPrime;
                } else {
                    BNode z = child[i + 1];
                    if (z.n >= t) {
                        DynamicSetElement kPrime = z.findSmallestInSubtree();
                        z.delete((Element)kPrime);
                        keys[i] = kPrime;
                    }
                    else {
                        y.keys[y.n] = k;
                        for (int j = 0; j < z.n; j++)
                            y.keys[y.n + j + 1] = z.keys[j];
                        if (!y.leaf)
                            for (int j = 0; j <= z.n; j++)
                                y.keys[y.n + j + 1] = z.keys[j];

                        y.n += z.n + 1;

                        // Remove k and z from this node.
                        for (int j = i + 1; j < n; j++) {
                            keys[j - 1] = keys[j];
                            child[j] = child[j + 1];
                        }
                        keys[n - 1] = null;
                        child[n] = null;
                        n--;


                        y.delete((Element)k); // recursively delete k from y
                    }
                }
            }

        }

    }
