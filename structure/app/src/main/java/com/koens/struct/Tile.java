package com.koens.struct;

public class Tile {

    private Position position;

    private boolean canBeMovedInto;

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

    public void setCanBeMovedInto(Boolean v) {
        this.canBeMovedInto = v;
    }
}
