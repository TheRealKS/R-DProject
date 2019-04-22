package com.koens.structure;

import com.koens.struct.*;

import java.util.LinkedList;
import java.util.HashMap;
import java.util.Map;

import android.widget.GridView;
import android.view.ViewGroup;

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
    enum Sprite
    {
        UNKNOWN("placeholder"),
        FLAGTILE("key"),
        TILE("wall_"),
        SLOT("arrow");

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
            if (s.name().equals(name))
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
        Tile[][] currentMatrix = this.game.getBoard().getConfiguration();

        Map<Position, Sprite> map = new HashMap<Position, Sprite>();

        for(int p = 0; p < this.n + 2; p++) {
            for (int q = 0; q < this.m + 2; q++)
            {
                Position pos    = new Position(p, q);
                Sprite s        = findSprite(currentMatrix[p][q]);
                map.put(pos, s);
            }
        }
        this.lastMap = map;

        return map;
    }


    /**
     * Determine suffix for SpriteSet.
     * @param pos
     * @return
     */
    private String getSuffix(Position pos)
    {
        StringBuilder suffix = new StringBuilder();

        Sprite N = this.lastMap.get(pos.getPosAfterMove(Direction.NORTH));
        Sprite E = this.lastMap.get(pos.getPosAfterMove(Direction.EAST ));
        Sprite S = this.lastMap.get(pos.getPosAfterMove(Direction.SOUTH));
        Sprite W = this.lastMap.get(pos.getPosAfterMove(Direction.WEST ));

        boolean n,e,s,w,h,v; // north east south west horizontal vertical
        n = (N == Sprite.TILE);
        e = (E == Sprite.TILE);
        s = (S == Sprite.TILE);
        w = (W == Sprite.TILE);

        if(!n && !e && !s && !w) // no surrounding walls, just tile then
            return "000";

        h = (e && w);
        v = (s && n);

        if(h) { suffix.append('h'); }
        if(!h && (e || w)) // not horizontal but e xor w, then
        {
            if(e)
                suffix.append('e');
            else
                suffix.append('w');
        }

        if(v) { suffix.append('v'); }
        if(!v && (s || n))
        {
            if(s)
                suffix.append('s');
            else
                suffix.append('n');
        }

        if(suffix.length() == 1) // then only one tile found, so add itself to comply with filenames
            suffix.append(suffix.charAt(0));


        return suffix.toString();
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
            return Sprite.UNKNOWN;

        String className    = t.getClass().getSimpleName(); // Classname of current child of Tile
        String EnumName     = className.toUpperCase(); // The value that should be in enum Sprite

        if(spriteExists(EnumName))
            return Sprite.valueOf(EnumName);

        System.out.println("Sprite for this enum not found: " + EnumName);
        return Sprite.UNKNOWN;
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
        this.gridView.setNumColumns(m + 2); // m + 2 is width @todo check if Im right

        // dynamic height of gridView
        ViewGroup.LayoutParams params = this.gridView.getLayoutParams();
        params.height = this.m * this.gridView.getColumnWidth(); //@todo check if not n.
        this.gridView.setLayoutParams(params);


        return true;
    }



}
