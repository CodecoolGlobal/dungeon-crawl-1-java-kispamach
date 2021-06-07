package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;

public class Guard extends Actor {

    public Guard(Cell cell) {
        super(cell);
    }

    @Override
    public String getTileName() {
        return "guard";
    }

}
