package com.game.entities.powerUps;

import com.game.entities.player.IntafacePlayer;

public interface InterfacePowerUpsManager {
    void refreshPouwerUps(long delta);

    void launchNewPowerUps (long currentTime);

    void drawAllPowerUps ();

    void powerUpIsColliding(long currentTime, IntafacePlayer player);
}
