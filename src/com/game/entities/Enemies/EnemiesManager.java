package com.game.entities.Enemies;

import com.game.core.gameLib.GameLib;
import com.game.entities.*;
import com.game.entities.player.IntafacePlayer;
import com.game.entities.player.PlayerManager;
import com.game.entities.projectile.InterfaceProjectile;
import com.game.entities.projectile.Projectile;

import java.awt.*;
import java.util.Iterator;
import java.util.LinkedList;

public class EnemiesManager implements GameConstant, InterfaceEnemiesManager {

    //variaveis de controle enemy1
    private long nextEnemy1;                // instante em que um novo inimigo 1 deve aparecer

    //variaveis de controle enemy2
    private double enemy2_spawnX;		    // coordenada x do próximo inimigo tipo 2 a aparecer
    private int enemy2_count;			    // raio (tamanho aproximado do inimigo 2)
    private long nextEnemy2;                // instante em que um novo inimigo 2 deve aparecer

    //variaveis de controle enemy3
    private double nextEnemy3;
    private long enemy3_nextShoot;

    private final LinkedList<InterfaceEnemy> enemy1List;   // linked list dos inimigos do tipo 1
    private final LinkedList<InterfaceEnemy> enemy2List;
    private final LinkedList<InterfaceEnemy> enemy3List;// linked list dos inimigos do tipo 2

    private final LinkedList<Projectile> enemyProjectileList;  // linked list dos projeteis de todos inimigos


    public EnemiesManager(long currentTime) {
        enemy1List = new LinkedList<>();
        enemy2List = new LinkedList<>();
        enemy3List = new LinkedList<>();

        enemyProjectileList = new LinkedList<>();

        this.nextEnemy1 = currentTime + 2000;
        this.nextEnemy2 = currentTime + 7000;
        this.nextEnemy3 = currentTime + 2000;

        this.enemy2_spawnX = GameLib.WIDTH * 0.20;
        this.enemy2_count = 0;
    }

    public void isCollidingPlayerProjectile(InterfaceProjectile p_element, long currentTime){
            p_element.isCollidingPlayerProjectile(enemy1List, currentTime);
            p_element.isCollidingPlayerProjectile(enemy2List, currentTime);
            p_element.isCollidingPlayerProjectile(enemy3List, currentTime);
    }

    public void isCollidingPlayer(IntafacePlayer player, long currentTime){
        player.playerIsColliding(enemy1List, currentTime);
        player.playerIsColliding(enemy2List, currentTime);
        player.playerIsColliding(enemy3List, currentTime);
        player.playerIsColliding(enemyProjectileList, currentTime);
    }

    /* atualiza os projeteis inimigos */
    public void refreshEnemiesProjectile(long delta){
        Iterator<Projectile> iterator = enemyProjectileList.iterator();
        while (iterator.hasNext()){
            Projectile projectile = iterator.next();
            if (projectile.getEntity_Y() > GameLib.HEIGHT || projectile.getEntity_X() > GameLib.WIDTH || projectile.getEntity_X() < 0){
                projectile.setEntity_state(INACTIVE);
                iterator.remove();
            }
            else {
                projectile.setEntity_X(projectile.getEntity_X() + (projectile.getEntity_velocity_X() * delta));
                projectile.setEntity_Y(projectile.getEntity_Y() + (projectile.getEntity_velocity_Y() * delta));
            }
        }
    }

    public void refreshAllenemies(PlayerManager player, long delta, long currentTime){
        refreshEnemy1(player, delta, currentTime);
        refreshEnemy2(delta, currentTime);
        refreshEnemy3(delta, currentTime);
    }

