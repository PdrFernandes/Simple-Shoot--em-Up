package com.game.core;

import com.game.core.gameLib.GameLib;

public class GameAtributes {

    private boolean running;

    //construtor
    public GameAtributes() {
    }

    //metodos

    /* Espera, sem fazer nada, até que o instante de tempo atual seja */
    /* maior ou igual ao instante especificado no parâmetro "time".    */
    public static void busyWait(long time){

        while(System.currentTimeMillis() < time) Thread.yield();
    }

    /* faz a checagem de comando para saber se o jogo deve parar */
    public void isGameRunning(){
        if(GameLib.iskeyPressed(GameLib.KEY_ESCAPE)) running = false;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunningTrue() {
        this.running = true;
    }


}
