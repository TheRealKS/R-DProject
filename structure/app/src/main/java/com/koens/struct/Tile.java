package com.koens.struct;

import com.koens.struct.entity.Entity;

public class Tile {

    private Position position;

    private boolean canBeMovedInto; /** what does this mean.. please add JavaDoc */

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
