package entity;

import components.Position;
import mainpackage.States;

public abstract class Entity {
    protected long next_shot;
    protected double explosion_start;
    protected double explosion_end;
    protected double radius;
    protected States state;
    private Position position;

    public Entity(States state, double entityPosX, double entityPosY, double entitySpeedX, double entitySpeedY, double explosion_start, double explosion_end, long next_shot, double radius){
        this.next_shot = next_shot;
        this.explosion_start = explosion_start;
        this.explosion_end = explosion_end;
        this.radius = radius;
        this.state = state;
        this.position = new Position(entityPosX, entityPosY, entitySpeedX, entitySpeedY);
    }
    
    public double getPosY(){
        return this.position.getPosY();
    }

    public double getPosX(){
        return this.position.getPosX();
    }

    public double getExplosionEnd(){
        return this.explosion_end;
    }

    public double getExplosionStart(){
        return this.explosion_start;
    }

    public double getRadius(){
        return this.radius;
    }

    public States getState() {
        return this.state;
    }

    public double getSpeedX(){
        return this.position.getSpeedX();
    }

    public double getSpeedY(){
        return this.position.getSpeedY();
    }

    public void setSpeedX(double speedX){
        this.position.setSpeedX(speedX);
    }

    public void setSpeedY(double speedY){
        this.position.setSpeedY(speedY);
    }

    public void walkX(double distance){
        position.walkX(distance);
    }

    public void walkY(double distance){
        position.walkY(distance);
    }

    public abstract void draw(double currentTime);
}
