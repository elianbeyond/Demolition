package demolition;

import processing.core.PImage;
import processing.core.PApplet;

/**
 * Represents an abstract MovingObject object that is inherited by BombGuy and Enemy.
 */
public abstract class MovingObject extends GameObject {

    /**
     * The MovingObject's current lives.
     */
    protected int lives;
    /**
     * The MovingObject's current facing direction.
     */
    protected char direction;
    /**
     * The MovingObject's direction which going to move.
     */
    protected char moveDirection;
    /**
     * The MovingObject's left sprites.
     */
    protected PImage[] leftSprites;
    /**
     * The MovingObject's right sprites.
     */
    protected PImage[] rightSprites;
    /**
     * The MovingObject's up sprites.
     */
    protected PImage[] upSprites;
    /**
     * The MovingObject's down sprites.
     */
    protected PImage[] downSprites;

     /**
     * Creates a new MovingObject object.
     * 
     * @param x The x-coordinate.
     * @param y The y-coordinate.
     */
    public MovingObject(int x, int y) {
        super(x, y);
        this.direction = 'D';
        this.moveDirection = ' ';
        this.leftSprites = new PImage[4];
        this.rightSprites = new PImage[4];
        this.upSprites = new PImage[4];
        this.downSprites = new PImage[4];
    }

    /**
     * Sets the MovingObject's left sprites.
     * 
     * @param imageOne The new sprite to use.
     * @param imageTwo The new sprite to use.
     * @param imageThree The new sprite to use.
     * @param imageFour The new sprite to use.
     */
    public void setLeftSprites(PImage imageOne,  PImage imageTwo, PImage imageThree, PImage imageFour) {
        this.leftSprites[0] = imageOne;
        this.leftSprites[1] = imageTwo;
        this.leftSprites[2] = imageThree;
        this.leftSprites[3] = imageFour;
    }

     /**
     * Sets the MovingObject's left sprites.
     * 
     * @param imageOne The new sprite to use.
     * @param imageTwo The new sprite to use.
     * @param imageThree The new sprite to use.
     * @param imageFour The new sprite to use.
     */
    public void setRightSprites(PImage imageOne,  PImage imageTwo, PImage imageThree, PImage imageFour) {
        this.rightSprites[0] = imageOne;
        this.rightSprites[1] = imageTwo;
        this.rightSprites[2] = imageThree;
        this.rightSprites[3] = imageFour;
    }

     /**
     * Sets the MovingObject's left sprites.
     * 
     * @param imageOne The new sprite to use.
     * @param imageTwo The new sprite to use.
     * @param imageThree The new sprite to use.
     * @param imageFour The new sprite to use.
     */
    public void setUpSprites(PImage imageOne,  PImage imageTwo, PImage imageThree, PImage imageFour) {
        this.upSprites[0] = imageOne;
        this.upSprites[1] = imageTwo;
        this.upSprites[2] = imageThree;
        this.upSprites[3] = imageFour;
    }

     /**
     * Sets the MovingObject's left sprites.
     * 
     * @param imageOne The new sprite to use.
     * @param imageTwo The new sprite to use.
     * @param imageThree The new sprite to use.
     * @param imageFour The new sprite to use.
     */
    public void setDownSprites(PImage imageOne,  PImage imageTwo, PImage imageThree, PImage imageFour) {
        this.downSprites[0] = imageOne;
        this.downSprites[1] = imageTwo;
        this.downSprites[2] = imageThree;
        this.downSprites[3] = imageFour;
    }

    /**
     * MovingObject move left.
     */
    public void moveLeft() {
        this.moveDirection = 'L';
        this.direction = 'L';
    }

    /**
     * MovingObject move right.
     */
    public void moveRight() {
        this.moveDirection = 'R';
        this.direction = 'R';
    }

    /**
     * MovingObject move up.
     */
    public void moveUp() {
        this.moveDirection = 'U';
        this.direction = 'U';
    }

    /**
     * MovingObject move down.
     */
    public void moveDown() {
        this.moveDirection = 'D';
        this.direction = 'D';
    }

    /**
     * Get MovingObject direction
     * 
     * @return this Moving Object current direction
     */
    public char getDirection() {
        return this.direction;
    }

    /**
     * Get MovingObject moving direction
     * 
     * @return this Moving Object current moving direction
     */
    public char getMoveDirection() {
        return this.moveDirection;
    }

    /**
     * Set MovingObject moving direction
     * 
     * @param direction MovingObject direction going to move
     */
    public void setMoveDirection(char direction) {
        this.moveDirection = direction;
    }

    /**
     * Set MovingObject facing direction
     * 
     * @param direction MovingObject facing direction
     */
    public void setDirection(char direction) {
        this.direction = direction;
    }

    /**
     * Updates the MovingObject every frame.
     */
    public void tick() {
        if (this.moveDirection == 'L') {
            this.x -= 32;
        } else if (this.moveDirection == 'R') {
            this.x += 32;
        } else if (this.moveDirection == 'U') {
            this.y -= 32;
        } else if (this.moveDirection == 'D') {
            this.y += 32;
        }

        this.moveDirection = ' ';
    }


    /**
     * Draws the MovingObject to the screen.
     * 
     * @param app The window to draw onto.
     */
    public void draw(PApplet app, int frameNum) {

        if (this.direction == 'L') {
            app.image(this.leftSprites[frameNum], this.x, this.y);
        } else if (this.direction == 'R') {
            app.image(this.rightSprites[frameNum], this.x, this.y);
        } else if (this.direction == 'U') {
            app.image(this.upSprites[frameNum], this.x, this.y);
        } else if (this.direction == 'D') {
            app.image(this.downSprites[frameNum], this.x, this.y);
        }   
    }

}