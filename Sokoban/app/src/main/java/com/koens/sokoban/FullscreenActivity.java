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
        setContentView(R.layout.activity_fullscreen);

        g = new Game(getBaseContext());

    }

    @Override
    protected void onStart()
    {
        super.onStart();

        matrix = findViewById(R.id.matrix);
        if(matrix == null)
            System.out.println("GridView not found");

        matrix.setOnTouchListener(this);
        GH = new GraphicsHandler(getBaseContext(), this.g, matrix);
        redraw();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event)
    {
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

        this.redraw();
        return true;
    }

    private void redraw()
    {

        System.out.println(GH.toString());
        if(!GH.updateGridView())
            System.out.println("Failed to update gridView");
    }
}