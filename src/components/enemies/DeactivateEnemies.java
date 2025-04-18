package components.enemies;

public class DeactivateEnemies {
    public static void Deactivate() {
        Circle.nextEnemy = Long.MAX_VALUE;
        Square.nextSquare = Long.MAX_VALUE;
    }
    public static void Activate(long currentTime) {
        Circle.nextEnemy = currentTime + 500;
        Square.nextSquare = currentTime + 1000;
    }
}
