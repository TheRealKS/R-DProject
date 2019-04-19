package com.koens.struct;

import com.koens.struct.entity.EntityManager;
import com.koens.struct.entity.NonPlayableEntity;

public class Board {gsgsgssgsg

    private Tile[][] configuration;
    EntityManager entityList;

    private int n, m;

    public Board(int n, int m, EntityManager mn) {
        this.n = n;
        this.m = m;

        this.entityList = mn;

        this.configuration = new Tile[n+2][m+2];
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
        int i;
        for (i = 0; i <= n+1; i++) {
            configuration[i][0] = new Tile(false, new Position(i, 0));
            configuration[i][m+1] = new Tile(false, new Position(i, m+1));
        }
        for (i = 0; i <= m+1; i++) {
            configuration[0][i] = new Tile(false, new Position(0, i));
            configuration[n+1][i] = new Tile(false, new Position(n+1, i));
        }

        for (i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                configuration[i][j] = new Tile(true, new Position(i, j));
            }
        }

        configuration[3][4] = new Slot(new Position(3, 4));
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Tile[] t : configuration) {
            for (Tile t1 : t) {
                NonPlayableEntity e = entityList.getPickUpAtPosition(t1.getPosition());
                if (entityList.getPlayerPosition(false).equals(t1.getPosition())) {
                    sb.append("x");
                    continue;
                } else if (e != null) {
                    if (!e.isPickedUp()) {
                        sb.append("B");
                    } else {
                        sb.append("0");
                    }
                    continue;
                }
                if (!t1.canBeMovedInto()) {
                    sb.append("| ");
                } else {
                    if (t1 instanceof  Slot) {
                        if (((Slot) t1).getOccupied()) {
                            sb.append("D");
                        } else {
                            sb.append("S");
                        }
                    } else {
                        sb.append("0");
                    }
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }

}
