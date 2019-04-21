package com.koens.struct;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.DynamicDrawableSpan;
import android.text.style.ImageSpan;

import com.koens.struct.entity.EntityManager;
import com.koens.struct.entity.NonPlayableEntity;
import com.koens.structure.R;

public class DrawBoard {

    private Tile[][] b;
    private Context ctx;
    EntityManager entityList;

    public DrawBoard(Context c, EntityManager mn, Tile[][] conf)
    {
        this.ctx = c;
        this.entityList = mn;
        this.b = conf;
    }

    public String drawGameLayer()
    {
        SpannableStringBuilder sb = new SpannableStringBuilder ();

        for (int i = 0; i < b.length; i++)
        {
            for (int j = 0; j < b[i].length; j++)
            {
                Tile t1 = b[i][j];
                NonPlayableEntity e = entityList.getPickUpAtPosition(t1.getPosition());

                if (entityList.getPlayerPosition(false).equals(t1.getPosition())) {
                    sb.append("x");
                    continue;
                } else if (e != null) {
                    if (!e.isPickedUp()) {
                        sb.append("b");
                    } else {
                        sb.append("0");
                    }
                    continue;
                }
                if (!t1.canBeMovedInto()) {
                    sb.append("0");
                } else {
                    if (t1 instanceof  Slot) {
                        if (((Slot) t1).getOccupied()) {
                            sb.append("d");
                        } else {
                            sb.append("s");
                        }
                    } else {
                        sb.append("0");
                    }
                }
            }
            sb.append(".");
        }

        return sb.toString();
    }

    public String drawBoardLayer()
    {
        SpannableStringBuilder sb = new SpannableStringBuilder ();

        for (int i = 0; i < b.length; i++)
        {
            for (int j = 0; j < b[i].length; j++)
            {
                if(b[i][j].isEmptyTile())
                {
                    sb.append("mm0");
                }
                else if (!b[i][j].canBeMovedInto())
                {
                    boolean N = hasAdjecentWall(i-1, j, b.length, b[i].length);
                    boolean E = hasAdjecentWall(i, j+1, b.length, b[i].length);
                    boolean S = hasAdjecentWall(i+1, j, b.length, b[i].length);
                    boolean W = hasAdjecentWall(i, j-1, b.length, b[i].length);

                    boolean left = hasLeftTile(i, j-1, b.length, b[i].length);
                    boolean right = hasLeftTile(i, j+1, b.length, b[i].length);

                    if(N && E && S && W)
                    {
                        sb.append("hv");
                    }
                    else if(N && E && S)
                    {
                        sb.append("ve");
                    }
                    else if(N && W && S)
                    {
                        sb.append("vw");
                    }
                    else if(E && S && W)
                    {
                        sb.append("hs");
                    }
                    else if(N && E && W)
                    {
                        sb.append("hn");
                    }
                    else if(E && W)
                    {
                        sb.append("ew");
                    }
                    else if(N && S)
                    {
                        sb.append("ns");
                    }
                    else if(N && E)
                    {
                        sb.append("ne");
                    }
                    else if(N && W)
                    {
                        sb.append("nw");
                    }
                    else if(E && S)
                    {
                        sb.append("es");
                    }
                    else if(S && W)
                    {
                        sb.append("sw");
                    }
                    else if(N)
                    {
                        sb.append("nn");
                    }
                    else if(E)
                    {
                        sb.append("ee");
                    }
                    else if(S)
                    {
                        sb.append("ss");
                    }
                    else if(W)
                    {
                        sb.append("ww");
                    }
                    else
                    {
                        sb.append("--");
                    }

                    if(left)
                    {
                        sb.append("0");
                    }
                    else if(right)
                    {
                        sb.append("1");
                    }
                    else if(E)
                    {
                        sb.append("1");
                    }
                    else
                    {
                        sb.append("0");
                    }
                }
                else
                {
                    sb.append("000");
                }
            }
            sb.append("...");
        }

        return sb.toString();
    }

    private boolean hasAdjecentWall(int i, int j, int row, int column)
    {
        if(i >= 0 && i < row && j >= 0 && j < column)
        {
            return !b[i][j].canBeMovedInto() && !b[i][j].isEmptyTile();
        }
        else
        {
            return false;
        }
    }

    private boolean hasLeftTile(int i, int j, int row, int column)
    {
        if(i >= 0 && i < row && j >= 0 && j < column)
        {
            return b[i][j].canBeMovedInto();
        }
        else
        {
            return false;
        }
    }
}
