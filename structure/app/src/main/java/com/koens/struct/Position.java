package com.koens.struct;

public class Position {
    private int x;
    private int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void moveInDirection(Direction direction)
    {
        switch (direction) {
            case NORTH:
                this.x--;
                break;
            case SOUTH:
                this.x++;
                break;
            case EAST:
                this.y++;
                break;
            case WEST:
                this.y--;
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
        if(H.getX() > x || H.getX() < 0 || H.getY() > y || H.getY() < 0)
            return null;

        return H;
    }

    public Position copy() {
        return new Position(x, y);
   }


    @Override
    public int hashCode()
    {
        int acc = 0;
        String ascii = this.toString();

        for(int i = 0; i < ascii.length(); i++)
            acc += ascii.charAt(i) * acc * 31;
        return acc;
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
