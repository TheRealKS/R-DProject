package com.koens.structure;

import com.koens.struct.*;

import java.util.LinkedList;
import java.util.HashMap;
import java.util.Map;

import android.widget.GridView;
import android.content.Context;

/**
 * @author Karim Abdulahi
 */
public class GraphicsHandler
{
    private Game game; // Reference to current game object
    private int n, m;  // Dimension of game

    /**
     * holds latest map from {@link #readGameMatrix()}
     */
    private Map<Position, Sprite> lastMap;

    /**
     * used in {@link #spriteExists}, such that it need not assert
     * things every time.
     */
    private LinkedList<String> spriteExistsHistory = new LinkedList<String>();

    /**
     * The GridView this class operates on
     */
    private GridView gridView;

    private Context context;


    /**
     * Simple constructor.
     * @param context Context
     * @param g game
     * @param gridView view that class operates on
     */
    public GraphicsHandler(Context context, Game g, GridView gridView)
    {
        this.game = g;
        this.n = g.getBoard().getN();
        this.m = g.getBoard().getM();
        this.gridView = gridView;
        this.context = context;

        this.readGameMatrix();
    }


    /**
     * In Sprite, each element is uCase(getClass()) of
     * some child of {@link Tile}. With {@link Sprite#getSpriteId()}
     * the associated sprite can be found.
     */
    enum Sprite {
        UNKNOWN("PLACEHOLDER"),
        WALL("wall_000"),
        PLAYER("arrow"),
        BOULDER("ball"),
        KEY("key");

        private String spriteId;

        Sprite(String spriteId) {
            this.spriteId = spriteId;
        }

        public String getSpriteId() {
            return spriteId;
        }
    }


    /**
     * Asserts if this element exists in enum Sprite
     *
     * @param name Name of Sprite
     * @return bool, true if exists.
     */
    private boolean spriteExists(String name)
    {
        if( spriteExistsHistory.contains(name) )
            return true; // been asserted in past

        for(Sprite s : Sprite.values())
        {
            if (s.toString().equals(name))
            {
                spriteExistsHistory.add(name);
                return true;
            }
        }
        return false;
    }

    /**
     * Read gameMatrix, for each {@link Tile} decide
     * what sprite must be used at that position
     * @return HashMap (Position, Sprite)
     */
    private Map<Position, Sprite> readGameMatrix()
    {
        final Tile[][] currentMatrix = this.game.getBoard().getConfiguration();
        Map<Position, Sprite> map = new HashMap<Position, Sprite>();

        for(int n = 0; n < this.n; n++) {
            for (int m = 0; m < this.m; m++)
            {
                final Position p    = new Position(n, m);
                final Sprite s      = findSprite(currentMatrix[n][m]);
                map.put(p, s);
            }
        }
        return (this.lastMap = map);
    }

    /**
     * For any child of {@link Tile} this method will try to
     * find a sprite that fits.
     *
     * @param t Child of {@link Tile}
     * @return  an element of {@link Sprite} that contains the information
     *          needed to retrieve the sprite. If element does not exist,
     *          {@link Sprite#UNKNOWN} is returned, which is a placeholder.
     */
    private Sprite findSprite(Tile t)
    {
        if(t.getClass().getSimpleName().equals("Tile")) // @todo test behavior
            return Sprite.valueOf("UNKNOWN");

        String className    = t.getClass().getSimpleName(); // Classname of current child of Tile
        String EnumName     = className.toUpperCase(); // The value that should be in enum Sprite

        if(spriteExists(EnumName))
            return Sprite.valueOf(EnumName);

        return Sprite.valueOf("UNKNOWN");
    }

    /**
     * Will update the grid according to current state of game.
     *
     * @return True on success, false in all other cases.
     */
    public boolean updateGridView()
    {
        if(this.gridView == null)
            return false;

        this.readGameMatrix(); // fetch newest state of game into ::game;

        MatrixAdapter MA = new MatrixAdapter(this.context, this.lastMap, this.n, this.m);
        this.gridView.setAdapter(MA);


        return true;
    }



}
