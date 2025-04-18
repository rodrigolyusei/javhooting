package components;

import java.awt.*;
import java.util.ArrayList;
import mainpackage.GameLib;

public class Background {
    private ArrayList<Double> X;
    private ArrayList<Double> Y;
    private double count;
    private double speed;

    public Background(int starsCount, double speed, double count){
        this.X = new ArrayList<>(starsCount);
        this.Y = new ArrayList<>(starsCount);
        for (int i = 0; i < starsCount; i++){
            this.X.add(Math.random() * GameLib.WIDTH);
            this.Y.add(Math.random() * GameLib.HEIGHT);
        }
        this.count = count;
        this.speed = speed;
    }

    public void draw(double delta, Color color){
        GameLib.setColor(color);
        this.count += this.speed * delta;

        for(int i = 0; i < X.size(); i++){

            GameLib.fillRect(X.get(i), (Y.get(i) + count) % GameLib.HEIGHT, 2, 2);
        }
    }
}
