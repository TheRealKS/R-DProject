package com.koens.struct;

public class Slot extends Tile {

    private Boolean occupied;

    public Slot(Position pos) {
        super(true, pos);
        occupied = false;
    }

    public Boolean getOccupied(){
        return this.occupied;
    }

    public void setOccupied(Boolean v) {
        this.occupied = v;
    }


}
