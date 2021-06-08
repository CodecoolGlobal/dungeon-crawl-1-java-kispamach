package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;

public class Weapon extends Item {

    private int attack = 5;

    public Weapon(Cell cell, String name) {
        super(cell, name);
    }

    public int getAttack() {
        return attack;
    }

    @Override
    public String getTileName() {
        return "weapon";
    }
}
