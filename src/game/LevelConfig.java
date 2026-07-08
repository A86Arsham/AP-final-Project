/**
 * data structure holding configuration for each level
 */
package game;

import main.*;
import ui.*;
import game.*;
import entities.*;
import database.*;
import audio.*;

public class LevelConfig {
    public String[] enemyTypes;
    public int cellHealth;
    public double gridSpeed;
    public int verticalMove;
    public int eggCooldown;
    public boolean isBossLevel;

    public LevelConfig(String[] enemyTypes, int cellHealth, double gridSpeed, int verticalMove, int eggCooldown, boolean isBossLevel){
        this.enemyTypes = enemyTypes;
        this.cellHealth = cellHealth;
        this.gridSpeed = gridSpeed;
        this.verticalMove = verticalMove;
        this.eggCooldown = eggCooldown;
        this.isBossLevel = isBossLevel;
    }
}
