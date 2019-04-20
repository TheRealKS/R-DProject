package com.koens.struct.entity;

import com.koens.struct.Board;
import com.koens.struct.Direction;
import com.koens.struct.Position;

public class NonMovingEntity extends NonPlayableEntity {

    public NonMovingEntityType type;

    public NonMovingEntity(Position p, Board b, NonMovingEntityType type) {
        super(p, b);
        this.type = type;
    }

    @Override
    void collide(Direction from, Entity collider) {
        if (collider instanceof PushableEntity) {
            PushableEntity p = (PushableEntity)collider;
            if (type == NonMovingEntityType.WATER && p.type == PushableEntityType.BOULDER) {
                collider.approveCollision(this);
            } else if (type == NonMovingEntityType.DOOR && p.type == PushableEntityType.KEY) {
                collider.approveCollision(this);
            }
        }
    }

    @Override
    void approveCollision(Entity collisionObject) {
        //NonMovingEntity cannot collide
    }
}
