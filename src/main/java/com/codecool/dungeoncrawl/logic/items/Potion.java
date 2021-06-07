package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;

public class Potion extends Item {

    private int health = 4;

    public Potion(Cell cell, String name) {
        super(cell, name);
    }

    @Override
    public String getTileName() {
        return "potion";
    }
}
