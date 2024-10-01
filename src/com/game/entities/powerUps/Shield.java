package com.game.entities.powerUps;

import com.game.core.gameLib.GameLib;
import com.game.entities.Entity;
import com.game.entities.GameObjects;
import com.game.entities.player.IntafacePlayer;

import java.awt.*;

class Shield extends Entity implements InterfacePowerUp {
    private double shield_v;		    	    // velocidades
    private double shield_angle;				// ângulos (indicam direção do movimento)

    public Shield(long currentTime) {
        super(ACTIVE, Math.random() * (GameLib.WIDTH - 20.0) + 10.0, -10.0, 0, 0, 6.0, 0, 0);

        this.shield_v = 0.35 + Math.random() * 0.15;
        this.shield_angle = (0.97 + (1.03 - 0.97) * Math.random()) * (3 * Math.PI / 2);
    }

    public void draw(){
        if(entity_state == ACTIVE){

            GameLib.setColor(Color.CYAN);
            GameLib.drawDiamond(entity_X, entity_Y, entity_radius);
            GameLib.drawDiamond(entity_X, entity_Y, entity_radius - 2);
            GameLib.drawDiamond(entity_X, entity_Y, entity_radius - 4);
        }
    }

    public void applyPower(IntafacePlayer player){
        player.setShilded(true);
    }

    public double calcForCollision (GameObjects player){
        return super.CalculoForCollision(player);
    }

    public double getPowerUp_v() {
        return shield_v;
    }

    public void setShield_v(double shield_v) {
        this.shield_v = shield_v;
    }

    public double getPowerUp_angle() {
        return shield_angle;
    }

    public void setShield_angle(double shield_angle) {
        this.shield_angle = shield_angle;
    }
}
