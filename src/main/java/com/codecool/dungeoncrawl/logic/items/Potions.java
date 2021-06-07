package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;

public class Potions extends Items{

    private int health = 4;

    public Potions(Cell cell, String name) {
        super(cell, name);
    }

    @Override
    public String getTileName() {
        return "potion";
    }
}
