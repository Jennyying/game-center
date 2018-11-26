package fall18project.gamecentre;
import java.util.*;
import java.math.*;

/**
 *Shuffles a given arraylist of tiles in such a way that the game is always winnable.
 */
public class BoardShuffler {
    /**
     * Array containing tiles
     */
    private Tile[] tiles;
    /**
     * Array containing random numbers
     */
    private int[] rand;
    /**
     * Dimesnion of board
     */
    private int dimension;

     /**
     *Initialises class tiles variable, and dimension with inputed parameters. Creates a
     *list of random numbers.
     *
     *@param dimension - the dimension of the board
     *@param numTiles - the number of tiles on the board
     *@param tiles - an arraylist of the tiles on the board
     */
    public BoardShuffler(ArrayList<Tile> tiles, int numTiles, int dimension)
    {
        this.tiles = new Tile[numTiles];
        this.dimension = dimension;
        this.rand = new int[100];
        for(int i = 0;i< 100 ;i++)
        {
            rand[i] = (int)Math.round(Math.random());
        }
        for(int i = 0; i < this.tiles.length; i++)
        {
            this.tiles[i] = tiles.get(i);
        }
    }

    private ArrayList<Tile> shuffle()
    {
        for(int i = 0; i < 100; i++)
        {
            if(rand[i] == 0)
            {
                int temp = (int)Math.round(Math.random());
                int tempindex = tiles.length;
                for(int j = 0; j < tiles.length; j++)
                {
                    if(tiles[j].getId() == tiles.length)
                    {
                        tempindex = j;
                    }
                }
                if(temp == 0)
                {
                    if(tempindex > 0)
                    {
                        Tile temptile = tiles[tempindex - 1];
                        tiles[tempindex - 1] = tiles[tempindex];
                        tiles[tempindex] = temptile;
                    }
                }
                else
                {
                    if(tempindex < tiles.length - 1)
                    {
                        Tile temptile = tiles[tempindex + 1];
                        tiles[tempindex + 1] = tiles[tempindex];
                        tiles[tempindex] = temptile;
                    }
                }
            }
            else
            {
                int temp = (int)Math.round(Math.random());
                int tempindex = tiles.length;
                for(int j = 0; j < tiles.length; j++)
                {
                    if(tiles[j].getId() == tiles.length)
                    {
                        tempindex = j;
                    }
                }
                if(temp == 0)
                {
                    if(tempindex > dimension - 1)
                    {
                        Tile temptile = tiles[tempindex - dimension];
                        tiles[tempindex - dimension] = tiles[tempindex];
                        tiles[tempindex] = temptile;
                    }
                }
                else
                {
                    if(tempindex < tiles.length - dimension)
                    {
                        Tile temptile = tiles[tempindex + dimension];
                        tiles[tempindex + dimension] = tiles[tempindex];
                        tiles[tempindex] = temptile;
                    }
                }
            }
        }
        ArrayList<Tile> tt = new ArrayList<>();
        for(int i = 0; i < tiles.length; i++)
        {
            tt.add(tiles[i]);
        }
        return tt;
    }
}
