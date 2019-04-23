package com.koens.struct.entity;


import com.koens.struct.Board;
import com.koens.struct.Position;

public abstract class NonPlayableEntity extends Entity
{

    public NonPlayableEntity(Position p, Board b)
    {
        this.position = p;
        this.board = b;
    }

}
