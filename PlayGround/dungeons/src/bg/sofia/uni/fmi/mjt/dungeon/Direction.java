package bg.sofia.uni.fmi.mjt.dungeon;

public enum Direction {
    LEFT(0),
    UP(1),
    DOWN(2),
    RIGHT(3);

    Direction(int directionCode) {
        this.directionCode = directionCode;
    }
    public int getDirectionCode(){
        return directionCode;
    }
    private final int directionCode;
}
