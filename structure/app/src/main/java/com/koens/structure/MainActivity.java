package com.koens.structure;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.koens.struct.Direction;
import com.koens.struct.Game;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    TextView tv;
    Game g;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String level= getIntent().getStringExtra("SELECTED_LEVEL");

        g = new Game(getBaseContext());

        tv = findViewById(R.id.textView);
        setupButtons();

        redraw();
    }

    private void setupButtons() {
        Button bttn;
        bttn = findViewById(R.id.button5);
        bttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                g.movePlayer(Direction.NORTH);
                redraw();
            }
        });
        bttn = findViewById(R.id.button6);
        bttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                g.movePlayer(Direction.SOUTH);
                redraw();
            }
        });
        bttn = findViewById(R.id.button7);
        bttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                g.movePlayer(Direction.EAST);
                redraw();
            }
        });
        bttn = findViewById(R.id.button8);
        bttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                g.movePlayer(Direction.WEST);
                redraw();
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void redraw() {
        tv.setText("\n" + g.getRepresentation());
    }
}
