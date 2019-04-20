package com.koens.struct.entity;

import com.koens.struct.Board;
import com.koens.struct.Direction;
import com.koens.struct.Position;
import com.koens.struct.Tile;

public class Player extends Entity {

    Direction aboutToMoveTo;

    public Player(Position p, Board b) {
        this.position = p;
        this.board = b;
    }

    public void move(Direction to) {
        Position p = this.position.copy();
        p.moveInDirection(to);
        Entity e = board.entityAt(p);
        if (e != null) {
            if (e instanceof PushableEntity) {
                aboutToMoveTo = to;
                e.collide(Direction.opposite(to), this);
            }
        } else {
            if (board.playerMovePossible(to)) {
                Tile previousTile = board.getTileAtPosition(this.position);
                this.position.moveInDirection(to);
                Tile t = board.getTileAtPosition(this.position);
                t.setOccupied(true, this);
                previousTile.setOccupied(false, this);
            }
        }
    }

    @Override
    public void collide(Direction from, Entity Collider) {

    }

    @Override
    void approveCollision(Entity collisionObject) {
        this.position.moveInDirection(aboutToMoveTo);
    }
}
