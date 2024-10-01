package com.game.background;

import com.game.core.gameLib.GameLib;

import java.awt.*;
import java.util.LinkedList;

public class BackgroundManager {

    private LinkedList<Star> background1List;

    private final double background1_speed; //= 0.070;
    private double background1_count; //= 0.0;

    private LinkedList<Star> background2List;

    private final double background2_speed; // = 0.045;
    private double background2_count; // = 0.0;

    public BackgroundManager() {
        this.background1List = new LinkedList<Star>();
        this.background1_speed = 0.070;
        this.background1_count = 0.0;
        this.background2List = new LinkedList<Star>();
        this.background2_speed = 0.045;
        this.background2_count = 0.0;
    }

    /* gera estrelas aleatorias para o background */
    public void generateStarsBackgroud (){
        for(int i = 0; i < 20; i++){
            background1List.add(new Star(Math.random() * GameLib.WIDTH, Math.random() * GameLib.HEIGHT));
            background2List.add(new Star(Math.random() * GameLib.WIDTH, Math.random() * GameLib.HEIGHT));
        }


    }

    /* desenha bg 1 (fundo perto) */
    public void drawBg1 (long delta){
        GameLib.setColor(Color.GRAY);
        background1_count += background1_speed * delta;

        for (Star star : background1List){
            GameLib.fillRect(star.getStar_X(), (star.getStar_Y() + background1_count) % GameLib.HEIGHT, 3, 3);
        }
    }

    /* desenha bg 2 (fundo distante) */
    public void drawBg2 (long delta){
        GameLib.setColor(Color.DARK_GRAY);
        background2_count += background2_speed * delta;

        for (Star star : background2List){
            GameLib.fillRect(star.getStar_X(), (star.getStar_Y() + background2_count) % GameLib.HEIGHT, 2, 2);
        }
    }


}
