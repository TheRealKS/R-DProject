package com.koens.struct;

public class Tile {

    private Position position;

    private boolean canBeMovedInto;

    private boolean emptyTile;

    public Tile(Boolean moveinto, boolean empTile, Position position) {
        this.position = position;
        this.canBeMovedInto = moveinto;
        this.emptyTile = empTile;
    }

    public Position getPosition() {
        return this.position;
    }

    public boolean canBeMovedInto() {
        return this.canBeMovedInto;
    }

    public boolean isEmptyTile() {
        return this.emptyTile;
    }

    public void setCanBeMovedInto(Boolean v) {
        this.canBeMovedInto = v;
    }
}
