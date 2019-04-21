package com.koens.struct;

import android.content.Context;
import android.content.res.Resources;
import android.widget.Toast;

import com.koens.struct.entity.Boulder;
import com.koens.struct.entity.EntityManager;
import com.koens.struct.entity.NonPlayableEntity;

public class Game {

    private EntityManager entityManager;
    private Board board;
    private Context ctx;

    public Game(Context c) {
        this.entityManager = new EntityManager(new Position(3, 3));
        this.board = new Board(7, 7, entityManager, c);
        this.ctx = c;
    }

    public void movePlayer(Direction dir) {
        if (board.playerMovePossible(dir)) {
            //Move the player
            entityManager.movePlayer(dir);

            if (board.slotAtPosition(entityManager.getPlayerPosition(false))) {
                Slot s = (Slot)board.getTileAtPosition(entityManager.getPlayerPosition(false));
                if (entityManager.isPlayerCarrying() && !s.getOccupied()) {
                    s.setOccupied(true);
                    entityManager.dropPickup(entityManager.getPlayerPosition(false));
                    if (board.checkVictory()) {
                        Toast.makeText(ctx, "You win!", Toast.LENGTH_LONG).show();
                    }
                }
            }

            NonPlayableEntity pickup = entityManager.getPickUpAtPlayerPosition();
            if (pickup != null) {
                if (pickup instanceof Boulder && !pickup.isPickedUp()) {
                    entityManager.pickUp(pickup);
                }
            }
        }
    }

    public String getTileRepresentation() {
        return board.drawBoard();
    }

    public String getGameRepresentation() {
        return board.drawGame();
    }
}
