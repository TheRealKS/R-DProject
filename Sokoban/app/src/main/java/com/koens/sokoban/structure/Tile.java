package com.koens.sokoban.structure;

import com.koens.sokoban.structure.entity.Entity;

public class Tile {

    private Position position;

    private boolean canBeMovedInto;

    protected boolean occupied;

    public Tile(Boolean moveinto, Position position) {
        this.position = position;
        this.canBeMovedInto = moveinto;
    }

    public Position getPosition() {
        return this.position;
    }

    public boolean canBeMovedInto() {
        return this.canBeMovedInto;
    }

    public Boolean getOccupied(){
        return this.occupied;
    }

    public void setOccupied(Boolean v, Entity Occupier) {
        this.occupied = v;
    }
}
