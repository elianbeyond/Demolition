package demolition;

/**
 * Handles Bomb Guy(player) performance.
 */
public class BombGuy extends MovingObject {
    
    // when check the real postion, this.Y + 16!!!!!

    /**
    * Creates a new Bomb Guy object.
    *
    * @param x Bomb Guy x-coordinate.
    * @param y Bomb Guy y-coordinate.
    * @param lives Bomb Guy current lives.
    */
    public BombGuy(int x, int y, int lives) {
        super(x, y);
        this.lives = lives;
        
    }

    /**
    * Return this bomb guy current lives.
    * 
    * @return this bomb guy current lives
    */
    public int getLives() {
        return this.lives;
    }

    /**
    * This bomb guy lose one life.
    * 
    */
    public void loseLife() {
        this.lives--;
    }

    /**
    * Set coordinate of this bomb guy 
    * @param x Bomb Guy x-coordinate.
    * @param y Bomb Guy y-coordinate.
    */
    public void setCoordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
    * Set lives of this bomb guy 
    * @param lives Bomb Guy lives
    */
    public void setLives(int lives) {
        this.lives = lives;
    }

    
}