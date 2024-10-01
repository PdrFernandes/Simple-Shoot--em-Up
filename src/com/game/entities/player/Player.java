package com.game.entities.player;

import com.game.entities.Entity;
import com.game.entities.GameObjects;

import java.util.LinkedList;

class Player extends Entity implements IntafacePlayer {

    private int player_life;            // vida atual do player
    private final int player_maxLife;   // vida maxima do player
    private boolean playerColided;      // indica se o player colidiu
    private double ivunerable_start;    // tempo de inicio da invunerabilidade
    private double ivunerable_end;      // tempo de termino da invunerabilidade
    private boolean shilded;            // indica se o player está com shield

    public Player(int entity_state, double entity_X, double entity_Y, double entity_velocity_X, double enity_velocity_Y, double entity_radius, double entity_explosion_start, double entity_explosion_end, int playerMaxLife) {
        super(entity_state, entity_X, entity_Y, entity_velocity_X, enity_velocity_Y, entity_radius, entity_explosion_start, entity_explosion_end);
        player_life = playerMaxLife;
        player_maxLife = playerMaxLife;
        playerColided = false;
        shilded = false;
    }

    public void playerIsColliding(LinkedList<? extends GameObjects> entityList, long currentTime) {
        if (entity_state == ACTIVE){
            for (GameObjects element : entityList) {
                if (super.CalculoForCollision(element) < (this.entity_radius + element.getEntity_radius()) * 0.8) {
                    if (this.player_life <= 1 && !shilded) {
                        decrementPlayer_life();
                        this.entity_state = EXPLODING;
                        entity_explosion_start = currentTime;
                        entity_explosion_end = currentTime + 2000;
                    }
                    else {
                        this.playerColided = true;
                        this.entity_state = INVUNERABLE;
                        this.ivunerable_start = currentTime;
                        this.ivunerable_end = currentTime + 1000;
                        break;
                    }
                }
            }
        }
    }

    public void resetPlayerLife(){
        player_life = player_maxLife;
    }

    public int getPlayer_life() {
        return player_life;
    }

    public void incrementPlayer_life() {
        player_life++;
    }

    public void decrementPlayer_life() {
        player_life--;
    }

    public int getPlayer_maxLife() {
        return player_maxLife;
    }

    public boolean isPlayerColided() {
        return playerColided;
    }

    public void setPlayerColided(boolean playerColided) {
        this.playerColided = playerColided;
    }

    public double getIvunerable_end() {
        return ivunerable_end;
    }

    public void setIvunerable_end(double ivunerable_end) {
        this.ivunerable_end = ivunerable_end;
    }

    public double getIvunerable_start() {
        return ivunerable_start;
    }

    public void setIvunerable_start(double ivunerable_start) {
        this.ivunerable_start = ivunerable_start;
    }

    public boolean isShilded() {
        return shilded;
    }

    public void setShilded(boolean shilded) {
        this.shilded = shilded;
    }
}
