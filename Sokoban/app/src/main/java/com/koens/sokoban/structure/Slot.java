package com.koens.sokoban.structure;

import com.koens.sokoban.structure.entity.Entity;
import com.koens.sokoban.structure.entity.EntityType;
import com.koens.sokoban.structure.entity.PushableEntity;

public class Slot extends Tile {

    public Slot(Position pos) {
        super(true, pos);
        occupied = false;
    }

    @Override
    public void setOccupied(Boolean v, Entity occupier) {
        if (occupier instanceof PushableEntity) {
            if (((PushableEntity)occupier).type == EntityType.BOULDER) {
                occupier.lock();
                super.setOccupied(v, occupier);
            }
        }
    }

}
