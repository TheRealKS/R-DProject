package com.koens.struct;

import com.koens.struct.entity.Entity;

/**
 * Consider the game matrix as a two layered matrix. The first
 * layer contains information about the layout; walls walkable tiles etc.
 * The upper layer contains entities that can move through the game matrix
 * or have some special property. Such entities are for example the Player,
 * a Boulder or Water. The first layer is made up of tiles.
 * @author Koen
 */
public class Tile
{
    /** Position of Tile */
    private Position position;

    /** Permit entities to move over this tile */
    private boolean canBeMovedInto;

    /** Currently occupied by some entity */
    protected boolean occupied;

    /**
     *
     * @param moveInto Allow entities to move over this Tile
     * @param position Current position of Tile
     */
    public Tile(boolean moveInto, Position position)
    {
        this.position = position;
        this.canBeMovedInto = moveInto;
    }

    /** @return {@link #position} */
    public Position getPosition()
    {
        return this.position;
    }

    /** @return {@link #canBeMovedInto} */
    public boolean canBeMovedInto()
    {
        return this.canBeMovedInto;
    }

    /** @return {@link #occupied} */
    public boolean getOccupied()
    {
        return this.occupied;
    }

    /**
     * Set tile as occupied by entity Occupier
     *
     * @param v If entity 'enters' cell, set v to true. If entity
     *          'leaves' cell, set v to false.
     * @param Occupier An entity that occupies this tile
     */
    public void setOccupied(boolean v, Entity Occupier)
    {
        this.occupied = v;
    }
}
