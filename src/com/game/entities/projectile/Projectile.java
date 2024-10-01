package com.game.entities.projectile;


import com.game.entities.Enemies.InterfaceEnemy;
import com.game.entities.GameConstant;
import com.game.entities.GameObjects;

import java.util.LinkedList;

public class Projectile implements GameObjects, GameConstant, InterfaceProjectile {
    private int entity_state;               // estado
    private double entity_X;                // coordenada x
    private double entity_Y;                // coordenada y
    private double entity_velocity_X;       // velocidade no eixo x
    private double entity_velocity_Y;       // velocidade no eixo y
    private final double entity_radius;     // raio (usado só para projéteis inimigos)


    public Projectile(int entity_state, double entity_X, double entity_Y, double entity_velocity_X, double entity_velocity_Y, double entity_radius) {
        this.entity_state = entity_state;
        this.entity_X = entity_X;
        this.entity_Y = entity_Y;
        this.entity_velocity_X = entity_velocity_X;
        this.entity_velocity_Y = entity_velocity_Y;
        this.entity_radius = entity_radius;
    }

    public void isCollidingPlayerProjectile (LinkedList<InterfaceEnemy> entityList, long currentTime) {
        for (InterfaceEnemy element : entityList) {

            if (CalculoForCollision(element) < element.getEntity_radius()) {
                element.setEntity_state(EXPLODING);
                element.setEntity_explosion_start(currentTime);
                element.setEntity_explosion_end(currentTime + 500);
            }
        }
    }

    @Override
    public double CalculoForCollision(GameObjects element) {
        double dx = element.getEntity_X() - this.entity_X;
        double dy = element.getEntity_Y() - this.entity_Y;
        return Math.sqrt(dx * dx + dy * dy);
    }

    @Override
    public int getEntity_state() {
        return this.entity_state;
    }

    @Override
    public void setEntity_state(int object_state) {
        this.entity_state = object_state;
    }

    @Override
    public double getEntity_X() {
        return this.entity_X;
    }

    @Override
    public void setEntity_X(double object_X) {
        this.entity_X = object_X;
    }

    @Override
    public double getEntity_Y() {
        return this.entity_Y;
    }

    @Override
    public void setEntity_Y(double object_Y) {
        this.entity_Y = object_Y;
    }

    @Override
    public double getEntity_velocity_X() {
        return this.entity_velocity_X;
    }

    @Override
    public void setEntity_velocity_X(double object_velocity_x) {
        this.entity_velocity_X = object_velocity_x;
    }

    @Override
    public double getEntity_velocity_Y() {
        return this.entity_velocity_Y;

    }

    @Override
    public void setEntity_velocity_Y(double object_velocity_Y) {
        this.entity_velocity_Y = object_velocity_Y;
    }

    public double getEntity_radius() {
        return entity_radius;
    }
}
