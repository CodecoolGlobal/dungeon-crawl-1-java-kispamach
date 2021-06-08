package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;

public class Guard extends Actor {

    public Guard(Cell cell) {
        super(cell);
        maxHealth -= 2;
        health -= 2;
        strength -= 2;
    }

    @Override
    public String getTileName() {
        return "guard";
    }

}
