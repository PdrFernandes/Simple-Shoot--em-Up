package com.game.background;

public class Star {

    private double star_X; // coordenada x
    private double star_Y; // coordenada y

    //Construtor
    public Star(double star_X, double star_Y) {
        this.star_X = star_X;
        this.star_Y = star_Y;
    }

    //Metodos


    //Getters and Setters
    public double getStar_X() {
        return star_X;
    }

    public void setStar_X(double star_X) {
        this.star_X = star_X;
    }

    public double getStar_Y() {
        return star_Y;
    }

    public void setStar_Y(double star_Y) {
        this.star_Y = star_Y;
    }
}
