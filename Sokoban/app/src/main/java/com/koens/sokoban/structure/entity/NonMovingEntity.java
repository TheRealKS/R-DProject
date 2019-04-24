package com.koens.sokoban.structure.entity;

import com.koens.sokoban.structure.Board;
import com.koens.sokoban.structure.Direction;
import com.koens.sokoban.structure.Position;

public class NonMovingEntity extends NonPlayableEntity {


    public NonMovingEntity(Position p, Board b, EntityType type) {
        super(p, b);
        this.type = type;
    }

    @Override
    void collide(Direction from, Entity collider)
    {
        if (collider.type.isPushable())
        {
            if (type == EntityType.WATER && collider.type == EntityType.BOULDER)
            {
                collider.approveCollision(this);
            }
            else if (type == EntityType.DOOR && collider.type == EntityType.KEY)
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
