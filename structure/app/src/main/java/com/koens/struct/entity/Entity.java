package com.koens.struct.entity;

import com.koens.struct.Board;
import com.koens.struct.Direction;
import com.koens.struct.Position;

/**
 * Entities are all objects in the game that have some special behavior
 * such as the player, boulder, water, etc.
 * @author Koen
 */
public abstract class Entity
{
    /** If true, no collision allowed */
    protected boolean lock;
    protected Position position = null;
    protected Board board;
    public EntityType type;

    /**
     * @return Position of entity
     */
    public Position getPosition() {
        return position;
    }

    /** @param p New position for entity */
    public void setPosition(Position p) {
        this.position = p;
    }

    /** @param d Direction in which entity must be moved */
    public void move(Direction d) {
        this.position.moveInDirection(d);
    }

    /** True if entity is locked */
    public boolean getLocked() {
        return lock;
    }

    /** Set entity to locked */
    public void lock() {
        this.lock = true;
    }

    @Override
    public boolean equals(Object o)
    {
        if (o instanceof Entity)
        {
            return ((Entity) o).getPosition().equals(this.position);
        }
        return false;
    }

    abstract void collide(Direction from, Entity collider);

    abstract void approveCollision(Entity collisionObject);
}
