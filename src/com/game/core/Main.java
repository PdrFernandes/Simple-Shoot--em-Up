package com.game.core;

import com.game.background.BackgroundManager;
import com.game.core.gameLib.GameLib;
import com.game.entities.Enemies.EnemiesManager;
import com.game.entities.player.PlayerManager;
import com.game.entities.powerUps.PowerUpsManager;

import static com.game.entities.GameConstant.ACTIVE;

public class Main {
	
	/* Método principal */
	
	public static void main(String [] args){

		GameAtributes gameAtributes = new GameAtributes();

		/* Indica que o jogo está em execução */
		gameAtributes.setRunningTrue();

		/* variáveis usadas no controle de tempo efetuado no main loop */
		long delta;
		long currentTime = System.currentTimeMillis();

		/* variáveis do player */

		// Cria todas as variaveis do player e uma linked list para seus projeteis
		PlayerManager playerManager = new PlayerManager(3);

		/* variáveis dos inimigos tipo */

		// Cira tres linked list, umas para projeteis inimigos e o resto para inimigos
		EnemiesManager enemiesManager = new EnemiesManager(currentTime);

		/* variáveis dos powerups */

		// Cria todas as variaveis do player e uma linked list para seus projeteis
		PowerUpsManager powerUpsManager = new PowerUpsManager(currentTime);

		/* estrelas que formam o fundo de primeiro plano */

		//Cria uma linked list para cada background
		BackgroundManager backgroundManager = new BackgroundManager();

		//Calcula as estrelas do bg1 e bg2 aleatoriamente, com base na resolução de 480 x 720

		backgroundManager.generateStarsBackgroud();
						
		/* iniciado interface gráfica */
		
		GameLib.initGraphics();
		
		/* ***********************************************************************************************/
		/*                                                                                               */
		/* Main loop do jogo                                                                             */
		/*                                                                                               */
		/* O main loop do jogo possui executa as seguintes operações:                                    */
		/*                                                                                               */
		/* 1) Verifica se há colisões e atualiza estados dos elementos conforme a necessidade.           */
		/*                                                                                               */
		/* 2) Atualiza estados dos elementos baseados no tempo que correu desde a última atualização     */
		/*    e no timestamp atual: posição e orientação, execução de disparos de projéteis, etc.        */
		/*                                                                                               */
		/* 3) Processa entrada do usuário (teclado) e atualiza estados do player conforme a necessidade. */
		/*                                                                                               */
		/* 4) Desenha a cena, a partir dos estados dos elementos.                                        */
		/*                                                                                               */
		/* 5) Espera um período de tempo (de modo que delta seja aproximadamente sempre constante).      */
		/*                                                                                               */
		/* ***********************************************************************************************/
		
		while(gameAtributes.isRunning()){
		
			/* Usada para atualizar o estado dos elementos do jogo    */
			/* (player, projéteis e inimigos) "delta" indica quantos  */
			/* ms se passaram desde a última atualização.             */

			delta = System.currentTimeMillis() - currentTime;
			
			/* Já a variável "currentTime" nos dá o timestamp atual.  */
			
			currentTime = System.currentTimeMillis();
			
			/* *************************/
			/* Verificação de colisões */
			/* *************************/
						
			if(playerManager.getPlayer_state() == ACTIVE) {

				/* colisões player - projeteis (inimigo) */
				/* colisões player - inimigos */
				playerManager.playerIsCollidingGameObject(currentTime, enemiesManager);

				/* colisões projeteis (player) - inimigos */
				playerManager.isCollidingPlayerProjectile(enemiesManager, currentTime);

				/* colisões player - powerUps */
				playerManager.powerUpIsColliding(powerUpsManager, currentTime);
			}

			/* *************************/
			/* Atualizações de estados */
			/* *************************/
			/* vida (player) */
			playerManager.playerRefreshLife();

			/* projeteis (player) */
			playerManager.refreshPlayerProjectile(delta);
			
			/* projeteis (inimigos) */
			enemiesManager.refreshEnemiesProjectile(delta);

			/* inimigos tipo 1, 2 e 3 */
			enemiesManager.refreshAllenemies(playerManager, delta, currentTime);

			/* powerups (shield e lifePoint) */
			powerUpsManager.refreshPouwerUps(delta);
			
			/* verificando se novos inimigos de todos os tipos devem ser "lançados" */
			enemiesManager.launchNewEnemies(currentTime);

			/* verificando se novos PowerUps de todos os tipos devem ser "lançados" */
			powerUpsManager.launchNewPowerUps(currentTime);

			/* Verificando se a invunerabilidade do player já acabou.       */
			/* Ao final da invunerabilidade, o player volta a ser atingivel */
			playerManager.isPlayererInvunerable(currentTime);
			
			/* Verificando se a explosão do player já acabou.         */
			/* Ao final da explosão, o player volta a ser controlável */
			playerManager.isPlayerexploding(currentTime);

			/* ******************************************/
			/* Verificando entrada do usuário (teclado) */
			/* ******************************************/

			/* faz a checagem de comandos para movimentação do player */
			playerManager.isPlayerMoving(delta);

			/* faz a checagem de comandos para disparos do player */
			playerManager.isPlayershooting(currentTime);

			/* faz a checagem de comando para saber se o jogo deve parar */
			gameAtributes.isGameRunning();
			
			/* Verificando se coordenadas do player ainda estão dentro	*/
			/* da tela de jogo após processar entrada do usuário.       */
			playerManager.isPlayerOnScreen();


			/* *****************/
			/* Desenho da cena */
			/* *****************/
			
			/* desenhando plano fundo distante */
			backgroundManager.drawBg2(delta);
			
			/* desenhando plano de fundo próximo */
			backgroundManager.drawBg1(delta);

			/* desenhando player */
			playerManager.drawPlayer(currentTime);

			/* deenhando projeteis (player) */
			playerManager.drawPlayerProjectiles();

			/* desenhando projeteis (inimigos) */
			enemiesManager.drawEnemiesProjectile();

			/* desenhando inimigos (tipo 1, 2, 3) */
			enemiesManager.drawAllEnemies(currentTime);

			/* desenhando powerUps (shield e life Point) */
			powerUpsManager.drawAllPowerUps();

			/* desenhando barra de vida (player) */
			playerManager.drawPlayerLife();

			/* chamama a display() da classe GameLib atualiza o desenho exibido pela interface do jogo. */
			GameLib.display();
			
			/* faz uma pausa de modo que cada execução do laço do main loop demore aproximadamente 5 ms. */
			GameAtributes.busyWait(currentTime + 5);
		}
		
		System.exit(0);
	}
}
