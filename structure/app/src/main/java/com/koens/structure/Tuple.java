package com.koens.structure;

/**
 * 2-tuple object that stores 2 objects that may have different types.
 *
 * @param <X> Object 1
 * @param <Y> Object 2
 */
public class Tuple<X, Y>
{
    public final X x;
    public final Y y;

    public Tuple(X x, Y y)
    {
        this.x = x;
        this.y = y;
    }

    public String toString()
    {
        return " T:[" + this.x.toString() + ";" + this.y.toString() + "]";
    }
}