package components.player;

import mainpackage.States;

public interface IPowerup {
    void walk(long delta);
    Boolean exploded(long currentTime);
    Boolean leaveScreen();
    double getPosX();
    double getPosY();
    double getRadius();
    double getEndPowerUp();
    States getState();
    Boolean isActive();
    void apply(long currentTime, Player player);
    void draw(double currentTime);
    void remove(Player player);
}