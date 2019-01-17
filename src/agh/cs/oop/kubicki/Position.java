package agh.cs.oop.kubicki;

public class Position {
    private int x;
    private int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return  "x=" + x +
                ", y=" + y;
    }

    public Position addPosition(Position position){
        return new Position(position.getX()+this.x,position.getY()+this.y);
    }

    @Override
    public boolean equals(Object other){
        if (this == other) return true;
        if (!(other instanceof Position)) return false;
        Position that = (Position) other;
        if(this.x == that.x && this.y == that.y) return true;
        else return false;
    }

    @Override
    public int hashCode(){
        int hash = 42;
        hash += this.x * 7;
        hash += this.y * 6;
        return hash;
    }

}
