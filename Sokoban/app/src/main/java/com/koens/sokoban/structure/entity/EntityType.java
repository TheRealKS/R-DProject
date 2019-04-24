package com.koens.sokoban.structure.entity;

public enum EntityType
{
    // non moving
    WATER(false, "water_tile_0"),
    DOOR(false, "que"),

    // pushable
    BOULDER(true, "boulder"),
    KEY(true, "key");

    private boolean pushable;
    private String drawableName;

    EntityType(boolean pushable, String drawableName)
    {
        this.pushable = pushable;
        this.drawableName = drawableName;
    }


    public boolean isPushable() {
        return this.pushable;
    }

    public String getDrawableName() {
        return this.drawableName;
    }
}
