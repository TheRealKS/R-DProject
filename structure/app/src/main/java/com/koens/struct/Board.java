package com.koens.struct;

import com.koens.struct.entity.Entity;
import com.koens.struct.entity.EntityManager;
import com.koens.struct.entity.NonMovingEntity;
import com.koens.struct.entity.NonMovingEntityType;
import com.koens.struct.entity.NonPlayableEntity;
import com.koens.struct.entity.PushableEntity;
import com.koens.struct.entity.PushableEntityType;

public class Board {

    private Tile[][] configuration;
    EntityManager entityList;

    private int n, m;

    public Board(int n, int m, EntityManager mn) {
        this.n = n;
        this.m = m;

        this.entityList = mn;

        this.configuration = new Tile[n + 2][m + 2];
        initBoard();
    }

    //Some sort of parser?

    public boolean playerMovePossible(Direction dir) {
        Position hypotheticalPosition = entityList.getPlayerPosition(true);
        return movePossible(dir, hypotheticalPosition);
    }

    public boolean movePossible(Direction dir, Position p) {
        p.moveInDirection(dir);
        if (p.getX() <= 0 || p.getX() >= n + 2) {
            return false;
        }
        if (p.getY() <= 0 || p.getY() >= m + 2) {
            return false;
        }

        Tile tileAtPosition = getTileAtPosition(p);
        if (!tileAtPosition.canBeMovedInto()) {
            return false;
        }

        return true;
    }

    public Tile getTileAtPosition(Position position) {
        return this.configuration[position.getX()][position.getY()];
    }

    public Entity entityAt(Position p) {
        return entityList.getPickUpAtPosition(p);
    }

    public Boolean checkVictory() {
        for (Tile[] t : configuration) {
            for (Tile t1 : t) {
                if (t1 instanceof Slot || t1 instanceof FlagTile) {
                    if (!t1.getOccupied()) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private void initBoard() {
        int i;
        for (i = 0; i <= n + 1; i++) {
            configuration[i][0] = new Tile(false, new Position(i, 0));
            configuration[i][m + 1] = new Tile(false, new Position(i, m + 1));
        }
        for (i = 0; i <= m + 1; i++) {
            configuration[0][i] = new Tile(false, new Position(0, i));
            configuration[n + 1][i] = new Tile(false, new Position(n + 1, i));
        }

        for (i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                configuration[i][j] = new Tile(true, new Position(i, j));
            }
        }

        configuration[3][4] = new Slot(new Position(3, 4));
        configuration[6][7] = new FlagTile(new Position(6, 7));
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
                    if (e instanceof NonMovingEntity) {
                        NonMovingEntity n = (NonMovingEntity) e;
                        if (n.type == NonMovingEntityType.DOOR) {
                            sb.append("D");
                        } else {
                            sb.append("W");
                        }
                    } else {
                        PushableEntity p = (PushableEntity)e;
                        if (p.type == PushableEntityType.BOULDER) {
                            sb.append("B");
                        } else {
                            sb.append("K");
                        }
                    }
                    continue;
                }
                if (!t1.canBeMovedInto()) {
                    sb.append("| ");
                } else {
                    if (t1 instanceof Slot) {
                        if (t1.getOccupied()) {
                            sb.append("I");
                        } else {
                            sb.append("S");
                        }
                    } else if (t1 instanceof FlagTile) {
                        if (t1.getOccupied()) {
                            sb.append("P");
                        } else {
                            sb.append("F");
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

    public void removeEnititiesAfterCollision(NonMovingEntity e, PushableEntity pushableEntity) {
        entityList.remove(e);
        entityList.remove(pushableEntity);
    }

    public int getN() {return this.n; }
    public int getM() {return this.m; }
    public Tile[][] getConfiguration() {return this.configuration; }
}