    /* atualiza os inimigos do tipo 1 (se eles devem ser destruidos, a suas posicoes e novos projeteis) */
    private void refreshEnemy1(PlayerManager player, long delta, long currentTime){
        Iterator<InterfaceEnemy> iterator = enemy1List.iterator();
        while (iterator.hasNext()){
            InterfaceEnemy element = iterator.next();

            if (element.getEntity_state() == EXPLODING){
                if(currentTime > element.getEntity_explosion_end()){
                    element.setEntity_state(INACTIVE);
                    iterator.remove();
                }
            }

            if(element.getEntity_state() == ACTIVE){
                if(element.getEntity_Y() > GameLib.HEIGHT + 10){
                    element.setEntity_state(INACTIVE);
                    iterator.remove();
                }
                else{

                    element.setEntity_X(element.getEntity_X() + (element.getEnemy_V() * Math.cos(element.getEnemy_angle()) * delta));
                    element.setEntity_Y(element.getEntity_Y() + (element.getEnemy_V() * Math.sin(element.getEnemy_angle()) * delta * (-1.0)));
                    element.setEnemy_angle(element.getEnemy_angle() + (element.getEnemy_RV() * delta));

                    if (currentTime > element.getEnemy_nextShoot() && element.getEntity_Y() < player.getPlayer_Y()){
                        enemyProjectileList.add(new Projectile(ACTIVE, element.getEntity_X(), element.getEntity_Y(), Math.cos(element.getEnemy_angle()) * 0.45, Math.sin(element.getEnemy_angle()) * 0.45 * (-1.0),2.0));
                        element.setEnemy_nextShoot((currentTime + 200 + Math.random() * 500));
                    }
                }
            }
        }
    }

    /* atualiza os inimigos do tipo 2 (se eles devem ser destruidos, a suas posicoes e novos projeteis) */
    private void refreshEnemy2(long delta, long currentTime){
        Iterator<InterfaceEnemy> iterator = enemy2List.iterator();
        while (iterator.hasNext()){
            InterfaceEnemy element = iterator.next();

            if (element.getEntity_state() == EXPLODING){
                if(currentTime > element.getEntity_explosion_end()){
                    element.setEntity_state(INACTIVE);
                    iterator.remove();
                }
            }

            if(element.getEntity_state() == ACTIVE){
                if(element.getEntity_X() < -10 || element.getEntity_X() > GameLib.WIDTH + 10){
                    element.setEntity_state(INACTIVE);
                    iterator.remove();
                }
                else{
                    boolean shootNow = false;
                    double previousY = element.getEntity_Y();

                    element.setEntity_X(element.getEntity_X() + (element.getEnemy_V() * Math.cos(element.getEnemy_angle()) * delta));
                    element.setEntity_Y(element.getEntity_Y() + (element.getEnemy_V() * Math.sin(element.getEnemy_angle()) * delta * (-1.0)));
                    element.setEnemy_angle(element.getEnemy_angle() + (element.getEnemy_RV() * delta));

                    double threshold = GameLib.HEIGHT * 0.30;

                    if(previousY < threshold && element.getEntity_Y() >= threshold) {

                        if(element.getEntity_X() < GameLib.WIDTH / 2.0) element.setEnemy_RV(0.003);
                        else element.setEnemy_RV(-0.003);
                    }

                    if(element.getEnemy_RV() > 0 && Math.abs(element.getEnemy_angle() - 3 * Math.PI) < 0.05){

                        element.setEnemy_RV(0.0);
                        element.setEnemy_angle(3 * Math.PI);
                        shootNow = true;
                    }

                    if(element.getEnemy_RV() < 0 && Math.abs(element.getEnemy_angle()) < 0.05){

                        element.setEnemy_RV(0.0);
                        element.setEnemy_angle(0.0);
                        shootNow = true;
                    }

                    if(shootNow){

                        double [] angles = { Math.PI/2 + Math.PI/8, Math.PI/2, Math.PI/2 - Math.PI/8 };

                        for (double angle : angles) {

                            double a = angle + Math.random() * Math.PI / 6 - Math.PI / 12;
                            double vx = Math.cos(a);
                            double vy = Math.sin(a);

                            enemyProjectileList.add(new Projectile(ACTIVE, element.getEntity_X(), element.getEntity_Y(), vx * 0.30, vy * 0.30, 2.0));
                        }
                    }
                }
            }
        }
    }

