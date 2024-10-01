package com.game.entities.Enemies;

import com.game.core.gameLib.GameLib;
import com.game.entities.Entity;

import java.awt.*;

class Enemy3 extends Entity implements InterfaceEnemy {
    private double enemy_V;		    	        // velocidades
    private double enemy_angle;				    // ângulos (indicam direção do movimento)
    private double enemy_RV;					// velocidades de rotação
    private double enemy_nextShoot;

    public Enemy3(long currentTime, double x, double enemy_angle) {
        super(ACTIVE, x ,20.0 + Math.random() * 80.0, 0, 0, 8.0, 0, 0);



        this.enemy_V = 0.10 + Math.random() * 0.1;
        this.enemy_angle = enemy_angle;
        this.enemy_RV = 0.0;

        this.enemy_nextShoot = currentTime + 400;
    }

    public void draw(long currentTime){
        if(entity_state == EXPLODING){

            double alpha = (currentTime - entity_explosion_start) / (entity_explosion_end - entity_explosion_start);
            GameLib.drawExplosion(entity_X, entity_Y, alpha);
        }

        if(entity_state == ACTIVE){

            GameLib.setColor(Color.GREEN);
            GameLib.drawCircle(entity_X, entity_Y, entity_radius);
            GameLib.drawLine(entity_X - entity_radius, entity_Y - entity_radius, entity_X + entity_radius, entity_Y - entity_radius);
            GameLib.drawLine(entity_X - entity_radius, entity_Y + entity_radius, entity_X + entity_radius, entity_Y + entity_radius);
            GameLib.drawLine(entity_X + entity_radius, entity_Y - entity_radius, entity_X + entity_radius, entity_Y + entity_radius);
            GameLib.drawLine(entity_X - entity_radius, entity_Y - entity_radius, entity_X - entity_radius, entity_Y + entity_radius);
        }
    }

    public double getEnemy_nextShoot() {
        return enemy_nextShoot;
    }

    public void setEnemy_nextShoot(double enemy_nextShoot) {
        this.enemy_nextShoot = enemy_nextShoot;
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
