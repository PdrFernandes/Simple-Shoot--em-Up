package com.game.entities.Enemies;

import com.game.entities.player.IntafacePlayer;
import com.game.entities.projectile.InterfaceProjectile;
import com.game.entities.projectile.Projectile;

import java.util.LinkedList;

public interface InterfaceEnemiesManager {
    void refreshEnemiesProjectile(long delta);

    void launchNewEnemies(long currentTime);

    void drawAllEnemies(long currentTime);

    void isCollidingPlayerProjectile(InterfaceProjectile p_element, long currentTime);

    void isCollidingPlayer(IntafacePlayer player, long currentTime);
}
