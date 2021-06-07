package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;

public class Weapons extends Items{

    private int attack = 5;

    public Weapons(Cell cell, String name) {
        super(cell, name);
    }

    @Override
    public String getTileName() {
        return "weapon";
    }
}
