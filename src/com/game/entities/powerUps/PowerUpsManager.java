package com.game.entities.powerUps;

import com.game.core.gameLib.GameLib;
import com.game.entities.GameConstant;
import com.game.entities.player.IntafacePlayer;

import java.util.Iterator;
import java.util.LinkedList;

public class PowerUpsManager implements GameConstant, InterfacePowerUpsManager {


    //variaveis de controle enemy1
    private long nextShield;                // instante em que um novo shield deve aparecer

    private long nextLifePoint;             // instante em que um novo shield deve aparecer

    private LinkedList<Shield> shieldList;   // linked list dos inimigos do tipo 1
    private LinkedList<LifePoint> lifePointList;  // linked list dos inimigos do tipo 2

    public PowerUpsManager(long currentTime) {
        this.lifePointList = new LinkedList<>();
        this.shieldList = new LinkedList<>();

        this.nextShield = currentTime + 3300;
        this.nextLifePoint = currentTime + 3000;

    }

    /* atualiza os inimigos do tipo 1 (se eles devem ser destruidos, a suas posicoes e novos projeteis) */
    public void refreshPouwerUps(long delta){
        refreshShield(delta);
        refreshLifePoint(delta);
    }

    private void refreshShield( long delta){
        Iterator<Shield> iterator = shieldList.iterator();
        while (iterator.hasNext()){
            refreshPowerUp(iterator, delta);
        }
    }

    private void refreshLifePoint( long delta){
        Iterator<LifePoint> iterator = lifePointList.iterator();
        while (iterator.hasNext()){
            refreshPowerUp(iterator, delta);
        }
    }

    private void refreshPowerUp(Iterator<? extends InterfacePowerUp> iterator, long delta) {
        InterfacePowerUp element = iterator.next();

        if (element.getEntity_state() == ACTIVE) {
            if (element.getEntity_Y() > GameLib.HEIGHT + 10 || element.getEntity_X() > GameLib.WIDTH || element.getEntity_X() < 0) {
                element.setEntity_state(INACTIVE);
                iterator.remove();
            }
            else {

                element.setEntity_X(element.getEntity_X() + (element.getPowerUp_v() * Math.cos(element.getPowerUp_angle()) * delta));
                element.setEntity_Y(element.getEntity_Y() + (element.getPowerUp_v() * Math.sin(element.getPowerUp_angle()) * delta * (-1.0)));
            }
        }
    }

    public void powerUpIsColliding(long currentTime, IntafacePlayer player) {
        Iterator<Shield> iterator = shieldList.iterator();
        while (iterator.hasNext()) {
            Shield shield = iterator.next();
            if (shield.calcForCollision(player) < (player.getEntity_radius() + shield.getEntity_radius()) * 0.8) {
                if (!player.isShilded()) {
                    shield.applyPower(player);
                    iterator.remove();
                }
            }
        }

        Iterator<LifePoint> iteratorLife = lifePointList.iterator();
        while (iteratorLife.hasNext()) {
            LifePoint lifePoint = iteratorLife.next();
            if (lifePoint.calcForCollision(player) < (player.getEntity_radius() + lifePoint.getEntity_radius()) * 0.8) {
                if (!player.isShilded() && player.getPlayer_life() < player.getPlayer_maxLife()) {
                    lifePoint.applyPower(player);
                    iteratorLife.remove();
                }
            }
        }
    }

    public void launchNewPowerUps (long currentTime){
        launchNewShield(currentTime);
        launchNewLifePoin(currentTime);
    }

    /* lanca novos inimigos do tipo 1 */
    private void launchNewShield (long currentTime){
        if (currentTime > nextShield){

            if (Math.random() > 0.50) shieldList.add(new Shield(currentTime));
            nextShield = currentTime + 3300;

        }
    }

    private void launchNewLifePoin (long currentTime){
        if (currentTime > nextLifePoint){

            if (Math.random() > 0.50) lifePointList.add(new LifePoint(currentTime));
            nextLifePoint = currentTime + 3000;

        }
    }

    public void drawAllPowerUps (){
        drawPowerUp(shieldList);
        drawPowerUp(lifePointList);
    }

    /* desenha os inimigos do tipo 1 */
    private void drawPowerUp (LinkedList<? extends InterfacePowerUp> powerUplist){
        for(InterfacePowerUp powerUp : powerUplist){
            powerUp.draw();
        }
    }

    //Getters setados como <default> para que quem estiver no main não ter acesso as listas diretamente

}
