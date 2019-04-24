package com.koens.sokoban.structure.entity;

import com.koens.sokoban.structure.Board;
import com.koens.sokoban.structure.Direction;
import com.koens.sokoban.structure.Position;
import com.koens.sokoban.structure.Tile;

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
