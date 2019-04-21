package com.koens.struct;

import com.koens.struct.entity.Entity;
import com.koens.struct.entity.PushableEntity;
import com.koens.struct.entity.PushableEntityType;

public class Slot extends Tile {

    public Slot(Position pos) {
        super(true, pos);
        occupied = false;
    }

    @Override
    public void setOccupied(Boolean v, Entity occupier) {
        if (occupier instanceof PushableEntity) {
            if (((PushableEntity)occupier).type == PushableEntityType.BOULDER) {
                occupier.lock();
                super.setOccupied(v, occupier);
            }
        }
    }

}
