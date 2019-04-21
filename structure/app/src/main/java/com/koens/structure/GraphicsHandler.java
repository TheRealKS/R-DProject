package com.koens.structure;

import com.koens.struct.*;

import java.util.LinkedList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class GraphicsHandler
{
    private Game game; // Reference to current game object
    private int n, m;  // Dimension of game

    /**
     * used in {@link GraphicsHandler#spriteExists}, such that it need not assert
     * things every time.
     */
    private LinkedList<String> spriteExistsHistory = new LinkedList<String>();


    /**
     * Simple constructor.
     * @param g game
     */
    public GraphicsHandler(Game g)
    {
        this.game = g;
        this.n = g.getBoard().getN();
        this.m = g.getBoard().getM();
    }


    /**
     * In Sprite, each element is uCase(getClass()) of
     * some child of {@link Tile}. With {@link Sprite#getSpriteId()}
     * the associated sprite can be found.
     */
    enum Sprite {
        UNKNOWN("PLACEHOLDER"),
        PLAYER("Test");

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
        if( spriteExistsHistory.contains(name) ) return true;

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
        return map;
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

        String className = t.getClass().getSimpleName(); // Classname of current child of Tile
        String EnumName = className.toUpperCase(); // The value that should be in enum Sprite

        if(spriteExists(EnumName))
            return Sprite.valueOf(EnumName);
        else
            return Sprite.valueOf("UNKNOWN");
    }



}
