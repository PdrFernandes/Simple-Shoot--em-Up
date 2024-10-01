package com.game.entities;

public interface GameObjects {
    double CalculoForCollision(GameObjects element);

    int getEntity_state();
    void setEntity_state(int object_state);
    double getEntity_X();
    void setEntity_X(double object_X);
    double getEntity_Y();
    void setEntity_Y(double object_Y);
    double getEntity_velocity_X();
    void setEntity_velocity_X(double object_velocity_x);
    double getEntity_velocity_Y();
    void setEntity_velocity_Y(double object_velocity_Y);
    double getEntity_radius();
}
