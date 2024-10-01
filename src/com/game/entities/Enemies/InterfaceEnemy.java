package com.game.entities.Enemies;

import com.game.entities.GameObjects;

public interface InterfaceEnemy extends GameObjects {

    void draw(long currentTime);

    double getEnemy_V();

    void setEnemy_V(double enemy_V);

    double getEnemy_angle();

    void setEnemy_angle(double enemy_angle);

    double getEnemy_RV();

    void setEnemy_RV(double enemy_RV);

    double getEntity_explosion_end();

    double getEntity_explosion_start();

    void setEntity_explosion_start(long currentTime);

    void setEntity_explosion_end(long currentTime);

    double getEnemy_nextShoot();

    void setEnemy_nextShoot(double enemy1_nextShoot);
}
