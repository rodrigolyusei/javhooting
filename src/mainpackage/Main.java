package mainpackage;

import components.handlers.*;
import components.player.Player;

public class Main {
	public static void busyWait(long time){
		while(System.currentTimeMillis() < time) Thread.yield();
	}

	public static void main(String [] args){
		/* Indica que o jogo está em execução */
		boolean running = true;

		/* Variáveis usadas no controle de tempo efetuado no main loop */
		long delta;
		long currentTime = System.currentTimeMillis();

		/* Inicialização dos objetos responsáveis por controlar a lógica do jogo */
		Player player = new Player(GameLib.WIDTH/2, GameLib.HEIGHT * 0.80, currentTime, 12);
		PowerUpHandler powerUpHandler = new PowerUpHandler();
		ProjetileHandler projetileHandler = new ProjetileHandler(player);
		EnemyHandler enemyHandler = new EnemyHandler(projetileHandler);
		CollisionHandler collisionHandler = new CollisionHandler(player, enemyHandler, projetileHandler, powerUpHandler);
		DrawHandler drawHandler = new DrawHandler(player, enemyHandler, projetileHandler, powerUpHandler);

		/* Inicialização da janela do jogo */
		GameLib.initGraphics();

		/* Início do jogo, começa apenas quando pega a primeira arma */
		powerUpHandler.addStartGame();
		while(!player.hasGun()){
			/* Atualização dos tempos */
			delta = System.currentTimeMillis() - currentTime;
			currentTime = System.currentTimeMillis();
			
			/* Verificação e atualização do jogo*/
			collisionHandler.checkCollision(currentTime, player.getState());
			powerUpHandler.Update(currentTime, delta, player);
			player.CheckMoviment(delta);

			/* Desenho da cena parada no tempo */
			drawHandler.continueBackground(0);
			drawHandler.drawEntities(currentTime);

			/* Atualização do display e pausa do jogo */
			GameLib.display();
			busyWait(currentTime + 5);
		}

		/* Começa o jogo em si, começando com a primeira arma */
		while(running){
		
			/* Atualização do delta (variação do tempo desde a última atualização) */
			delta = System.currentTimeMillis() - currentTime;
			
			/* Atualização do timestamp atual. */
			currentTime = System.currentTimeMillis();

			/* Verificação de colisões */
			collisionHandler.checkCollision(currentTime, player.getState());

			/* Atualizações de estados */
			projetileHandler.CheckShoots(delta);
			enemyHandler.Update(currentTime, delta, player.getPosY());
			powerUpHandler.Update(currentTime, delta, player);
			enemyHandler.Add(currentTime);
			powerUpHandler.Add(currentTime);
			player.checkRevive(currentTime);

			/* Verificando entrada do usuário (teclado) */
			if(player.getState() == States.ACTIVE || player.getState() == States.DAMAGED){
				player.CheckMoviment(delta);
				if(GameLib.iskeyPressed(GameLib.KEY_CONTROL) && player.getState() == States.ACTIVE){
					projetileHandler.DispararPlayer(currentTime);
				}
			}
			if(GameLib.iskeyPressed(GameLib.KEY_ESCAPE)) running = false;

			/* Desenho da cena */
			drawHandler.continueBackground(delta);
			drawHandler.drawEntities(currentTime);
			
			/* Atualização do desenho exibido pela interface do jogo */
			GameLib.display();
			
			/* Pausa de modo que cada execução do laço do main loop demore aproximadamente 5 ms */
			busyWait(currentTime + 5);
		}
		
		System.exit(0);
	}
}
