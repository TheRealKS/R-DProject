package com.koens.sokoban.structure.entity;


import com.koens.sokoban.structure.Board;
import com.koens.sokoban.structure.Position;

public abstract class NonPlayableEntity extends Entity
{

    public NonPlayableEntity(Position p, Board b)
    {
        this.position = p;
        this.board = b;
    }

}
