package com.koens.struct.entity;

import com.koens.struct.Direction;
import com.koens.struct.Position;

public abstract class Entity {

    Position position = null;

    Position getPosition() {
        return position;
    }

    void setPosition(Position p) {
        this.position = p;
    }

    void move(Direction d) {
        this.position.moveInDirection(d);
    }
}
