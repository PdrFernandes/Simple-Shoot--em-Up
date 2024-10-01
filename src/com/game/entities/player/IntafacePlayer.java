package com.game.entities.player;

import com.game.entities.GameObjects;

import java.util.LinkedList;

public interface IntafacePlayer extends GameObjects {
     void resetPlayerLife();

     int getPlayer_life();

     void incrementPlayer_life();

     void decrementPlayer_life();

     int getPlayer_maxLife();

     boolean isPlayerColided();

     void setPlayerColided(boolean playerColided);

     double getIvunerable_end();

     void setIvunerable_end(double ivunerable_end);

     double getIvunerable_start();

     void setIvunerable_start(double ivunerable_start);

     boolean isShilded();

     void setShilded(boolean shilded);

     void playerIsColliding(LinkedList<? extends GameObjects> entityList, long currentTime);
}
