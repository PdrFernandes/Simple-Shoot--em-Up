package com.game.entities.Enemies;

import com.game.core.gameLib.GameLib;
import com.game.entities.Entity;

import java.awt.*;

class Enemy2 extends Entity implements InterfaceEnemy{ // coordenadas y
    private double enemy2_V;					// velocidades
    private double enemy2_angle;				// ângulos (indicam direção do movimento)
    private double enemy2_RV;					// velocidades de rotação


    public Enemy2(double enemy2_spawnX) {
        super(ACTIVE, enemy2_spawnX, -10.0, 0, 0, 12.0, 0, 0);

        this.enemy2_V = 0.42;
        this.enemy2_angle = (3 * Math.PI) / 2;
        this.enemy2_RV = 0.0;

    }

    public void draw(long currentTime){
        if(entity_state == EXPLODING){

            double alpha = (currentTime - entity_explosion_start) / (entity_explosion_end - entity_explosion_start);
            GameLib.drawExplosion(entity_X, entity_Y, alpha);
        }

        if(entity_state == ACTIVE){

            GameLib.setColor(Color.MAGENTA);
            GameLib.drawDiamond(entity_X, entity_Y, entity_radius);
        }
    }

    public double getEnemy_V() {
        return enemy2_V;
    }

    public void setEnemy_V(double enemy_V) {
        this.enemy2_V = enemy_V;
    }

    public double getEnemy_angle() {
        return enemy2_angle;
    }

    public void setEnemy_angle(double enemy2_angle) {
        this.enemy2_angle = enemy2_angle;
    }

    public double getEnemy_RV() {
        return enemy2_RV;
    }

    public void setEnemy_RV(double enemy2_RV) {
        this.enemy2_RV = enemy2_RV;
    }

    @Override
    public void setEntity_explosion_start(long currentTime) {
    }

    @Override
    public void setEntity_explosion_end(long l) {
    }

    @Override
    public double getEnemy_nextShoot() {
        return 0;
    }

    @Override
    public void setEnemy_nextShoot(double enemy1_nextShoot) {
    }
}


