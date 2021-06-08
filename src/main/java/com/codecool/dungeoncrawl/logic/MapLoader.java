package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.actors.Guard;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.actors.Alien;
import com.codecool.dungeoncrawl.logic.items.Gear;
import com.codecool.dungeoncrawl.logic.items.Key;
import com.codecool.dungeoncrawl.logic.items.Potion;
import com.codecool.dungeoncrawl.logic.items.Weapon;

import java.io.InputStream;
import java.util.Scanner;

public class MapLoader {
    public static GameMap loadMap() {
        InputStream is = MapLoader.class.getResourceAsStream("/map.txt");
        Scanner scanner = new Scanner(is);
        int width = scanner.nextInt();
        int height = scanner.nextInt();

        scanner.nextLine(); // empty line

        GameMap map = new GameMap(width, height, CellType.EMPTY);
        for (int y = 0; y < height; y++) {
            String line = scanner.nextLine();
            for (int x = 0; x < width; x++) {
                if (x < line.length()) {
                    Cell cell = map.getCell(x, y);
                    switch (line.charAt(x)) {
                        case ' ':
                            cell.setType(CellType.EMPTY);
                            break;
                        case '#':
                            cell.setType(CellType.WALL);
                            break;
                        case 'd':
                            cell.setType(CellType.DOOR);
                            break;
                        case '.':
                            cell.setType(CellType.FLOOR);
                            break;
                        case 's':
                            cell.setType(CellType.FLOOR);
                            new Alien(cell);
                            break;
                        case 'g':
                            cell.setType(CellType.FLOOR);
                            new Guard(cell);
                            break;
                        case 'k':
                            cell.setType(CellType.KEY);
                            new Key(cell, "Level 1 key");
                            break;
                        case 'a':
                            cell.setType(CellType.GEAR);
                            new Gear(cell, "Scaphander");
                            break;
                        case 'w':
                            cell.setType(CellType.WEAPON);
                            new Weapon(cell, "Phasers");
                            break;
                        case 'p':
                            cell.setType(CellType.POTION);
                            new Potion(cell, "Medic kit");
                            break;
                        case '@':
                            cell.setType(CellType.FLOOR);
                            map.setPlayer(new Player(cell));
                            break;
                        default:
                            throw new RuntimeException("Unrecognized character: '" + line.charAt(x) + "'");
                    }
                }
            }
        }
        return map;
    }

}
