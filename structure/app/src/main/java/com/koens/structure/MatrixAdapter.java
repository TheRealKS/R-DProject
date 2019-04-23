package com.koens.structure;

import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.widget.BaseAdapter;

import com.koens.struct.Game;
import com.koens.struct.Position;
import com.koens.struct.entity.Entity;
import com.koens.struct.entity.EntityType;
import com.koens.struct.entity.NonMovingEntity;
import com.koens.struct.entity.NonPlayableEntity;
import com.koens.struct.entity.PushableEntity;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.lang.Math;

/**
 * GraphicsHandler will create a GridView, gridView will consequently
 * invoke this class to get its data.
 *
 * @author Karim Abdulahi
 */
public class MatrixAdapter extends BaseAdapter
{
    private Context context;
    private Map<Position, Tuple<GraphicsHandler.Sprite, String>> data;
    private int n, m;
    private Game game;
    private Map<Integer, ImageView> items;

    public MatrixAdapter(Context context, Map<Position, Tuple<GraphicsHandler.Sprite, String>> data,
                         int n, int m, Game game)
    {
        this.context = context;
        this.data = data;
        this.n = n;
        this.m = m;
        this.game = game;
        this.items = new HashMap<>();
    }


    private Position int2pos(int position)
    {
        final int y = position % (this.n+2);
        final int x = (int) Math.floor(position / ((double) this.n + 2));
        return new Position(y, x);
    }

    // Tell the GridView how many items it will get
    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public Object getItem(int position)
    {
        return this.items.get(  Integer.valueOf(position) );
    }


    // GridView will get data from here
    // i.e. will give the viewdata for every cel in the gridview
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ImageView imageView;

        if(convertView == null)
        {
            imageView = new ImageView(this.context);
            imageView.setPadding(0,0,0,0);
        }
        else
        {
            imageView = (ImageView) convertView; // @todo check if right type
        }

        Position pos = int2pos(position);
        Tuple<GraphicsHandler.Sprite, String> tuple   = this.data.get(pos);
        int id = 0;

        if(tuple != null)
        {
            String sprite = tuple.x.getSpriteId() + tuple.y;
            id = this.context.getResources().getIdentifier(sprite, "drawable", this.context.getPackageName());
        }

        if(id != 0)// only if res exists, setImage...
        {
            Resources r = context.getResources();

            Drawable[] layers = new Drawable[2];
            layers[0] = r.getDrawable(id, null);

            int drawableId = 0;
            Entity entity = this.game.entityAtPosition(pos);

            if(pos.equals(this.game.getEM().getPlayerPosition()))
                layers[1] = r.getDrawable(R.drawable.pm, null);
            else if(entity != null)
            {
                imageView.setTag(entity.type);

                String sprite = entity.type.getDrawableName();
                drawableId = this.context.getResources().getIdentifier(sprite, "drawable", this.context.getPackageName());

                if( drawableId == 0) // not found
                    layers[1] = r.getDrawable(R.drawable.que, null); // que is default unknown
                else
                    layers[1] = r.getDrawable(drawableId, null);



            }
            else
            {
                layers[1] = new ColorDrawable(Color.TRANSPARENT);
            }


            LayerDrawable layerDrawable = new LayerDrawable(layers);

            imageView.setImageDrawable(layerDrawable);
        }

        imageView.setAdjustViewBounds(true);
        this.items.put(Integer.valueOf(position), imageView);
        return imageView;
    }

}
