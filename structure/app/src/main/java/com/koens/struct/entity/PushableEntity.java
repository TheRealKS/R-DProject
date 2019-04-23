package com.koens.struct.entity;

import com.koens.struct.Board;
import com.koens.struct.Direction;
import com.koens.struct.Position;
import com.koens.struct.Tile;

public class PushableEntity extends NonPlayableEntity
{

    private Entity lastCollidingEntity;

    public PushableEntity(Position position, Board b, EntityType t)
    {
        super(position, b);
        this.type = t;
    }

    @Override
    public void collide(Direction from, Entity collider)
    {
        if (this.lock)
            return;

        Direction to = Direction.opposite(from);
        if (board.movePossible(to, position.copy()))
        {
            Position h = position.copy();
            h.moveInDirection(to);
            Entity e = board.entityAt(h);
            if (e != null)
            {
                lastCollidingEntity = collider;
                e.collide(from, this);
            }
            else {

                collider.approveCollision(this);
                this.position.moveInDirection(to);
                Tile t = board.getTileAtPosition(this.position);
                t.setOccupied(true, this);
            }
        }
    }

    @Override
    void approveCollision(Entity collisionObject)
    {
        board.removeEnititiesAfterCollision((NonMovingEntity)collisionObject, this);
        lastCollidingEntity.approveCollision(this);
    }
}
