package com.koens.sokoban.structure.entity;

import com.koens.sokoban.structure.Board;
import com.koens.sokoban.structure.Direction;
import com.koens.sokoban.structure.Position;

import java.util.ArrayList;
import java.util.List;

/**
 * Can more than one entity exist in the same cell?
 * How to access type when an instance of NonPlayableEntity is
 * processed? E.g. if {@link #getPickUpAtPosition} is invoked,
 * how do I know if the returned object is a boulder, key or door?
 */
public class EntityManager {

    List<NonPlayableEntity> pickups;

    Board board;

    Player p;

    public EntityManager(Position playerPosition, Board b) {
        this.board = b;
        this.p = new Player(playerPosition, b);
        this.pickups = new ArrayList<>();

        pickups.add(new PushableEntity(new Position(2, 2), b, EntityType.BOULDER));
        pickups.add(new PushableEntity(new Position(4, 5), b, EntityType.BOULDER));
        pickups.add(new PushableEntity(new Position(7, 2), b, EntityType.KEY));
        pickups.add(new NonMovingEntity(new Position(4, 4), b, EntityType.WATER));
        pickups.add(new NonMovingEntity(new Position(5, 6), b, EntityType.DOOR));
    }


    public Position getPlayerPosition() {
        return this.getPlayerPosition(false);
    }
    /**
     * @param copy
     * @return
     */
    public Position getPlayerPosition(Boolean copy) {
        if (copy) {
            return p.getPosition().copy();
        } else {
            return p.getPosition();
        }
    }

    public void movePlayer(Direction dir) {
        this.p.move(dir);
    }

    public NonPlayableEntity getPickUpAtPlayerPosition() {
        return getPickUpAtPosition(p.getPosition());
    }

    public NonPlayableEntity getPickUpAtPosition(Position pos) {
        for (NonPlayableEntity e : pickups) {
            if (e.getPosition().equals(pos)) {
                return e;
            }
        }
        return null;
    }

    public void remove(Entity e) {
        if (e instanceof NonPlayableEntity) {
            pickups.remove(e);
        }
    }
}
