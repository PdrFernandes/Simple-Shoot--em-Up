package com.game.entities.player;

import java.awt.*;
import java.util.Iterator;
import java.util.LinkedList;

import com.game.core.gameLib.GameLib;
import com.game.entities.Enemies.InterfaceEnemiesManager;
import com.game.entities.GameConstant;
//import com.game.entities.Player;
import com.game.entities.projectile.InterfaceProjectile;
import com.game.entities.powerUps.InterfacePowerUpsManager;
import com.game.entities.projectile.Projectile;

public class PlayerManager implements GameConstant {

    private Player player;

    //variaveis para controle inter de alteração de cor do player
    private static final Color LIGHT_GRAY = Color.LIGHT_GRAY;
    private static final Color BLUE = Color.BLUE;

    private Color actualColor;                                  // define a cor atual do player, usado para controlar animação de invuneravel
    private double contador_cicloDeCores_invuneravel;           // contador para quantas vezes passou no ciclo de alteração de cores
    private double contador_cicloDeCores_invuneravel_2;         // mesmo fim de: contador_cicloDeCores_invuneravel

    private long player_nextShot;                               // instante a partir do qual pode haver um próximo tiro
    private LinkedList<Projectile> playerProjectilesList;       // linked list de projeteis do player

    //Construtor
    public PlayerManager(int player_maxLife) {
        player = new Player(ACTIVE, GameLib.WIDTH / 2.0, GameLib.HEIGHT * 0.90, 0.25, 0.25, 12.0, 0, 0, player_maxLife);
        //super(ACTIVE, GameLib.WIDTH / 2.0, GameLib.HEIGHT * 0.90, 0.25, 0.25, 12.0, 0, 0);

        this.playerProjectilesList = new LinkedList<>();
        this.player_nextShot = System.currentTimeMillis();
    }

    //Metodos

    /* tem por finalidade checar se o player colidiu com algo, se verdadeiro */
    /* o player perde uma vida e altera seu estado para invuneravel            */
    public void playerRefreshLife() {
        if (player.isPlayerColided()) {
            if (player.isShilded()) player.setShilded(false);
            else player.decrementPlayer_life();
            actualColor = BLUE;
            contador_cicloDeCores_invuneravel_2 = 1;
            contador_cicloDeCores_invuneravel = 1;
            System.out.println(player.getPlayer_life());
            player.setPlayerColided(false);
        }
    }

    /* checa a colisão entre um player e uma linked list de objetos derivados de GameObjects (inimigos e projeteis inimigos) */
    public void playerIsCollidingGameObject(long currentTime, InterfaceEnemiesManager enemiesManager) {

        enemiesManager.isCollidingPlayer(this.player, currentTime);

    }

    /* checa a colisão entre a linked list de projeteis do player */
    /* e uma linked list de objetos derivados de Entity           */
    public void isCollidingPlayerProjectile(InterfaceEnemiesManager enemiesManagers, long currentTime) {
        for (InterfaceProjectile p_element : this.playerProjectilesList){
            enemiesManagers.isCollidingPlayerProjectile(p_element, currentTime);
        }
    }

    public void powerUpIsColliding(InterfacePowerUpsManager powerUpsManager, long currentTime) {
        if (player.getEntity_state() == ACTIVE){
            powerUpsManager.powerUpIsColliding(currentTime, this.player);
        }
    }

    /* Atualiza o estado dos projeteis do player */
    public void refreshPlayerProjectile(long delta){
        Iterator<Projectile> iterator = playerProjectilesList.iterator();
        while (iterator.hasNext()){
            Projectile projectile = iterator.next();
            if (projectile.getEntity_Y() < 0){
                projectile.setEntity_state(INACTIVE);
                iterator.remove();
            }
            else {
                projectile.setEntity_X(projectile.getEntity_X() + (projectile.getEntity_velocity_X() * delta));
                projectile.setEntity_Y(projectile.getEntity_Y() + (projectile.getEntity_velocity_Y() * delta));
            }
        }
    }

    /* tem por finalidade checar se o player está invuneravel, se verdadeiro    */
    /* e se o tempo da ivunerabilidade acabou, atualiza o estado para ativo     */
    public void isPlayererInvunerable (long currentTime){
        if(player.getEntity_state() == INVUNERABLE){

            if(currentTime > player.getIvunerable_end()){
                player.setEntity_state(ACTIVE);
            }
        }
    }

    /* tem por finalidade checar se o player está explidindo, se verdadeiro     */
    /* e se o tempo da explosão acabou, atualiza o estado para ativo            */
    public void isPlayerexploding (long currentTime){
        if(player.getEntity_state() == EXPLODING){

            if(currentTime > player.getEntity_explosion_end()){
                player.setEntity_state(ACTIVE);
                player.resetPlayerLife();
            }
        }
    }

