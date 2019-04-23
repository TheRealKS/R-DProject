package com.koens.struct.entity;

import com.koens.struct.Board;
import com.koens.struct.Direction;
import com.koens.struct.Position;

public class NonMovingEntity extends NonPlayableEntity {


    public NonMovingEntity(Position p, Board b, EntityType type) {
        super(p, b);
        this.type = type;
    }

    @Override
    void collide(Direction from, Entity collider)
    {
        if (collider instanceof PushableEntity)
        {
            PushableEntity p = (PushableEntity)collider;

            if (type == EntityType.WATER && p.type == EntityType.BOULDER)
            {
                collider.approveCollision(this);
            }
            else if (type == EntityType.DOOR && p.type == EntityType.KEY)
            {
                collider.approveCollision(this);
            }
        }
    }

    @Override
    void approveCollision(Entity collisionObject) {
        //NonMovingEntity cannot collide
    }
}
