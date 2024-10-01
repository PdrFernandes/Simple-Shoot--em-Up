package com.game.entities.powerUps;

import com.game.entities.GameObjects;
import com.game.entities.player.IntafacePlayer;

interface InterfacePowerUp extends GameObjects {
    void applyPower(IntafacePlayer player);

    void draw();

    double calcForCollision (GameObjects player);

    double getPowerUp_v();
    double getPowerUp_angle();
}
