package fall18project.gamecentre.sliding_tiles;

import android.support.annotation.NonNull;

import java.io.Serializable;

/**
 * A Tile in a sliding tiles puzzle.
 */
public class Tile implements Comparable<Tile>, Serializable {

    private static int[] tile_images;
    /**
     * The background id to find the tile image.
     */
    private int background;
    /**
     * The unique id.
     */
    private int id;

    /**
     * A Tile with id and background. The background may not have a corresponding image.
     *
     * @param id         the id
     * @param background the background
     */
    public Tile(int id, int background) {
        this.id = id;
        this.background = background;
    }

    /**
     * A tile with a background id; look up and set the id.
     *
     * @param backgroundId
     */
    public Tile(int backgroundId, boolean last) {
        setTile_images();
        id = backgroundId + 1;
        if (last) {
            background = tile_images[48];
        } else {
            background = tile_images[backgroundId];
        }
    }

    private static void setTile_images() {
        tile_images = new int[49];
        for (int i = 0; i < 49; i++) {
            tile_images[i] = TileImage.getTileimage_num(i);
        }
    }

    /**
     * Return the background id.
     *
     * @return the background id
     */
    public int getBackground() {
        return background;
    }

    /**
     * Return the tile id.
     *
     * @return the tile id
     */
    public int getId() {
        return id;
    }

    @Override
    public int compareTo(@NonNull Tile o) {
        return o.id - this.id;
    }
}
