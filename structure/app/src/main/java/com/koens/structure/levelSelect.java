package com.koens.structure;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class levelSelect extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_select);
    }

    //Pass 1, 2 or 3 via Intent

    int level;

    public void startLevel1(View v){
        level = 1;
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("SELECTED_LEVEL", level);
        startActivity(intent);
    }

    public void startLevel2(View v){
        level = 2;
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("SELECTED_LEVEL", level);
        startActivity(intent);
    }

    public void startLevel3(View v){
        level = 3;
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("SELECTED_LEVEL", level);
        startActivity(intent);
    }

}
