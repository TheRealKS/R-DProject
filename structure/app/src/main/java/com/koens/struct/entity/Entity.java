package com.koens.struct.entity;

import com.koens.struct.Board;
import com.koens.struct.Direction;
import com.koens.struct.Position;

public abstract class Entity {

    protected boolean lock;
    Position position = null;

    Board board;

    Position getPosition() {
        return position;
    }

    void setPosition(Position p) {
        this.position = p;
    }

    void move(Direction d) {
        this.position.moveInDirection(d);
    }

    public boolean getLocked() {
        return lock;
    }

    public void lock() {this.lock = true;}

    @Override
    public boolean equals(Object o) {
        if (o instanceof Entity) {
            return ((Entity) o).getPosition().equals(this.position);
        }
        return false;
    }

    abstract void collide(Direction from, Entity collider);

    abstract void approveCollision(Entity collisionObject);
}
