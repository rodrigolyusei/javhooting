package components.handlers;

import java.util.ArrayList;
import java.util.Iterator;
import components.player.*;

public class PowerUpHandler {
    protected ArrayList<IPowerup> powerups;

    public PowerUpHandler(){
        powerups = new ArrayList<>();
    }

    public void Update(long currentTime, long delta, Player player){
        for(Iterator<IPowerup> iterator = powerups.iterator(); iterator.hasNext(); ) {
            IPowerup e = iterator.next();
            if(e.getEndPowerUp() < currentTime && (e.exploded(currentTime) || e.leaveScreen())){
                if(e.isActive())
                    e.remove(player);
                iterator.remove();
            }
            else{
                e.walk(delta);
            }
        }
    }

    public void Add(long currentTime){
        if(currentTime > Speed.getNextPowerUp()) {
            this.powerups.add(new Speed(currentTime));
        }
        if(currentTime > DoubleGun.getNextPowerUp()){
            this.powerups.add(new DoubleGun(currentTime));
        }
        if(currentTime > Shield.getNextPowerUp()){
            this.powerups.add(new Shield(currentTime));
        }
    }

    public void addStartGame(){
        this.powerups.add(new DefaultGun());
    }
    
}
