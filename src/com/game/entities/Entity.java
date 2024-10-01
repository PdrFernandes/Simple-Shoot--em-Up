package com.game.entities;

public class Entity implements GameObjects, GameConstant {
    protected int entity_state;				    				// estado
    protected double entity_X;			                        // coordenada x
    protected double entity_Y;			                        // coordenada y
    protected double entity_velocity_X;						    // velocidade no eixo x
    protected double entity_velocity_Y;						    // velocidade no eixo y
    protected double entity_radius;						    	// raio (tamanho aproximado do player)
    protected double entity_explosion_start;					// instante do início da explosão
    protected double entity_explosion_end;						// instante do final da explosão


    //Constructor
    public Entity(int entity_state, double entity_X, double entity_Y, double entity_velocity_X, double enity_velocity_Y, double entity_radius, double entity_explosion_start, double entity_explosion_end) {
        this.entity_state = entity_state;
        this.entity_X = entity_X;
        this.entity_Y = entity_Y;
        this.entity_velocity_X = entity_velocity_X;
        this.entity_velocity_Y = enity_velocity_Y;
        this.entity_radius = entity_radius;
        this.entity_explosion_start = entity_explosion_start;
        this.entity_explosion_end = entity_explosion_end;
    }



    //Metodos

    public double CalculoForCollision(GameObjects element) {
            double dx = element.getEntity_X() - this.entity_X;
            double dy = element.getEntity_Y() - this.entity_Y;
            return Math.sqrt(dx * dx + dy * dy);
    }

    //Getter and Setter
    public double getEntity_explosion_end() {
        return entity_explosion_end;
    }

    public void setEntity_explosion_end(double entity_explosion_end) {
        this.entity_explosion_end = entity_explosion_end;
    }

    public double getEntity_explosion_start() {
        return entity_explosion_start;
    }

    public void setEntity_explosion_start(double entity_explosion_start) {
        this.entity_explosion_start = entity_explosion_start;
    }

    public double getEntity_radius() {
        return entity_radius;
    }

    public double getEntity_velocity_Y() {
        return entity_velocity_Y;
    }

    public void setEntity_velocity_Y(double enity_velocity_Y) {
        this.entity_velocity_Y = enity_velocity_Y;
    }

    public double getEntity_Y() {
        return entity_Y;
    }

    public void setEntity_Y(double y) {
        this.entity_Y = y;
    }

    public int getEntity_state() {
        return entity_state;
    }

    public void setEntity_state(int state) {
        this.entity_state = state;
    }

    public double getEntity_X() {
        return entity_X;
    }

    public void setEntity_X(double x) {
        this.entity_X = x;
    }

    public double getEntity_velocity_X() {
        return entity_velocity_X;
    }

    public void setEntity_velocity_X(double entity_velocity_X) {
        this.entity_velocity_X = entity_velocity_X;
    }


}
