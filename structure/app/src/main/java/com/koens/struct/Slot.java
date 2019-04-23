package com.koens.struct;

import com.koens.struct.entity.Entity;
import com.koens.struct.entity.EntityType;
import com.koens.struct.entity.PushableEntity;

public class Slot extends Tile
{

    public Slot(Position pos)
    {
        super(true, pos);
        occupied = false;
    }

    public void setOccupied(boolean v, Entity occupier)
    {
        if (occupier instanceof PushableEntity)
        {
            if (((PushableEntity)occupier).type == EntityType.BOULDER)
            {
                occupier.lock();
                this.occupied = true;
            }
        }
    }

}