    /* faz a checagem de comandos para movimentação do player */
    public void isPlayerMoving(long delta) {
        if (player.getEntity_state() == ACTIVE || player.getEntity_state() == INVUNERABLE) {

            if (GameLib.iskeyPressed(GameLib.KEY_UP)) player.setEntity_Y(player.getEntity_Y() - delta * player.getEntity_velocity_Y());
            if (GameLib.iskeyPressed(GameLib.KEY_DOWN)) player.setEntity_Y(player.getEntity_Y() + delta * player.getEntity_velocity_Y());
            if (GameLib.iskeyPressed(GameLib.KEY_LEFT)) player.setEntity_X(player.getEntity_X() - delta * player.getEntity_velocity_X());
            if (GameLib.iskeyPressed(GameLib.KEY_RIGHT)) player.setEntity_X(player.getEntity_X() + delta * player.getEntity_velocity_X());

        }
    }

    /* faz a checagem de comandos para disparos do player */
    public void isPlayershooting(long currentTime){
        if (player.getEntity_state() == ACTIVE){
            if (GameLib.iskeyPressed(GameLib.KEY_CONTROL)) {
                if (currentTime > player_nextShot) {

                    playerProjectilesList.add(new Projectile(ACTIVE, player.getEntity_X(), player.getEntity_Y() - 2 * player.getEntity_radius(), 0.0, -1.0, 0));
                    player_nextShot = currentTime + 100;

                }
            }
        }
    }

    /* checa se o player está na tela */
    public void isPlayerOnScreen (){
        if(player.getEntity_X() < 0.0) player.setEntity_X(0.0);
        if(player.getEntity_X() >= GameLib.WIDTH) player.setEntity_X(GameLib.WIDTH - 1);
        if(player.getEntity_Y() < 25.0) player.setEntity_Y(25.0);
        if(player.getEntity_Y() >= GameLib.HEIGHT) player.setEntity_Y(GameLib.HEIGHT - 1);
    }

    /* desenha o layer */
    public void drawPlayer(long currentTime){
        if (player.getEntity_state() == INVUNERABLE){
            /* animação de invuneravel */

            double funcao1 = 500 * Math.pow(contador_cicloDeCores_invuneravel, 3);

            double funcao2 = 5 * Math.pow(contador_cicloDeCores_invuneravel_2, 3);

            if (actualColor == LIGHT_GRAY && funcao1 >= funcao2) {
                GameLib.setColor(BLUE);
                GameLib.drawPlayer(player.getEntity_X(), player.getEntity_Y(), player.getEntity_radius());
                actualColor = BLUE;
                contador_cicloDeCores_invuneravel = 1;
            } else if (actualColor == BLUE && funcao1 >= funcao2) {
                GameLib.setColor(LIGHT_GRAY);
                GameLib.drawPlayer(player.getEntity_X(), player.getEntity_Y(), player.getEntity_radius());
                actualColor = LIGHT_GRAY;
                contador_cicloDeCores_invuneravel = 1;
            }else {
                GameLib.setColor(actualColor);
                GameLib.drawPlayer(player.getEntity_X(), player.getEntity_Y(), player.getEntity_radius());
            }


            contador_cicloDeCores_invuneravel++;
            contador_cicloDeCores_invuneravel_2++;
        }
        else if(player.getEntity_state() == EXPLODING){

            double alpha = (currentTime - player.getEntity_explosion_start()) / (player.getEntity_explosion_end() - player.getEntity_explosion_start());
            GameLib.drawExplosion(player.getEntity_X(), player.getEntity_Y(), alpha);
        }
        else{

            GameLib.setColor(Color.BLUE);
            GameLib.drawPlayer(player.getEntity_X(), player.getEntity_Y(), player.getEntity_radius());
        }
        if (player.isShilded()){
            GameLib.setColor(Color.LIGHT_GRAY);
            GameLib.drawCircle(player.getEntity_X(), player.getEntity_Y(), player.getEntity_radius() + 4);
        }
    }

    /* desenha os projeteis do player */
    public void drawPlayerProjectiles(){
        for (Projectile projectile : playerProjectilesList) {

            if (projectile.getEntity_state() == ACTIVE) {

                GameLib.setColor(Color.GREEN);
                GameLib.drawLine(projectile.getEntity_X(), projectile.getEntity_Y() - 5, projectile.getEntity_X(), projectile.getEntity_Y() + 5);
                GameLib.drawLine(projectile.getEntity_X() - 1, projectile.getEntity_Y() - 3, projectile.getEntity_X() - 1, projectile.getEntity_Y() + 3);
                GameLib.drawLine(projectile.getEntity_X() + 1, projectile.getEntity_Y() - 3, projectile.getEntity_X() + 1, projectile.getEntity_Y() + 3);
            }
        }
    }

    /* desenha a barra de vida e vida do player */
    public void drawPlayerLife(){
        GameLib.setColor(Color.LIGHT_GRAY);
        int x_variavel = 25 + 32 * player.getPlayer_maxLife();
        GameLib.drawLine(x_variavel, 700, 20, 700);
        GameLib.drawLine(x_variavel, 685, 20, 685);
        GameLib.drawLine(20, 700, 20, 685);
        GameLib.drawLine(x_variavel, 700, x_variavel, 685);

        GameLib.setColor(Color.RED);
        for (int i = 0, x=24; i < player.getPlayer_life(); i++, x+= 32){
            for (int y = 689; y<=696; y++) {
                GameLib.drawLine(x + 29, y, x, y);
            }
        }
    }

    // getters
    public double getPlayer_Y(){
        return player.getEntity_Y();
    }

    public double getPlayer_state(){
        return player.getEntity_state();
    }
}
