package demolition;

/**
 * Object coordinate in this game
 */
public class Coordinate { 
    /* x-coordinate*/ 
    private int x;   
    /* y-coordinate*/ 
    private int y;   
   
    /**
    * Creates and initializes a point with given (x, y)
    *
    * @param x Object x-coordinate.
    * @param y Object y-coordinate.
    */
    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
    * Return object x-coordinate.
    * 
    * @return Object x-coordinate.
    */
    public int getX() { return(this.x); }

    /**
    * Return object y-coordinate.
    * 
    * @return Object y-coordinate.
    */
    public int getY() { return(this.y); }

    /**
    * Print the object by format "x y".
    * 
    */
    public String toString() {
        return String.valueOf(this.x) + ' ' + String.valueOf(this.y);
    }

    
    @Override
    public int hashCode() {
        int result = 17;
        result = 37*result+ this.y;
        
        return result;
    }

    @Override
    public boolean equals(Object obj){
        
        if (obj != null) {
            if (obj instanceof Coordinate) {
                Coordinate coordinate = (Coordinate) obj;
                return this.x == coordinate.getX() && this.y == coordinate.getY();
            }
        }
        return false;     
    }

}