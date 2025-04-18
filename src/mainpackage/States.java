package mainpackage;

public enum States {
    INACTIVE(0),
    ACTIVE(1),
    DAMAGED(2),
    EXPLODING(3);

    private final int value;

    States(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}