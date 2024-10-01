package com.game.entities.powerUps;

import com.game.core.gameLib.GameLib;
import com.game.entities.Entity;
import com.game.entities.GameConstant;
import com.game.entities.GameObjects;
import com.game.entities.player.IntafacePlayer;

import java.awt.*;

class LifePoint extends Entity implements InterfacePowerUp, GameConstant {
    private double lifePoint_v;		    	    // velocidades
    private double lifePoint_angle;				    // ângulos (indicam direção do movimento)

    public LifePoint(long currentTime) {
        super(ACTIVE, Math.random() * (GameLib.WIDTH - 20.0) + 10.0, -10.0, 0, 0, 6.0, 0, 0);

        this.lifePoint_v = 0.35 + Math.random() * 0.15;
        this.lifePoint_angle = (3 * Math.PI / 2);
    }

    public void draw(){
        if(entity_state == ACTIVE){

            GameLib.setColor(Color.RED);
            GameLib.drawDiamond(entity_X, entity_Y, entity_radius);
            GameLib.drawDiamond(entity_X, entity_Y, entity_radius - 2);
            GameLib.drawDiamond(entity_X, entity_Y, entity_radius - 4);
        }
    }

    public void applyPower(IntafacePlayer player){
        player.incrementPlayer_life();
    }

    public double calcForCollision (GameObjects player){
        return super.CalculoForCollision(player);
    }

    public double getPowerUp_v() {
        return lifePoint_v;
    }

    public void setLifePoint_v(double lifePoint_v) {
        this.lifePoint_v = lifePoint_v;
    }

    public double getPowerUp_angle() {
        return lifePoint_angle;
    }

    public void setLifePoint_angle(double lifePoint_angle) {
        this.lifePoint_angle = lifePoint_angle;
    }
}
