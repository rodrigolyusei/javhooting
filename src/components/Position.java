package components;

public class Position {
    private double posX;
    private double posY;
    private double speedX;
    private double speedY;

    public Position(double posX, double posY, double speedX, double speedY) {
        this.posX = posX;
        this.posY = posY;
        this.speedX = speedX;
        this.speedY = speedY;
    }

    public double getPosX() {
        return this.posX;
    }

    public double getPosY(){
        return this.posY;
    }

    public double getSpeedX() {
        return this.speedX;
    }

    public double getSpeedY() {
        return this.speedY;
    }

    public void setSpeedX(double speedX) {
        this.speedX = speedX;
    }

    public void setSpeedY(double speedY) {
        this.speedY = speedY;
    }

    public void walkX(double distance) {
        this.posX += distance;
    }
    
    public void walkY(double distance) {
        this.posY += distance;
    }
}