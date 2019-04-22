package com.koens.structure;

import android.widget.BaseAdapter;

import com.koens.struct.Position;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.content.Context;

import java.util.Map;
import java.lang.Math;

/**
 * This class is used by GridView::setAdapter();
 * https://developer.android.com/reference/android/widget/GridView#setAdapter(android.widget.ListAdapter)
 * it implements baseAdapter.
 * @author Karim Abdulahi
 */
public class MatrixAdapter extends BaseAdapter
{
    private Context context;
    private Map<Position, Tuple<GraphicsHandler.Sprite, String>> data;
    private int n, m;

    public MatrixAdapter(Context context, Map<Position, Tuple<GraphicsHandler.Sprite, String>> data,
                         int n, int m)
    {
        this.context = context;
        this.data = data;
        this.n = n;
        this.m = m;
    }


    private Position int2pos(int position)
    {
        final int h = position % (this.m+2);
        final int v = (int) Math.floor(position / ((double) this.m + 2));
        return new Position(v, h);
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
        return null;
    }

    // GridView will get data from here
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

        if(tuple != null) {
            String sprite = tuple.x.getSpriteId() + tuple.y;
            id = this.context.getResources().getIdentifier(sprite, "drawable", this.context.getPackageName());
        }

        if(id != 0)// only if res exists, setImage...
            imageView.setImageResource(id);
        imageView.setAdjustViewBounds(true);

        return imageView;
    }

}
