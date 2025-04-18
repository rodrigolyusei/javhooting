package components.enemies;

import java.util.ArrayList;
import components.Projectile;

public interface IEnemy {
    void walk(long delta);
    Boolean exploded(long currentTime);
    Boolean leaveScreen();
    ArrayList<Projectile> Shoot(long currentTime, long delta);
    Boolean getNextShoot(long currentTime);
    double getPosY();
    double getRadius();
    double getPosX();
    void kill(long currentTime);
    void draw(double currentTime);
}