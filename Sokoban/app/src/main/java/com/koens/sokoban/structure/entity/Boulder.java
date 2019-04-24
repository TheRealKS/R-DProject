package com.koens.sokoban.structure.entity;

import com.koens.sokoban.structure.Position;

public abstract class Boulder extends NonPlayableEntity {

    public Boulder(Position position) {
        super(position, null);
    }
}
