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

    public void moveNorth() {
        this.y++;
    }

    public void moveSouth() {
        this.y--;
    }

    public void moveEast() {
        this.x++;
    }

    public void moveWest() {
        this.x--;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Position)) return false;
        Position p = (Position)o;
        return x == p.getX() && y == p.getY();
    }
}
