package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;

public class Gear extends Item {

    private int armor = 5;

    public Gear(Cell cell, String name) {
        super(cell, name);
    }

    @Override
    public String getTileName() {
        return "gear";
    }
}
