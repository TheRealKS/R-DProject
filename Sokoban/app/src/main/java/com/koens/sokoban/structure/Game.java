package com.koens.sokoban.structure;

import android.content.Context;
import android.widget.Toast;

import com.koens.sokoban.structure.entity.Entity;
import com.koens.sokoban.structure.entity.EntityManager;

import java.security.spec.ECFieldF2m;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class Game {

    private EntityManager entityManager;
    private Board board;
    private Context ctx;
    private long startTime;

    public Game(Context c) {
        this.board = new Board(8, 8, null);
        this.entityManager = new EntityManager(new Position( 1, 1), this.board);
        this.board.entityList = entityManager;
        this.ctx = c;
        this.startTime = Calendar.getInstance().getTimeInMillis();
    }

    public void movePlayer(Direction dir) {
        entityManager.movePlayer(dir);
        if (board.checkVictory()) {
            long endTime = Calendar.getInstance().getTimeInMillis();
            long difference = endTime - startTime;
            Date d = new Date(difference);
            DateFormat formatter = new SimpleDateFormat("HH:mm:ss.SSS");
            formatter.setTimeZone(TimeZone.getDefault());
            Toast.makeText(ctx, "You win! It took you: " + formatter.format(d), Toast.LENGTH_LONG).show();
        }
    }

    public String getRepresentation() {
        return board.toString();
    }

    public Board getBoard() {
        return board;
    }

    public Entity entityAtPosition(Position pos) {
        return this.board.entityAt(pos);
    }

    public EntityManager getEM() {
        return this.entityManager;
    }
}
