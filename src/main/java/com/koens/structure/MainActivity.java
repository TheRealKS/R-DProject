package com.koens.structure;

import android.annotation.SuppressLint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.DynamicDrawableSpan;
import android.text.style.ImageSpan;
import android.widget.LinearLayout.LayoutParams;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.text.SpannableStringBuilder;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Spannable;

import com.koens.struct.Direction;
import com.koens.struct.Game;

import org.w3c.dom.Text;

import java.lang.reflect.Field;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener {

    TextView tileLayer;
    TextView gameLayer;
    Game g;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        g = new Game(getBaseContext());

        tileLayer = findViewById(R.id.tileLayer);
        gameLayer = findViewById(R.id.gameLayer);
        gameLayer.setOnTouchListener(this);

        redraw();
    }
    
    @Override
    public boolean onTouch (View v, MotionEvent event) {
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

    @SuppressLint("SetTextI18n")
    private void redraw()
    {
        SpannableStringBuilder sb = new SpannableStringBuilder (g.getTileRepresentation());
        SpannableStringBuilder sb2 = new SpannableStringBuilder (g.getGameRepresentation());
        SpannableStringBuilder new_sb = new SpannableStringBuilder ();
        SpannableStringBuilder new_sb2 = new SpannableStringBuilder ();

        int tile_size = 150;
        tileLayer.setLineSpacing(0, 0.7f);
        gameLayer.setLineSpacing(0, 0.7f);

        int sb_len = sb.length();
        new_sb.append("\n");
        for(int i = 0; i < sb_len; i+=3)
        {
            if(!sb.subSequence(0, 3).toString().equals("..."))
            {
                new_sb.append(getImageSpannable(getResId("wall_" + sb.subSequence(0, 3), R.drawable.class), tile_size, tile_size));
            }
            else
            {
                new_sb.append("\n");
            }
            sb.delete(0, 3);
        }
        new_sb.delete(new_sb.length()-1, new_sb.length());

        int sb2_len = sb2.length();
        new_sb2.append("\n\n");
        for(int i = 0; i < sb2_len; i++)
        {
            if(sb2.subSequence(0, 1).toString().equals("x"))
            {
                new_sb2.append(getImageSpannable(R.drawable.arrow, tile_size, tile_size));
            }
            else if(sb2.subSequence(0, 1).toString().equals("b"))
            {
                new_sb2.append(getImageSpannable(R.drawable.key, tile_size, tile_size));
            }
            else if(sb2.subSequence(0, 1).toString().equals("d"))
            {
                new_sb2.append(getImageSpannable(R.drawable.ball, tile_size, tile_size));
            }
            else if(sb2.subSequence(0, 1).toString().equals("s"))
            {
                new_sb2.append(getImageSpannable(R.drawable.ball, tile_size, tile_size));
            }
            else if(sb2.subSequence(0, 1).toString().equals("0"))
            {
                new_sb2.append(getImageSpannable(getResId("wall_mm0", R.drawable.class), tile_size, tile_size));
            }
            else
            {
                new_sb2.append("\n");
            }
            sb2.delete(0, 1);
        }
        new_sb2.delete(new_sb.length()-1, new_sb.length());

        gameLayer.setText(new_sb2);
        tileLayer.setText(new_sb);
    }

    private Spannable getImageSpannable(int drawableId, int targetWidth, int targetHeight) {

        Bitmap originalBitmap = BitmapFactory.decodeResource(getResources(), drawableId);
        Bitmap bitmap = Bitmap.createScaledBitmap(originalBitmap, targetWidth, targetHeight, true);

        Drawable dr = new BitmapDrawable(getResources(), bitmap);
        dr.setBounds(0, 0, bitmap.getWidth(), bitmap.getHeight());

        Spannable imageSpannable = new SpannableString("\uFFFC");
        ImageSpan imgSpan = new ImageSpan(dr, DynamicDrawableSpan.ALIGN_BOTTOM);

        imageSpannable.setSpan(imgSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return imageSpannable;
    }

    public static int getResId(String resName, Class<?> c)
    {
        try {
            Field idField = c.getDeclaredField(resName);
            return idField.getInt(idField);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
}
