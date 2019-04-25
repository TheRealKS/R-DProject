package com.koens.structure;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.koens.struct.Direction;
import com.koens.struct.Game;

import org.w3c.dom.Text;

import java.security.InvalidParameterException;
import java.util.NoSuchElementException;


/**
 * Game activity
 */
public class MainActivity extends AppCompatActivity implements View.OnTouchListener {

    Game g;
    GraphicsHandler GH;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        g = new Game(getBaseContext());


        GridView matrix = (GridView) findViewById(R.id.matrix);

        GH = new GraphicsHandler(this, this.g, matrix);
        GH.updateGridView();
        matrix.setOnTouchListener(this);

//        setupButtons();

    }


    @Override
    public boolean onTouch (View v, MotionEvent event)
    {
        int width = v.getWidth();
        int height = v.getHeight();
        float x = event.getX();
        float y = event.getY();
        float dxdy = (float) height / width;

        if (event.getAction() == MotionEvent.ACTION_UP) {
            if(y <= x * dxdy && y <= height - x * dxdy) {
                g.movePlayer(Direction.NORTH);
            } else if(y > x*dxdy && y <= height - x*dxdy) {
                g.movePlayer(Direction.WEST);
            } else if(y <= x*dxdy && y > height - x*dxdy) {
                g.movePlayer(Direction.EAST);
            } else if (y > x*dxdy && y > height - x*dxdy) {
                g.movePlayer(Direction.SOUTH);
            }
        }

        redraw();
        return true;
    }

    /**
     * E_BUTTON (Enumeration of buttons) contains the
     * button and its associated value. E.g. BTN_NORTH
     * will have value Direction.NORTH.
     */
    enum E_BUTTON {
        BTN_NORTH   (Direction.NORTH),
        BTN_EAST    (Direction.EAST),
        BTN_SOUTH   (Direction.SOUTH),
        BTN_WEST    (Direction.WEST);

        private final Direction value;

        E_BUTTON(Direction dir) {
            this.value = dir;
        }

        public Direction getValue() {
            return this.value;
        }
    }

    /**
     * For each BTN defined in {@link E_BUTTON}
     * a onClickListener is initialized such that it
     * will call {@link Game#movePlayer(Direction)} with
     * Direction := {@link E_BUTTON#value}.
     *
     * @throws InvalidParameterException when button not found in view. @todo replace with FinishAndExit (something like that)
     */
    private void setupButtons() throws NoSuchElementException
    {
        for(E_BUTTON BTN : E_BUTTON.values())
        {
            final String btnId = BTN.toString();
            final int identifier = getResources().getIdentifier(btnId, "id", getPackageName());
            Button btn = ((Button) findViewById(identifier));

            if(btn == null)
                throw new NoSuchElementException("No button with id " + BTN.toString() + " found. ");

            btn.setOnClickListener((View v) -> {
                    g.movePlayer(BTN.getValue());
                    redraw();
                }
            );

        }
        redraw();

    }

    /**
     * Pass current game state to graphicsHandler,
     * get visual representation and update view.
     */
    private void redraw()
    {
        GH.updateGridView();
    }
}
