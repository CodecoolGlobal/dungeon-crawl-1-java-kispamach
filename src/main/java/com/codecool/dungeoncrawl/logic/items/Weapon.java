package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;

public class Weapon extends Item {

    private int attack = 5;

    public Weapon(Cell cell, String name) {
        super(cell, name);
    }

    @Override
    public String getTileName() {
        return "weapon";
    }
}
