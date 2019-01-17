package agh.cs.oop.kubicki;


public enum Direction {
    NORTH,
    NORTH_EAST,
    EAST,
    NORTH_WEST,
    WEST,
    SOUTH,
    SOUTH_EAST,
    SOUTH_WEST;

    public Direction convertFromInt(int x) throws Exception {
        switch (x){
            case 0:
                return NORTH;
            case 1:
                return NORTH_EAST;
            case 2:
                return EAST;
            case 3:
                return SOUTH_EAST;
            case 4:
                return SOUTH;
            case 5:
                return SOUTH_WEST;
            case 6:
                return WEST;
            case 7:
                return NORTH_WEST;
            default:
                throw new IndexOutOfBoundsException("Bad direction!");
        }
    }

    public Position convertToPos() {
        int x = 0;
        int y = 0;
        switch (this) {
            case SOUTH_WEST:
                x = -1;
                y = -1;
                break;
            case SOUTH_EAST:
                x = 1;
                y = -1;
                break;
            case NORTH_WEST:
                x = -1;
                y = 1;
                break;
            case NORTH_EAST:
                x = 1;
                y = 1;
                break;
            case NORTH:
                y = 1;
                break;
            case SOUTH:
                y = -1;
                break;
            case WEST:
                x = -1;
                break;
            case EAST:
                x = 1;
                break;
        }
        return new Position(x,y);
    }
}