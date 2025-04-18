package components.enemies;

import components.Projectile;
import entity.Entity;
import java.awt.*;
import java.util.ArrayList;
import mainpackage.GameLib;
import mainpackage.States;

public class Square extends Entity implements IEnemy{
    private Boolean shootNow = false;
    private double angle;
    private double angleSpeed;
    private double projetileRadius;
    private static double squareCounter = 0;
    private static double path = GameLib.HEIGHT *0.2;
    protected static long nextSquare = System.currentTimeMillis()+ 4000;

    public Square(long currentTime, double spawnX){
        super(States.ACTIVE, spawnX, -10.0, 0,0.42,0,0, currentTime + 500, 12);
        this.angle = 3 * Math.PI / 2;
        this.angleSpeed = 0;
        this.projetileRadius = 2.0;
    }

    public static long getNextSquare(){
        return nextSquare;
    }

    public static Square CreateSquare(long currentTime){
        if (squareCounter < 9){
            squareCounter++;
            nextSquare = currentTime + 120;
            return new Square(currentTime, path);
        }
        squareCounter = 0;
        nextSquare = (long) (currentTime + 3000 + Math.random() * 3000);
        Square newSquare = new Square(currentTime, path);
        path = Math.random() > 0.5 ? GameLib.WIDTH * 0.2 : GameLib.WIDTH * 0.8;
        return newSquare;
    }


    @Override
    public void walk(long delta) {
        this.shootNow = false;
        double previousY = getPosY();
        walkX(getSpeedY() * Math.cos(this.angle) * delta);
        walkY(getSpeedY() * Math.sin(this.angle) * delta * (-1.0));
        this.angle += this.angleSpeed * delta;

        double threshold = GameLib.HEIGHT * 0.30;

        if(previousY < threshold && getPosY() >= threshold) {
            if(this.getPosX() < GameLib.WIDTH / 2) this.angleSpeed = 0.003;
            else this.angleSpeed = -0.003;
        }
        if(this.angleSpeed > 0 && Math.abs(this.angle - 3 * Math.PI) < 0.05){
            this.angleSpeed = 0.0;
            this.angle = 3 * Math.PI;
            this.shootNow = true;
        }
        if(this.angleSpeed< 0 && Math.abs(this.angle) < 0.05){
            this.angleSpeed = 0.0;
            this.angle = 0.0;
            this.shootNow = true;
        }
    }

    @Override
    public Boolean exploded(long currentTime) {
        return this.state == States.EXPLODING && currentTime > this.explosion_end;
    }

    @Override
    public Boolean leaveScreen(){
        return getPosX() < -10 || getPosX() > GameLib.WIDTH + 10;
    }

    @Override
    public ArrayList<Projectile> Shoot(long currentTime, long delta) {
        double [] angles = { Math.PI/2 + Math.PI/8, Math.PI/2, Math.PI/2 - Math.PI/8 };

        ArrayList<Projectile> projectiles = new ArrayList<>();

        for(double angle : angles){
            double a = angle + Math.random() * Math.PI/6 - Math.PI/12;
            double projX = getPosX();
            double projY = getPosY();
            double projSpeedX = Math.cos(a) * 0.30;
            double projSpeedY = Math.sin(a) * 0.30;
            projectiles.add(new Projectile(this.projetileRadius, projX, projY, projSpeedX, projSpeedY));
        }
        this.next_shot = (long) (currentTime + 200 +Math.random() * 500);
        return projectiles;
    }
    @Override
    public Boolean getNextShoot(long currentTime){
        return shootNow;
    }

    @Override
    public void kill(long currenTime){
        this.state = States.EXPLODING;
        this.explosion_start = currenTime;
        this.explosion_end = currenTime + 500;
    }
    @Override
    public void draw(double currentTime){
        if(this.state == States.EXPLODING){
            double alpha = (currentTime - this.explosion_start) / (this.explosion_end - this.explosion_start);
            GameLib.drawExplosion(this.getPosX(), this.getPosY(), alpha);
        }
        else{
            GameLib.setColor(Color.MAGENTA);
            GameLib.drawDiamond(this.getPosX(), this.getPosY(), this.getRadius());
        }
    }
}