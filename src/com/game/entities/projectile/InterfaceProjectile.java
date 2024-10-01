package com.game.entities.projectile;

import com.game.entities.Enemies.InterfaceEnemy;

import java.util.LinkedList;

public interface InterfaceProjectile {
    void isCollidingPlayerProjectile (LinkedList<InterfaceEnemy> entityList, long currentTime);
}
