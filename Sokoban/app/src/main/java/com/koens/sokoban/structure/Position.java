package com.koens.sokoban.structure;

/**
 * Manages position in 2-dimensional array such that
 * array[x][y] is represented as Position(x,y).
 * @author Koen
 */
public class Position
{
    private int x;
    private int y;

    public Position(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    /**
     * Accessor for x-coordinate.
     * @return x
     */
    public int getX()
    {
        return x;
    }

    /**
     * Accessor for y-coordinate.
     * @return y
     */
    public int getY()
    {
        return y;
    }

    /**
     * Apply mutation to this instance of object
     * see {@link #getPosAfterMove(Direction, int, int)} if
     * you need the position after mutation without altering
     * current state.
     *
     * @param direction Element of {@link Direction}
     */
    public void moveInDirection(Direction direction)
    {
        switch (direction)
        {
            case NORTH:
                this.y--;
                break;
            case SOUTH:
                this.y++;
                break;
            case EAST:
                this.x++;
                break;
            case WEST:
                this.x--;
                break;
        }
    }

    /**
     * Applies direction, but does not alter current state.
     * It rather returns a new instance of {@link Position}.
     * If out of bound, no change made.
     *
     * @param dir Direction NORTH, SOUTH, EAST, WEST
     * @param x bound
     * @param y bound
     * @return Position
     */
    public Position getPosAfterMove(Direction dir, int x, int y)
    {
        Position H = this.copy();
        H.moveInDirection(dir);
        if( !(H.getX() < x && H.getX() >= 0) || !(H.getY() < y && H.getY() >= 0) )
            return null;

        return H;
    }

    /**
     * Copy
     * @return copy of current object such that hashCode is the same.
     */
    public Position copy()
    {
        return new Position(x, y);
    }

    /**
     * This is necessary for keys in a hashmap. Thanks for wasting
     * a my time finding that you deleted it.
     *
     * @return
     */
    @Override
    public int hashCode()
    {
        return this.x + this.y*1117;
    }


    @Override
    public boolean equals(Object o)
    {
        if (o.getClass() != this.getClass())
            return false;

        Position p = (Position) o;
        return (this.x == p.getX() && this.y == p.getY());
    }

    @Override
    public String toString()
    {
        return "(" + this.x + " ," + this.y + ")";
    }
}
