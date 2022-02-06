package com.company;

public interface DynamicSetElement extends Comparable
{

        public void setKey(Comparable key);

        public Comparable getKey();

        public int compareTo(Object e);

        public static class Helper extends java.lang.Object
        {

            public static int compareTo(DynamicSetElement e, Object o)
            {
                if (o instanceof DynamicSetElement)
                    return e.getKey().compareTo(((DynamicSetElement) o).getKey());
                else if (o instanceof Comparable)
                    return e.getKey().compareTo(o);
                else
                    throw new ClassCastException("Attempt to compare a DynamicSetElement to an object that does not implement Comparable.");
            }

            public static DynamicSetElement cast(Object o)
            {
                if (o instanceof DynamicSetElement)
                    return (DynamicSetElement) o;
                else
                    throw new ClassCastException("Object fails to implement DynamicSetElement interface.");
            }
        }
}
