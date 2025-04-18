package components.enemies;

import java.awt.*;
import java.util.ArrayList;
import components.Projectile;
import entity.Entity;
import mainpackage.GameLib;
import mainpackage.States;

public class BigSquare extends Entity implements IEnemy {
    private double projetileRadius;
    private double direction = -1;
    private double life;
    private double damage_end;
    private double flash;
    private double minimumY;
    protected static Boolean spawned = false;
    protected static long nextPreparation = System.currentTimeMillis() + 20000;

    public BigSquare(long currentTime){
        super(States.ACTIVE, GameLib.WIDTH / 2, -20, 0.3,0.5,0,0, currentTime + 500, 40);
        spawned = true;
        this.projetileRadius = 4.0;
        this.life = 500;
        this.damage_end = 0;
        this.flash = 0;
        minimumY = 90;
    }

    public static Boolean alreadySpawned(){
        return spawned;
    }

    public static double getNextPreparation(){
        return nextPreparation;
    }

    public void walk(long delta) { //
        if(getPosY() < this.minimumY){
            walkY(getSpeedY()*delta);
            this.state = States.INACTIVE;
            return;
        }
        if(getPosX() > GameLib.WIDTH - 40){
            this.direction = -1;
        }
        else if(getPosX() < 60){
            this.direction = 1;
        }
        walkX(getSpeedX() * delta * this.direction);
    }

    public Boolean exploded(long currentTime) {
        Boolean exploded = this.state == States.EXPLODING && currentTime > this.explosion_end;
        if(this.state != States.EXPLODING && currentTime > this.damage_end){
            this.state = States.ACTIVE;
        }
        return exploded;
    }

    public Boolean leaveScreen(){
        return getPosX() < -10 || getPosX() > GameLib.WIDTH + 10;
    }

    public ArrayList<Projectile> Shoot(long currentTime, long delta) {
        double [] angles = {
                Math.PI/2 - 2 * Math.PI/8,
                Math.PI/2 + Math.PI/8,
                Math.PI/2,
                Math.PI/2 - Math.PI/8,
                Math.PI/2 - 2 * Math.PI/8
        };

        ArrayList<Projectile> projectiles = new ArrayList<>();
        for(double angle : angles){
            double a = angle + Math.random() * Math.PI/6 - Math.PI/12;
            double projX = getPosX();
            double projY = getPosY();
            double projSpeedX = Math.cos(a) * 0.30;
            double projSpeedY = Math.sin(a) * 0.80;
            projectiles.add(new Projectile(this.projetileRadius, projX, projY, projSpeedX, projSpeedY));
        }
        this.next_shot = (long) (currentTime + 200 +Math.random() * 500);
        return projectiles;
    }

    public Boolean getNextShoot(long currentTime){
        if(this.getPosY() < this.minimumY) return false;
        return next_shot < currentTime;
    }

    public void kill(long currenTime){
        if(this.state == States.INACTIVE) return;
        if (this.life > 1){
            this.life--;
            this.state = States.DAMAGED;
            this.damage_end = currenTime + 100;
        }
        else{
            DeactivateEnemies.Activate(currenTime);
            this.state = States.EXPLODING;
            this.explosion_start = currenTime;
            this.explosion_end = currenTime + 500;
            nextPreparation = currenTime + 20000;
            spawned = false;
        }
    }

    public void draw(double currentTime){
        if(this.state == States.EXPLODING){
            double alpha = (currentTime - this.explosion_start) / (this.explosion_end - this.explosion_start);
            GameLib.drawExplosion(this.getPosX(), this.getPosY(), alpha);
        }
        else if(this.getState() == States.DAMAGED){
            if(flash > currentTime){
                GameLib.setColor(Color.WHITE);
            }
            else{
                GameLib.setColor(Color.BLUE);
                flash = currentTime + 20;
            }
            GameLib.drawDiamond(this.getPosX(), this.getPosY(), this.getRadius());
        }
        else{
            GameLib.setColor(Color.MAGENTA);
            GameLib.drawDiamond(this.getPosX(), this.getPosY(), this.getRadius());
        }
    }
}
