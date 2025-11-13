package bg.sofia.uni.fmi.mjt.dungeon.actor;

import bg.sofia.uni.fmi.mjt.dungeon.Direction;

public record Position(int row, int col) {
    /*
    public Position(int row, int col){
        this.row = row;
        this.col = col;
    }
    public int getX() {
        return row;
    }

    public int getY() {
        return col;
    }

    public void setX(int row) {
        this.row = row;
    }

    public void setY(int col) {
        this.col = col;
    }



    private int row;
    private int col;
     */
    public Position move(Direction direction){
        return switch(direction) {
            case LEFT -> new Position(this.row, this.col - 1);

            case UP -> new Position(this.row - 1, this.col);

            case DOWN -> new Position(this.row + 1, this.col);

            case RIGHT -> new Position(this.row, this.col + 1);
        };
    }

    public boolean inBounds(int x1, int y1, int x2, int y2){
        return row >= x1 && row <= x2 && col >= y1 && col <= y2;
    }

}
