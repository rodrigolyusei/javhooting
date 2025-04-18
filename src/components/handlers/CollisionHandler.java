package components.handlers;

import components.enemies.IEnemy;
import components.player.IPowerup;
import components.player.Player;
import components.Projectile;
import mainpackage.States;

public class CollisionHandler {
    private Player player;
    private EnemyHandler enemyHandler;
    private ProjetileHandler projetileHandler;
    private PowerUpHandler powerUpHandler;

    public CollisionHandler(Player player, EnemyHandler enemyHandler, ProjetileHandler projectileHandler, PowerUpHandler powerUpHandler) {
        this.player = player;
        this.enemyHandler = enemyHandler;
        this.projetileHandler = projectileHandler;
        this.powerUpHandler = powerUpHandler;
    }

    public void checkCollision(long currentTime, States playerState){
        if(playerState == States.ACTIVE){
            enemyProjectilePlayer(currentTime);
            playerEnemy(currentTime);
            playerPowerUp(currentTime);
        }
        playerProjectileEnemy(currentTime);
    }

    private void enemyProjectilePlayer(long currentTime) {
        for(Projectile proj : this.projetileHandler.enemyProjectileList){
            double dx = proj.getPosX() - this.player.getPosX();
            double dy = proj.getPosY() - this.player.getPosY();
            double dist = Math.sqrt(dx * dx + dy * dy);

            if (dist < (this.player.getRadius() + proj.getRadius()) * 0.8){
                this.player.kill(currentTime);
            }
        }
    }

    private void playerEnemy(long currentTime) {
        for(IEnemy enemy : enemyHandler.enemies){
           double dx = enemy.getPosX() - this.player.getPosX();
           double dy = enemy.getPosY() - this.player.getPosY();
           double dist = Math.sqrt(dx * dx + dy * dy);

           if(dist < (player.getRadius() + enemy.getRadius()) * 0.8){
               this.player.kill(currentTime);
           }
        }
    }

    private void playerProjectileEnemy(long currentTime) {
        for(Projectile proj : projetileHandler.playerProjectileList){
            for(IEnemy enemy : enemyHandler.enemies){
                double dx = enemy.getPosX() - proj.getPosX();
                double dy = enemy.getPosY() - proj.getPosY();
                double dist = Math.sqrt(dx * dx + dy * dy);

                if(dist < (player.getRadius() + enemy.getRadius()) * 0.8){
                    enemy.kill(currentTime);
                }
            }
        }
    }

    private void playerPowerUp(long currentTime) {
        for(IPowerup powerup : powerUpHandler.powerups){
           double dx = powerup.getPosX() - this.player.getPosX();
           double dy = powerup.getPosY() - this.player.getPosY();
           double dist = Math.sqrt(dx * dx + dy * dy);

           if(dist<(player.getRadius() + powerup.getRadius())*0.8 && powerup.getState()==States.ACTIVE){
               powerup.apply(currentTime, this.player);
           }
        }
    }

}
