package components.player;

import entity.Entity;
import java.awt.*;
import mainpackage.GameLib;
import mainpackage.States;

public class Speed extends Entity implements IPowerup {
    private double angleSpeed;
    private double angle;
    private static long nextPowerUp = System.currentTimeMillis() + 6000;
    private double end_powerup;
    private Boolean ativo;
    
    public Speed (Long currentTime) {
        super (States.ACTIVE, (GameLib.WIDTH)*Math.random(), (GameLib.HEIGHT/3)*Math.random(),
        0, 0.20 + Math.random() * 0.15,0,0,
        0, 5);
        this.angle = 3 * Math.PI / 2;
        this.angleSpeed = 0;
        this.end_powerup = 0;
        this.ativo = false;
        Speed.nextPowerUp = currentTime + 3000;
    }

    @Override
    public void walk(long delta) {
        walkX(getSpeedX() * Math.cos(this.angle) * delta);
        walkY(getSpeedY() * Math.sin(this.angle) * delta * (-1.0));
        this.angle += this.angleSpeed * delta;
    }

    @Override
    public Boolean exploded(long currentTime) {
        return this.state == States.EXPLODING && currentTime > this.explosion_end;
    }

    @Override
    public Boolean leaveScreen(){
        return getPosY() > GameLib.HEIGHT + 10;
    }

    @Override
    public double getEndPowerUp() {
        return this.end_powerup;
    }

    @Override
    public Boolean isActive() {
        return this.ativo;
    }

    public static Long getNextPowerUp(){
        return nextPowerUp;
    }

    @Override
    public void apply(long currenTime, Player player){
        this.ativo = true;
        this.end_powerup = currenTime + 4000;
        this.state = States.EXPLODING;
        this.explosion_start = currenTime;
        this.explosion_end = currenTime + 100;
        player.setSpeedX(Player.defaultSpeed * 1.3);
        player.setSpeedY(Player.defaultSpeed * 1.3);
        System.out.println("CONSEGUI UM SPEED!!!!!!!!");
    }

    @Override
    public void remove(Player player){
        this.ativo = false;
        player.setSpeedX(Player.defaultSpeed);
        player.setSpeedY(Player.defaultSpeed);
        System.out.println("perdi o speed...");
    }

    @Override
    public void draw(double currentTime){
        if(this.state == States.EXPLODING){
            if(this.explosion_end < currentTime){
                this.state = States.INACTIVE;
                return;
            }
            double alpha = (currentTime - this.explosion_start) / (this.explosion_end - this.explosion_start);
            GameLib.drawExplosion(this.getPosX(), this.getPosY(), alpha);
        }
        else if(this.state != States.INACTIVE){
            GameLib.setColor(Color.PINK);
            GameLib.drawDiamond(this.getPosX(), this.getPosY(), this.getRadius());
        }
    }
}
