package com.codecool.dungeoncrawl.logic;

public enum CellType {
    EMPTY("empty"),
    DOOR("door"),
    FLOOR("floor"),
    KEY("key"),
    WEAPON("weapon"),
    GEAR("gear"),
    POTION("potion"),
    WALL("wall");

    private final String tileName;

    CellType(String tileName) {
        this.tileName = tileName;
    }

    public String getTileName() {
        return tileName;
    }
}
