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
    private Map<Position, Tuple<Sprite, String>> lastMap;

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

        this.lastMap = new HashMap<>();
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

        public String getSpriteId(){
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
     * @return HashMap (Position, Tuple(Sprite, String))
     */
    private Map<Position, Tuple<Sprite, String>> readGameMatrix()
    {
        Tile[][] currentMatrix = this.game.getBoard().getConfiguration();

        Map<Position, Tuple<Sprite, String>> map = new HashMap<>();

        for(int p = 0; p < this.n + 2; p++) {
            for (int q = 0; q < this.m + 2; q++)
            {
                Position pos    = new Position(p, q);
                Sprite s        = findSprite(currentMatrix[p][q]);
                System.out.println(s.getSpriteId());
                map.put(pos, new Tuple<Sprite, String>(s, this.getSuffix(pos, currentMatrix)));
            }
        }

        map.put(null, new Tuple<Sprite, String>(Sprite.UNKNOWN, null)); // if null, than unknown
        this.lastMap = map;

        return map;
    }


    /**
     * Determine suffix for SpriteSet.
     * @param pos suffix for tile at position
     * @param matrix current game configuration
     * @return suffix
     */
    public String getSuffix(Position pos, Tile[][] matrix)
    {
        StringBuilder suffix = new StringBuilder();

        Position N = pos.getPosAfterMove(Direction.NORTH, this.m, this.n);
        Position E = pos.getPosAfterMove(Direction.EAST , this.m, this.n);
        Position S = pos.getPosAfterMove(Direction.SOUTH, this.m, this.n);
        Position W = pos.getPosAfterMove(Direction.WEST , this.m, this.n);

        boolean wallN, wallE, wallS, wallW, h, v; // north east south west horizontal vertical

        if(N != null) { wallN = !(matrix[N.getX()][N.getY()].canBeMovedInto()); }
        else { wallN = true; }

        if(E != null) { wallE = !(matrix[E.getX()][E.getY()].canBeMovedInto()); }
        else { wallE = true; }

        if(S != null) { wallS = !(matrix[S.getX()][S.getY()].canBeMovedInto()); }
        else { wallS = true; }

        if(W != null) { wallW = !(matrix[W.getX()][W.getY()].canBeMovedInto()); }
        else { wallW = true; }

        if( !wallN && !wallE && !wallS && !wallW ) // no surrounding walls, just tile then
            return "000";

        h = wallE && wallW;
        v = wallN && wallS;

        if(h) { suffix.append('h'); }
        if(v) { suffix.append('v'); }

        if(!v && (wallS || wallN))
        {
            if(wallS)
                suffix.append('s');
            else
                suffix.append('n');
        }

        if(!h && (wallE || wallW)) // not horizontal but e xor w, then
        {
            if(wallE)
                suffix.append('e');
            else
                suffix.append('w');
        }


        if(suffix.length() == 1) // then only one tile found, so add itself to comply with filenames
            suffix.append(suffix.charAt(0));

        suffix.append('1');
        System.out.println("wall_"+suffix.toString());
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
        params.height = (this.m + 2) * this.gridView.getColumnWidth(); //@todo check if not n.
        this.gridView.setLayoutParams(params);


        return true;
    }



}
