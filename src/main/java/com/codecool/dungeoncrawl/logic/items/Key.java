package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;

public class Key extends Items{

    public Key(Cell cell, String name) {
        super(cell, name);
    }

    @Override
    public String getTileName() {
        return "key";
    }
}
