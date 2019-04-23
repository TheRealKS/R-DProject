package com.koens.structure;

import com.koens.struct.*;

import java.util.LinkedList;
import java.util.HashMap;
import java.util.Map;

import android.widget.GridView;
import android.view.ViewGroup;

import android.content.Context;

/**
 * Updates gridView according to game state.
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
        FLAGTILE("wall_"),
        FLOOR("wall_000"),
        TILE("wall_"),
        SLOT("placeholder");

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

        for(int x = 0; x < this.n + 2; x++)
        {
            for (int y = 0; y < this.m + 2; y++)
            {
                Position pos    = new Position(x, y);
                Sprite s        = findSprite(currentMatrix[x][y]);
                String suffix = "";
                if(s == Sprite.TILE)
                    suffix = this.getSuffix(pos, currentMatrix);
                map.put(pos, new Tuple<>(s, suffix));
            }
        }

        map.put(null, new Tuple<>(Sprite.UNKNOWN, null)); // if null, then unknown
        this.lastMap = map;

        return map;
    }


    enum TILE
    {
        VOID, WALL, FLOOR
    }

    private TILE tileType(Position cPOS, Direction dir, Tile[][] matrix)
    {
        return tileType(cPOS, new Direction[]{dir}, matrix);
    }
    private TILE tileType(Position cPOS, Direction dir[], Tile[][] matrix)
    {
        Position P = cPOS;
        for(Direction d : dir)
            P = P.getPosAfterMove(d, this.n + 2, this.m + 2);

        if(P != null) {
            if(matrix[P.getX()][P.getY()].canBeMovedInto())
                return TILE.FLOOR;
            else
                return TILE.WALL;
        }
        return TILE.VOID;
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

        TILE N = tileType(pos, Direction.NORTH, matrix);
        TILE E = tileType(pos, Direction.EAST, matrix);
        TILE S = tileType(pos, Direction.SOUTH, matrix);
        TILE W = tileType(pos, Direction.WEST, matrix);

        boolean h = (W == TILE.WALL && E == TILE.WALL);
        boolean v = (N == TILE.WALL && S == TILE.WALL);


        if(h)
        {
            if(v) {
                return "hv";
            } else if(S == TILE.WALL) {
                return "hs";
            } else {
                return "h";
            }
        }
        else if (v)
        {
            suffix.append("v");
            if(E == TILE.WALL || W == TILE.WALL)
            {
               if(E == TILE.WALL)
                   suffix.append('e');
               else
                   suffix.append('w');
            }
            else
            {
                if(W == TILE.FLOOR && E == TILE.FLOOR)
                    suffix.append('2');
                else if(W == TILE.VOID && E == TILE.FLOOR )
                    suffix.append('1');
                else if(E == TILE.VOID && W == TILE.FLOOR )
                    suffix.append('0');
            }
            return suffix.toString();
        }
        else
        {
            if(N == TILE.WALL) suffix.append("n");
            if(E == TILE.WALL) suffix.append("e");
            if(S == TILE.WALL) suffix.append("s");
            if(W == TILE.WALL) suffix.append("w");
            String cur = suffix.toString();

            if(cur.equals("ne") || cur.equals("es") || cur.equals("sw") || cur.equals("nw"))
            {
                if(cur.equals("ne") && (tileType(pos, new Direction[]{Direction.NORTH, Direction.EAST}, matrix) == TILE.WALL))
                    return "000";
                if(cur.equals("es") && (tileType(pos, new Direction[]{Direction.EAST, Direction.SOUTH}, matrix) == TILE.WALL))
                    return "000";
                if(cur.equals("sw") && (tileType(pos, new Direction[]{Direction.SOUTH, Direction.WEST}, matrix) == TILE.WALL))
                    return "000";
                if(cur.equals("nw") && (tileType(pos, new Direction[]{Direction.NORTH, Direction.WEST}, matrix) == TILE.WALL))
                    return "000";
            }
        }


        if(suffix.length() < 2)
            return "000";

        if(W == TILE.FLOOR || E == TILE.FLOOR)
            suffix.append('0');

        System.out.println(suffix);
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
        if(t.canBeMovedInto())
            return Sprite.FLOOR;
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

        this.readGameMatrix(); // fetch newest state of game into map;

        System.out.println(this.toString());
        MatrixAdapter MA = new MatrixAdapter(this.context, this.lastMap, this.n, this.m, this.game);
        this.gridView.setAdapter(MA);
        this.gridView.setNumColumns(n + 2); // m + 2 is width @todo check if Im right

        // dynamic height of gridView
        ViewGroup.LayoutParams params = this.gridView.getLayoutParams();
        params.height = (this.m + 2) * this.gridView.getColumnWidth(); //@todo check if not n.
        this.gridView.setLayoutParams(params);


        return true;
    }

    public String toString()
    {
        StringBuilder str = new StringBuilder();
        for(int i = 0 ; i < this.m + 2; i++)
        {
            for (int j = 0; j < this.n + 2; j++)
            {
                final Position pos = new Position(j, i);
                str.append(pos + this.lastMap.get(pos).toString() );
            }
            str.append("\n");
        }
        return str.toString();
    }



}
