package com.koens.sokoban.structure;

import com.koens.sokoban.structure.entity.Entity;
import com.koens.sokoban.structure.entity.Player;

public class FlagTile extends Tile {

    public FlagTile(Position position) {
        super(true, position);
        occupied = false;
    }

    @Override
    public void setOccupied(Boolean v, Entity occupier) {
        if (occupier instanceof Player) {
            super.setOccupied(v, occupier);
        }
    }
}
