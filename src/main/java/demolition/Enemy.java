package demolition;

/**
 * Handles enemy performance.
 */
public class Enemy extends MovingObject {
    private char color;

    /**
    * Creates a new enemy object.
    *
    * @param x Enemy x-coordinate.
    * @param y Enemy y-coordinate.
    * @param color Enemy's color(Yellow || Red).
    */
    public Enemy(int x, int y, char color) {
        super(x, y);
        this.lives = 1;
        this.color = color;
    }

    /**
    * Return this enemy's color.
    * 
    * @return this enemy's color
    */
    public char getColor() {
        return this.color;
    }

    /**
    * Return this enemy current lives.
    * 
    * @return this enemy current lives
    */
    public int getLives() {
        return this.lives;
    }

    /**
    * This enemy lose one life.
    * 
    */
    public void loseLife() {
        this.lives--;
    }

    /**
    * Check if player collide with this enemy.
    * 
    * @param manager Manager class of this game.
    *
    * @return True: Player collide with this enemy. False: Player is not collide with this enemy.
    */
    public boolean checkPlayerInTouch(Manager manager) {

        if (this.x == manager.getBombGuy().getX() && this.y == manager.getBombGuy().getY()) {
            manager.getBombGuy().loseLife();
            return true;
        }
        
        return false;
    }

}