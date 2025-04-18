package components.enemies;

import java.awt.*;
import java.util.ArrayList;
import components.Projectile;
import entity.Entity;
import mainpackage.GameLib;
import mainpackage.States;

public class Circle extends Entity implements IEnemy{
    private double angle;
    private double angleSpeed;
    private double projetileRadius;
    protected static long nextEnemy = System.currentTimeMillis() + 2000;

    public Circle(long currentTime){
        super(States.ACTIVE, Math.random() * (GameLib.WIDTH - 20.0) + 10.0, -10.0, 0,0.20 + Math.random() * 0.15,0,0, currentTime + 500, 9);
        this.angle = 3 * Math.PI / 2;
        this.angleSpeed = 0;
        this.projetileRadius = 2.0;
        Circle.nextEnemy = currentTime + 500;
    }

    public static long getNextEnemy(){
        return nextEnemy;
    }

    public void walk(long delta) {
        walkX(getSpeedX() * Math.cos(this.angle) * delta);
        walkY(getSpeedY() * Math.sin(this.angle) * delta * (-1.0));
        this.angle += this.angleSpeed * delta;
    }

    public Boolean exploded(long currentTime) {
        return this.state == States.EXPLODING && currentTime > this.explosion_end;
    }

    public Boolean leaveScreen(){
        return getPosY() > GameLib.HEIGHT + 10;
    }

    public ArrayList<Projectile> Shoot(long currentTime, long delta) {
        ArrayList<Projectile> projectiles = new ArrayList<>();

        double projX = getPosX();
        double projY = getPosY();
        double projSpeedX = Math.cos(this.angle) * 0.45;
        double projSpeedY = Math.sin(this.angle) * 0.45 * (-1.0);
        this.next_shot = (long) (currentTime + 200 + Math.random() * 500);

        Projectile proj = new Projectile(this.projetileRadius, projX, projY, projSpeedX, projSpeedY);
        projectiles.add(proj);
        return projectiles;
    }

    public Boolean getNextShoot(long currentTime){
        return this.next_shot < currentTime;
    }

    public void kill(long currenTime){
        this.state = States.EXPLODING;
        this.explosion_start = currenTime;
        this.explosion_end = currenTime + 500;
    }

    public void draw(double currentTime){
        if(this.state == States.EXPLODING){
            double alpha = (currentTime - this.explosion_start) / (this.explosion_end - this.explosion_start);
            GameLib.drawExplosion(this.getPosX(), this.getPosY(), alpha);
        }
        else{
            GameLib.setColor(Color.CYAN);
            GameLib.drawCircle(this.getPosX(), this.getPosY(), this.getRadius());
        }
    }
}
