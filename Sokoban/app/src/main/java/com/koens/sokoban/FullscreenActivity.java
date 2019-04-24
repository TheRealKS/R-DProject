package com.koens.sokoban;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.GridView;
import android.view.View;

import com.koens.sokoban.structure.Direction;
import com.koens.sokoban.structure.Game;

public class FullscreenActivity extends AppCompatActivity implements View.OnTouchListener {

    Game g;
    GraphicsHandler GH;
    GridView matrix;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        matrix = findViewById(R.id.matrix);
        //gameLayer.setOnTouchListener(this);

        g = new Game(getBaseContext());

        GH = new GraphicsHandler(getBaseContext(), this.g, matrix);

        MatrixAdapter MA = new MatrixAdapter(getBaseContext(), this.lastMap, this.n, this.m, this.game);
        this.gridView.setAdapter(MA);
        System.out.println(GH.toString());

        GH.updateGridView();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int width = v.getWidth();
        int height = v.getHeight();
        float x = event.getX();
        float y = event.getY();
        float dxdy = (float) height / width;

        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (y <= x * dxdy && y <= height - x * dxdy) {
                g.movePlayer(Direction.NORTH);
            } else if (y > x * dxdy && y <= height - x * dxdy) {
                g.movePlayer(Direction.WEST);
            } else if (y <= x * dxdy && y > height - x * dxdy) {
                g.movePlayer(Direction.EAST);
            } else if (y > x * dxdy && y > height - x * dxdy) {
                g.movePlayer(Direction.SOUTH);
            }
        }

        return true;
    }
}