package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.Drawable;
import com.codecool.dungeoncrawl.logic.items.Gear;
import com.codecool.dungeoncrawl.logic.items.Item;
import com.codecool.dungeoncrawl.logic.items.Potion;
import com.codecool.dungeoncrawl.logic.items.Weapon;

import java.util.ArrayList;

public abstract class Actor implements Drawable {
    private String name;
    private Cell cell;
    protected int maxHealth = 10;
    protected int health = 10;
    protected int strength = 6;
    private ArrayList<Item> inventory = new ArrayList<>();

    public Actor(Cell cell) {
        this.cell = cell;
        this.cell.setActor(this);
    }

    public void move(int dx, int dy) {

        Cell nextCell = cell.getNeighbor(dx, dy);
        if (nextCell.isEnemy() || nextCell.isPlayer()) attack(nextCell.getActor(), nextCell);
        if (cell.isPlayer() && nextCell.isAvailable() && !nextCell.isEnemy()) {
            cell.setActor(null);
            nextCell.setActor(this);
            cell = nextCell;
        } else if (nextCell.isAvailable() && !nextCell.isEnemy() && !nextCell.isPlayer())   {
            cell.setActor(null);
            nextCell.setActor(this);
            cell = nextCell;
        }
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getStrength() {
        return strength;
    }

    public Cell getCell() {
        return cell;
    }

    public int getX() {
        return cell.getX();
    }

    public int getY() {
        return cell.getY();
    }

    public ArrayList<Item> getInventory() {
        return inventory;
    }

    public void pickUpItem(Item item) {
        this.inventory.add(item);
        if (item instanceof Gear) {
            maxHealth += ((Gear) item).getArmor();
            health += ((Gear) item).getArmor();
        } else if (item instanceof Weapon) {
            strength += ((Weapon) item).getAttack();
        } else if (item instanceof Potion) {
            health += ((Potion) item).getHeal();
            if (health >= maxHealth) health = maxHealth;
        }
        item.getCell().setType(CellType.FLOOR);
    }

    public String itemInInventory () {
        StringBuilder sb = new StringBuilder();
        for (Item item : this.inventory) {
            sb.append(item.getName()).append(",\n");
        }
        return sb.toString();
    }

    public void attack(Actor actor, Cell cell) {
        while (true) {
            actor.setHealth(actor.getHealth() - this.strength);
            System.out.println(this.getClass().getSimpleName() + this.health + " + " + actor.getHealth() + actor.getClass().getSimpleName());
            if (actor.getHealth() < 0 || this.health < 0) break;
            this.health = this.health - actor.getStrength();
            System.out.println(this.getClass().getSimpleName() + this.health + " + " + actor.getHealth() + actor.getClass().getSimpleName());
            if (actor.getHealth() < 0 || this.health < 0) break;
        }
        if (actor.getHealth() <= 0)  {
            cell.setActor(null);
            System.out.println(actor.getClass().getSimpleName() + " halt");
        } else if (this.health <= 0 ){
            cell.setActor(null);
            System.out.println(this.getClass().getSimpleName() + " halt");
        }
    }
}
