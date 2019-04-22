package com.koens.struct;

public enum Direction {
    NORTH,
    SOUTH,
    EAST,
    WEST;

    public static Direction opposite(Direction d)
    {
        switch (d)
        {
            case NORTH: return SOUTH;
            case SOUTH: return NORTH;
            case EAST: return WEST;
            case WEST: return EAST;
            default: return null;
        }
        //return null;
    }
}
