package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;

public class Potion extends Item {

    private int heal = 4;

    public Potion(Cell cell, String name) {
        super(cell, name);
    }

    public int getHeal() {
        return heal;
    }

    @Override
    public String getTileName() {
        return "potion";
    }
}
