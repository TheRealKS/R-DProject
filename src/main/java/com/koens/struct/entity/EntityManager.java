package com.koens.struct.entity;

import com.koens.struct.Direction;
import com.koens.struct.Position;

import java.util.ArrayList;
import java.util.List;

public class EntityManager {

    List<NonPlayableEntity> pickups;

    Player p;

    public EntityManager(Position playerPosition) {
        this.p = new Player(playerPosition);
        this.pickups = new ArrayList<>();
        pickups.add(new Boulder(new Position(1, 2)));
    }

    public Position getPlayerPosition(Boolean copy) {
        if (copy) {
            return new Position(p.getPosition().getX(), p.getPosition().getY());
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

    public void pickUp(NonPlayableEntity pickedup) {
        p.pickUp(pickedup);
        pickedup.setPickedup(true);
    }

    public void dropPickup(Position dropPosition) {
        p.carry.setPickedup(false);
        p.carry.setPosition(dropPosition);
        p.drop();
    }

    public Boolean isPlayerCarrying() {
        return p.carrying;
    }
}
