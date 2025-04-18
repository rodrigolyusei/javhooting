package components.player;

import entity.Entity;
import java.awt.*;
import mainpackage.GameLib;
import mainpackage.States;

public class Shield extends Entity implements IPowerup {
    private double angleSpeed;
    private double angle;
    private static long nextPowerUp = System.currentTimeMillis() + 6000;
    private double end_powerup;
    private Boolean ativo;
    private int direction = 1;
    private final int shieldValue = 3;

    public Shield(Long currentTime) {
        super (States.ACTIVE, (GameLib.WIDTH)*Math.random(), (GameLib.HEIGHT/3)*Math.random(),
        0.1, 0.10 + Math.random() * 0.15,0,0,
        0, 5);
        this.angle = 3 * Math.PI / 2;
        this.angleSpeed = 0;
        this.end_powerup = 0;
        this.ativo = false;
        Shield.nextPowerUp = Long.MAX_VALUE;
        System.out.println("APARECEU UM SHIELD!!!");
    }

    @Override
    public void walk(long delta) {
        if(getPosX() > GameLib.WIDTH - 40){
            this.direction = -1;
        }
        else if(getPosX() < 60){
            this.direction = 1;
        }
        walkX(getSpeedX() *Math.cos(this.angle) * delta * this.direction);
        walkY(getSpeedY() * Math.sin(this.angle) * delta * (-1.0));
        this.angle += this.angleSpeed * delta;
    }

    @Override
    public Boolean exploded(long currentTime) {
        return this.state == States.EXPLODING && currentTime > this.explosion_end;
    }

    @Override
    public Boolean leaveScreen(){
        Boolean leaved = getPosY() > GameLib.HEIGHT + 10;
        if (leaved) Shield.nextPowerUp = System.currentTimeMillis() + 6000;
        return leaved;
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
        this.end_powerup = currenTime + 20000;
        this.state = States.EXPLODING;
        this.explosion_start = currenTime;
        this.explosion_end = currenTime + 100;
        player.setShield(this.shieldValue);
        System.out.println("CONSEGUI UM SHIELD!!!!!!!!");
    }

    @Override
    public void remove(Player player){
        this.ativo = false;
        player.setShield(0);
        nextPowerUp = System.currentTimeMillis() + 4000;
        System.out.println("perdi o shield...");
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
            GameLib.setColor(Color.YELLOW);
            GameLib.drawDiamond(this.getPosX(), this.getPosY(), this.getRadius());
        }
    }
}
