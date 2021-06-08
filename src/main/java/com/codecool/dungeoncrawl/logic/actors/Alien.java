package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;

public class Alien extends Actor {



    public Alien(Cell cell) {
        super(cell);
        maxHealth -= 3;
        health -= 3;
        strength += 1;
    }

    @Override
    public String getTileName() {
        return "skeleton";
    }
}
