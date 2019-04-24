package com.koens.struct.entity;

import com.koens.struct.Position;

public class Player extends Entity {

    NonPlayableEntity carry;
    Boolean carrying;

    public Player(Position p) {
        this.position = p;
    }

    public void pickUp(NonPlayableEntity p) {
        this.carry = p;
        this.carrying = true;
    }

    public void drop() {
        this.carry = null;
        this.carrying = false;
    }
}