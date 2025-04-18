package components.handlers;

import java.awt.*;
import components.enemies.IEnemy;
import components.player.IPowerup;
import components.player.Player;
import components.Background;
import components.Projectile;
import mainpackage.GameLib;

public class DrawHandler {
    private Background proximo;
    private Background distante;
    private Player player;
    private EnemyHandler enemyHandler;
    private ProjetileHandler projectiles;
    private PowerUpHandler powerUpHandler;

    public DrawHandler(Player player, EnemyHandler enemyHandler, ProjetileHandler projectiles, PowerUpHandler powerUpHandler) {
        this.proximo = new Background(20, 0.07, 0.0);
        this.distante = new Background(50, 0.45, 0.0);
        this.player = player;
        this.enemyHandler = enemyHandler;
        this.projectiles = projectiles;
        this.powerUpHandler = powerUpHandler;
    }

    public void continueBackground(long delta){
        this.distante.draw(delta, Color.DARK_GRAY);
        this.proximo.draw(delta, Color.GRAY);
    }

    public void drawEntities(double currentTime){
        this.player.draw(currentTime);
        drawPlayerProjectiles();
        drawEnemiesProjectiles();
        drawEnemies(currentTime);
        drawPowerUps(currentTime);
    }

    private void drawPlayerProjectiles(){
        for(Projectile proj : this.projectiles.playerProjectileList){
            GameLib.setColor(Color.GREEN);
            GameLib.drawLine(proj.getPosX(), proj.getPosY() - 5, proj.getPosX(), proj.getPosY() + 5);
            GameLib.drawLine(proj.getPosX() - 1, proj.getPosY() - 3, proj.getPosX()- 1, proj.getPosY() + 3);
            GameLib.drawLine(proj.getPosX() + 1, proj.getPosY() - 3, proj.getPosX() + 1,proj.getPosY() + 3);
        }
    }

    private void drawEnemiesProjectiles(){
        for(Projectile proj : this.projectiles.enemyProjectileList){
            GameLib.setColor(Color.RED);
            GameLib.drawCircle(proj.getPosX(), proj.getPosY(), proj.getRadius());
        }
    }

    private void drawEnemies(double currentTime){
        for(IEnemy enemy : this.enemyHandler.enemies){
            enemy.draw(currentTime);
        }
    }

    private void drawPowerUps(double currentTime){
        for(IPowerup powerup : powerUpHandler.powerups){
            powerup.draw(currentTime);
        }
    }
}
