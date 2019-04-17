package com.koens.struct.entity;


import com.koens.struct.Position;

public class NonPlayableEntity extends Entity {

    private Boolean pickedup = false;

    public NonPlayableEntity(Position p) {
        this.position = p;
    }

    public boolean isPickedUp() {
        return pickedup;
    }

    public void setPickedup(Boolean v) {
        this.pickedup = v;
    }
}
