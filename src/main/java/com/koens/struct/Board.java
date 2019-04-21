package com.koens.struct;

import android.content.Context;
import android.text.SpannableStringBuilder;

import com.koens.struct.entity.EntityManager;
import com.koens.struct.entity.NonPlayableEntity;

public class Board {

    private Tile[][] configuration;
    private Context ctx;
    EntityManager entityList;

    private int n, m;

    public Board(int n, int m, EntityManager mn, Context c) {
        this.n = n;
        this.m = m;
        this.ctx = c;
        this.entityList = mn;

        this.configuration = new Tile[n][m];
        initBoard();
    }

    //Some sort of parser?

    public void setTile(Position pos, Tile t) {
        this.configuration[pos.getX()][pos.getY()] = t;
    }

    public boolean playerMovePossible(Direction dir) {
        Position hypotheticalPosition = entityList.getPlayerPosition(true);
        hypotheticalPosition.moveInDirection(dir);
        if (hypotheticalPosition.getX() <= 0 || hypotheticalPosition.getX() >= n+2) {
            return false;
        }
        if (hypotheticalPosition.getY() <= 0 || hypotheticalPosition.getY() >= m+2) {
            return false;
        }

        Tile tileAtPosition = getTileAtPosition(hypotheticalPosition);
        if (!tileAtPosition.canBeMovedInto()) {
            return false;
        }

        return true;
    }

    public boolean slotAtPosition(Position position) {
        Tile t = getTileAtPosition(position);
        return t instanceof Slot;
    }

    public Tile getTileAtPosition(Position position) {
        return this.configuration[position.getX()][position.getY()];
    }

    public Boolean checkVictory() {
        for (Tile[] t : configuration) {
            for (Tile t1 : t) {
                if (t1 instanceof Slot) {
                    if (!((Slot) t1).getOccupied()) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private void initBoard() {
        configuration[0][0] = new Tile(false, true, new Position(0, 0));
        configuration[0][1] = new Tile(false, false, new Position(0, 1));
        configuration[0][2] = new Tile(false, false, new Position(0, 2));
        configuration[0][3] = new Tile(false, false, new Position(0, 3));
        configuration[0][4] = new Tile(false, false, new Position(0, 4));
        configuration[0][5] = new Tile(false, false, new Position(0, 5));
        configuration[0][6] = new Tile(false, false, new Position(0, 6));

        configuration[1][0] = new Tile(false, false, new Position(1, 0));
        configuration[1][1] = new Tile(false, false, new Position(1, 1));
        configuration[1][2] = new Tile(true, false, new Position(1, 2));
        configuration[1][3] = new Tile(false, false, new Position(1, 3));
        configuration[1][4] = new Tile(true, false, new Position(1, 4));
        configuration[1][5] = new Tile(true, false, new Position(1, 5));
        configuration[1][6] = new Tile(false, false, new Position(1, 6));

        configuration[2][0] = new Tile(false, false, new Position(2, 0));
        configuration[2][1] = new Tile(true, false, new Position(2, 1));
        configuration[2][2] = new Tile(true, false, new Position(2, 2));
        configuration[2][3] = new Tile(true, false, new Position(2, 3));
        configuration[2][4] = new Tile(true, false, new Position(2, 4));
        configuration[2][5] = new Tile(true, false, new Position(2, 5));
        configuration[2][6] = new Tile(false, false, new Position(2, 6));

        configuration[3][0] = new Tile(false, false, new Position(3, 0));
        configuration[3][1] = new Tile(true, false, new Position(3, 1));
        configuration[3][2] = new Tile(true, false, new Position(3, 2));
        configuration[3][3] = new Tile(true, false, new Position(3, 3));
        configuration[3][4] = new Tile(true, false, new Position(3, 4));
        configuration[3][5] = new Tile(true, false, new Position(3, 5));
        configuration[3][6] = new Tile(false, false, new Position(3, 6));

        configuration[4][0] = new Tile(false, false, new Position(4, 0));
        configuration[4][1] = new Tile(false, false, new Position(4, 1));
        configuration[4][2] = new Tile(false, false, new Position(4, 2));
        configuration[4][3] = new Tile(false, false, new Position(4, 3));
        configuration[4][4] = new Tile(false, false, new Position(4, 4));
        configuration[4][5] = new Tile(true, false, new Position(4, 5));
        configuration[4][6] = new Tile(false, false, new Position(4, 6));

        configuration[5][0] = new Tile(false, false, new Position(5, 0));
        configuration[5][1] = new Tile(true, false, new Position(5, 1));
        configuration[5][2] = new Tile(true, false, new Position(5, 2));
        configuration[5][3] = new Tile(true, false, new Position(5, 3));
        configuration[5][4] = new Tile(true, false, new Position(5, 4));
        configuration[5][5] = new Tile(true, false, new Position(5, 5));
        configuration[5][6] = new Tile(false, false, new Position(5, 6));

        configuration[6][0] = new Tile(false, false, new Position(6, 0));
        configuration[6][1] = new Tile(false, false, new Position(6, 1));
        configuration[6][2] = new Tile(false, false, new Position(6, 2));
        configuration[6][3] = new Tile(false, false, new Position(6, 3));
        configuration[6][4] = new Tile(false, false, new Position(6, 4));
        configuration[6][5] = new Tile(false, false, new Position(6, 5));
        configuration[6][6] = new Tile(false, false, new Position(6, 6));

        configuration[5][1] = new Slot(new Position(5, 1));
    }

    public String drawBoard()
    {
        DrawBoard draw = new DrawBoard(ctx, entityList, configuration);
        return draw.drawBoardLayer();
    }

    public String drawGame()
    {
        DrawBoard draw = new DrawBoard(ctx, entityList, configuration);
        return draw.drawGameLayer();
    }
}
