package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.logic.Drawable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Tiles {
    public static int TILE_WIDTH = 32;

    private static Image tileset = new Image("/tiles2.png", 543 * 2, 543 * 2, true, false);
    private static Map<String, Tile> tileMap = new HashMap<>();
    public static class Tile {
        public final int x, y, w, h;
        Tile(int i, int j) {
            x = i * (TILE_WIDTH + 2);
            y = j * (TILE_WIDTH + 2);
            w = TILE_WIDTH;
            h = TILE_WIDTH;
        }
    }

    static {
        Random random = new Random();
        tileMap.put("empty", new Tile(random.nextInt(3) + 1, 0));
        tileMap.put("wall", new Tile(7, 18));
        tileMap.put("floor", new Tile(0, 0));
        tileMap.put("player", new Tile(28, 9));
        tileMap.put("skeleton", new Tile(31, 6));
        tileMap.put("guard", new Tile(24, 3));
        tileMap.put("key", new Tile(17, 23));
        tileMap.put("gear", new Tile(5, 22));
        tileMap.put("weapon", new Tile(6, 31));
        tileMap.put("potion", new Tile(27, 23));
        tileMap.put("door", new Tile(9, 11));
    }

    public static void drawTile(GraphicsContext context, Drawable d, int x, int y) {
        Tile tile = tileMap.get(d.getTileName());
        context.drawImage(tileset, tile.x, tile.y, tile.w, tile.h,
                x * TILE_WIDTH, y * TILE_WIDTH, TILE_WIDTH, TILE_WIDTH);
    }
}
