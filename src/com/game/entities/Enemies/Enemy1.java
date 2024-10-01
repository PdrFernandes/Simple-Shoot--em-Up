package com.game.entities.Enemies;

import com.game.core.gameLib.GameLib;
import com.game.entities.Entity;

import java.awt.*;

class Enemy1 extends Entity implements InterfaceEnemy {
    private double enemy_V;		    	        // velocidades
    private double enemy_angle;				    // ângulos (indicam direção do movimento)
    private double enemy_RV;					// velocidades de rotação
    private double enemy1_nextShoot;

    public Enemy1(long currentTime) {
        super(ACTIVE, Math.random() * (GameLib.WIDTH - 20.0) + 10.0, -10.0, 0, 0, 9.0, 0, 0);

        this.enemy_V = 0.20 + Math.random() * 0.15;
        this.enemy_angle = 3 * Math.PI / 2;
        this.enemy_RV = 0.0;

        this.enemy1_nextShoot = currentTime + 500;
    }

    public void draw(long currentTime){
            if(entity_state == EXPLODING){

                double alpha = (currentTime - entity_explosion_start) / (entity_explosion_end - entity_explosion_start);
                GameLib.drawExplosion(entity_X, entity_Y, alpha);
            }

            if(entity_state == ACTIVE){

                GameLib.setColor(Color.CYAN);
                GameLib.drawCircle(entity_X, entity_Y, entity_radius);
            }
    }

    public double getEnemy_nextShoot() {
        return enemy1_nextShoot;
    }

    public void setEnemy_nextShoot(double enemy_nextShoot) {
        this.enemy1_nextShoot = enemy_nextShoot;
    }

    public double getEnemy_V() {
        return enemy_V;
    }

    public void setEnemy_V(double enemy_V) {
        this.enemy_V = enemy_V;
    }

    public double getEnemy_angle() {
        return enemy_angle;
    }

    public void setEnemy_angle(double enemy_angle) {
        this.enemy_angle = enemy_angle;
    }

    public double getEnemy_RV() {
        return enemy_RV;
    }

    public void setEnemy_RV(double enemy_RV) {
        this.enemy_RV = enemy_RV;
    }

    @Override
    public void setEntity_explosion_start(long currentTime) {
        super.setEntity_explosion_start(currentTime);
    }

    @Override
    public void setEntity_explosion_end(long currentTime) {
        super.setEntity_explosion_end(currentTime);
    }
}
