package demolition;

/**
 * Represents an StillObject object.
 */
public class StillObject extends GameObject {

     /* If StillObject broken*/ 
    private boolean isBroken;
     /* This StillObject type*/ 
    private String type;

    /**
     * Creates a new StillObject object.
     * 
     * @param x The x-coordinate.
     * @param y The y-coordinate.
     * @param type The type of StillObject.
     * @param isBroken The StillObject break or not.
     */
    public StillObject(int x, int y, String type, boolean isBroken) {
        super(x, y);
        this.isBroken = isBroken;
        this.type = type;
    }

    /**
     * If StillObject object is broken
     * 
     * @return If StillObject object is broken
     */
    public boolean isBroken() {
        return this.isBroken;
    }

    /**
     * Change StillObject type from broken to empty.
     */
    public void brokenToEmpty() {
        this.type = "empty";
        this.isBroken = true;
    }

    /**
     * Return this StillObject current type.
     * @return this StillObject current type.
     */
    public String getType() {
        return this.type;
    }

    /**
     * Updates the StillObject every frame.
     */
    public void tick() {
        
    }
}