    /* atualiza os inimigos do tipo 3 (se eles devem ser destruidos, a suas posicoes e novos projeteis) */
    private void refreshEnemy3(long delta, long currentTime){
        Iterator<InterfaceEnemy> iterator = enemy3List.iterator();
        while (iterator.hasNext()){
            InterfaceEnemy element = iterator.next();

            if (element.getEntity_state() == EXPLODING){
                if(currentTime > element.getEntity_explosion_end()){
                    element.setEntity_state(INACTIVE);
                    iterator.remove();
                }
            }

            if(element.getEntity_state() == ACTIVE){
                if(element.getEntity_X() >= GameLib.WIDTH + 15.0){
                    element.setEntity_state(INACTIVE);
                    iterator.remove();
                }
                if(element.getEntity_X() <= (-15.0)){
                    element.setEntity_state(INACTIVE);
                    iterator.remove();
                }
                else{

                    element.setEntity_X(element.getEntity_X() + (element.getEnemy_V() * Math.cos(element.getEnemy_angle()) * delta));
                    element.setEntity_Y(element.getEntity_Y() + (element.getEnemy_V() * Math.sin(element.getEnemy_angle()) * delta * (-1.0)));
                    element.setEnemy_angle(element.getEnemy_angle() + (element.getEnemy_RV() * delta));

                    if (currentTime > element.getEnemy_nextShoot()){
                        double vx = Math.cos(4 * Math.PI / 3);
                        double vx2 = Math.cos(5 * Math.PI / 3);

                        enemyProjectileList.add(new Projectile(ACTIVE, element.getEntity_X(), element.getEntity_Y(), vx * 0.3, Math.cos(0) * 0.45,2.0));
                        enemyProjectileList.add(new Projectile(ACTIVE, element.getEntity_X(), element.getEntity_Y(), vx2 * 0.3, Math.cos(0) * 0.45,2.0));
                        element.setEnemy_nextShoot(currentTime + 400 + Math.random() * 500);
                    }
                }
            }
        }
    }

    public void launchNewEnemies(long currentTime){
        launchNewEnemy1(currentTime);
        launchNewEnemy2(currentTime);
        launchNewEnemy3(currentTime);
    }

    /* lanca novos inimigos do tipo 1 */
    private void launchNewEnemy1 (long currentTime){
        if (currentTime > nextEnemy1){

            enemy1List.add(new Enemy1(currentTime));
            nextEnemy1 = currentTime + 500;

        }
    }

    /* lanca novos inimigos do tipo 2 */
    private void launchNewEnemy2 (long currentTime){
        if(currentTime > nextEnemy2){

            enemy2List.add(new Enemy2(enemy2_spawnX));
            enemy2_count++;

            if(enemy2_count < 10){

                nextEnemy2 = currentTime + 120;
            }
            else {

                enemy2_count = 0;
                enemy2_spawnX = Math.random() > 0.5 ? GameLib.WIDTH * 0.2 : GameLib.WIDTH * 0.8;
                nextEnemy2 = (long) (currentTime + 3000 + Math.random() * 3000);
            }
        }
    }

    /* lanca novos inimigos do tipo 3 */
    private void launchNewEnemy3 (long currentTime) {
        if (currentTime > nextEnemy3) {
            if (Math.random() >= 0.50) {
                enemy3List.add(new Enemy3(currentTime, GameLib.WIDTH + 10.0, Math.PI));
            }
            else {
                enemy3List.add(new Enemy3(currentTime, -10.0, 0));
            }
            nextEnemy3 = currentTime + 2500;

        }
    }

    /* desenha projeteis dos inimigos */
    public void drawEnemiesProjectile(){
        for(Projectile projectile : enemyProjectileList){

            if(projectile.getEntity_state() == ACTIVE){

                GameLib.setColor(Color.RED);
                GameLib.drawCircle(projectile.getEntity_X(), projectile.getEntity_Y(), projectile.getEntity_radius());
            }
        }
    }

    public void drawAllEnemies(long currentTime){
        drawEnemy(enemy1List ,currentTime);
        drawEnemy(enemy2List ,currentTime);
        drawEnemy(enemy3List ,currentTime);
    }

    /* desenha os inimigos */
    private void drawEnemy (LinkedList<InterfaceEnemy> enemyList ,long currentTime){
        for(InterfaceEnemy enemy : enemyList){
            enemy.draw(currentTime);
        }
    }


}
