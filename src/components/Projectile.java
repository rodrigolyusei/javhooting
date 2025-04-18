package components;

import mainpackage.GameLib;

public class Projectile {
    private double radius;
    private Position position;

    public Projectile(double radius, double posX, double posY, double speedX, double speedY) {
        this.radius = radius;
        this.position = new Position(posX, posY, speedX, speedY);
    }
    
    public double getRadius(){
        return this.radius;
    }

    public double getPosX(){
        return this.position.getPosX();
    }

    public double getPosY(){
        return this.position.getPosY();
    }

    public void andar(double delta){
        this.position.walkX(this.position.getSpeedX() * delta);
        this.position.walkY(this.position.getSpeedY() * delta);
    }

    public Boolean isValid(){
        return !(this.position.getPosY() < 0 || this.position.getPosY() > GameLib.HEIGHT);
    }
}
