package com.koens.sokoban.structure;

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

    public void moveInDirection(Direction direction) {
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

   public Position copy() {
        return new Position(x, y);
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

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Position)) return false;
        Position p = (Position)o;
        return x == p.getX() && y == p.getY();
    }
}
