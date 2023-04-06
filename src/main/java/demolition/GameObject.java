package demolition;

import processing.core.PImage;
import processing.core.PApplet;

/**
 * Represents an abstract GameObject object that is inherited by MovingObject and StillObject.
 */
public abstract class GameObject {

    /**
     * The GameObject's x-coordinate.
     */
    protected int x;
    
    /**
     * The GameObject's y-coordinate.
     */
    protected int y;

    /**
     * The GameObject's sprite.
     */
    private PImage sprite;

    /**
     * Creates a new GameObject object.
     * 
     * @param x The x-coordinate.
     * @param y The y-coordinate.
     */
    public GameObject(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Sets the GameObject's sprite.
     * 
     * @param sprite The new sprite to use.
     */
    public void setSprite(PImage sprite) {
        this.sprite = sprite;
    }

    /**
     * Updates the GameObject every frame.
     */
    public abstract void tick();

    /**
     * Draws the GameObject to the screen.
     * 
     * @param app The window to draw onto.
     */
    public void draw(PApplet app) {
        // The image() method is used to draw PImages onto the screen.
        // The first argument is the image, the second and third arguments are coordinates
        app.image(this.sprite, this.x, this.y);
    }

    /**
     * Gets the x-coordinate.
     * @return The x-coordinate.
     */
    public int getX() {
        return this.x;
    }

    /**
     * Returns the y-coordinate.
     * @return The y-coordinate.
     */
    public int getY() {
        return this.y;
    }

    /**
     * Returns the Coordinate of this gameObject.
     * @return The Coordinate.
     */
    public int[] getCoordinate() {
        int[] coordinate = new int[2];
        coordinate[0] = this.x;
        coordinate[1] = this.y;
        return coordinate;
    }

}