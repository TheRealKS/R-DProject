package com.koens.struct.entity;

import com.koens.struct.Position;

public abstract class Boulder extends NonPlayableEntity {

    public Boulder(Position position) {
        super(position, null);
    }
}
