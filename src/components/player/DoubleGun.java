package components.player;

import components.Projectile;
import entity.Entity;
import java.awt.*;
import java.util.ArrayList;
import mainpackage.GameLib;
import mainpackage.States;

public class DoubleGun extends Entity implements IPowerup, IGun {
    private double projectile_radius;
    private double end_powerup;
    private static long nextPowerUp = System.currentTimeMillis() + 6000;
    private Boolean ativo;

    public DoubleGun(long currentTime){
        super(States.ACTIVE, GameLib.WIDTH*(Math.random()/2+0.25), GameLib.HEIGHT*(Math.random()/3+0.2), 0, 0, 0, 0, 100, 10);
        projectile_radius = 2;
        this.ativo = false;
        this.end_powerup = Double.MAX_VALUE;
        DoubleGun.nextPowerUp = Long.MAX_VALUE;
    }

    public static Long getNextPowerUp(){
        return nextPowerUp;
    }

    @Override
    public ArrayList<Projectile> shoot(long currentTime, double player_x, double player_y, double player_radius){
        this.next_shot = currentTime + 100;
        ArrayList<Projectile> projectiles = new ArrayList<>();
        Projectile projectileleft = new Projectile(this.projectile_radius, player_x+player_radius, player_y - 2 * player_radius, 0, -1);
        Projectile projectileright = new Projectile(this.projectile_radius, player_x-player_radius, player_y - 2 * player_radius, 0, -1);
        projectiles.add(projectileleft);
        projectiles.add(projectileright);
        return projectiles;
    }

    @Override
    public long getNextShoot() {
        return this.next_shot;
    }

    @Override
    public void walk(long delta) {

    }

    @Override
    public Boolean exploded(long currentTime) {
        return this.state == States.EXPLODING && currentTime > this.explosion_end;
    }

    @Override
    public Boolean leaveScreen() {
        return getPosY() > GameLib.HEIGHT + 10;
    }

    @Override
    public double getEndPowerUp() {
        return this.end_powerup;
    }

    @Override
    public Boolean isActive() {
        return ativo;
    }

    @Override
    public void apply(long currentTime, Player player) {
        player.removeGun();
        this.ativo = true;
        this.state = States.EXPLODING;
        this.explosion_start = currentTime;
        this.explosion_end = currentTime + 100;
        nextPowerUp = Long.MAX_VALUE;
        this.walkX(2000);
        this.walkY(2000);
        player.setGun(this);
        System.out.println("CONSEGUI UMA ARMA!!!!!!!!");
    }

    @Override
    public void remove(Player player) {
        this.ativo = false;
        this.end_powerup = 0;
    }

    @Override
    public void draw(double currentTime) {
        if(this.state == States.EXPLODING){
            if(this.explosion_end < currentTime){
                this.state = States.INACTIVE;
                return;
            }
            double alpha = (currentTime - this.explosion_start) / (this.explosion_end - this.explosion_start);
            GameLib.drawExplosion(this.getPosX(), this.getPosY(), alpha);
        }
        else if(this.state != States.INACTIVE){
            GameLib.setColor(Color.GREEN);
            GameLib.drawDiamond(this.getPosX(), this.getPosY(), this.getRadius());
            GameLib.drawDiamond(this.getPosX(), this.getPosY(), this.getRadius() - 4);
        }
    }
}
