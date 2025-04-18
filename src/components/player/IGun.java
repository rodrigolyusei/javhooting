package components.player;

import java.util.ArrayList;
import components.Projectile;

interface IGun {
    ArrayList<Projectile> shoot(long currentTime, double player_x, double player_y, double player_radius);
    long getNextShoot();
    void remove(Player player);
}
