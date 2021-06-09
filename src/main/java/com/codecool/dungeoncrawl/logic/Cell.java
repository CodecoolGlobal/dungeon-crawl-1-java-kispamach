package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.actors.Actor;
import com.codecool.dungeoncrawl.logic.actors.Guard;
import com.codecool.dungeoncrawl.logic.actors.Alien;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.items.Item;

public class Cell implements Drawable {
    private CellType type;
    private Actor actor;
    private Item item;
    private GameMap gameMap;
    private int x, y;

    Cell(GameMap gameMap, int x, int y, CellType type) {
        this.gameMap = gameMap;
        this.x = x;
        this.y = y;
        this.type = type;
    }

    public CellType getType() {
        return type;
    }

    public void setType(CellType type) {
        this.type = type;
    }

    public void setActor(Actor actor) {
        this.actor = actor;
    }

    public Actor getActor() {
        return actor;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Cell getNeighbor(int dx, int dy) {
        return gameMap.getCell(x + dx, y + dy);
    }

    @Override
    public String getTileName() {
        return type.getTileName();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isAvailable() {
        return this.getType().equals(CellType.FLOOR) || this.isItem();
    }

    public boolean isEnemy() {
        return this.actor instanceof Alien || this.actor instanceof Guard;
    }

    public boolean isPlayer() {
        return this.actor instanceof Player;
    }

    public boolean isItem() {
        return this.type.equals(CellType.GEAR) || this.type.equals(CellType.KEY)
                || this.type.equals(CellType.WEAPON) || this.type.equals(CellType.POTION);
    }

    public boolean isDoor() {
        return this.type.equals(CellType.DOOR);
    }
}
