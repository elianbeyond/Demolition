package demolition;

import processing.core.PImage;
import processing.core.PApplet;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Handles bomb performance. 
 */
public class Bomb extends GameObject {

    /* Bomb sprites list*/ 
    private PImage[] bombSprites;
    /* Explosion centre sprite*/ 
    private PImage explosionCentre;
    /* Explosion horizaontal sprite*/ 
    private PImage explosionHorizaontal;
    /* Explosion vertical sprite*/ 
    private PImage explosionVertical;
    /* Bomb explosion count down*/  
    private int countDown;
    /* Explosion range list*/ 
    private int[] explosionRange;

    /**
    * Creates a new Bomb object.
    *
    * @param x Bomb x-coordinate.
    * @param y Bomb y-coordinate.
    */
    public Bomb(int x, int y) {
        super(x, y);
        this.countDown = 150; // 120 for bomb, 30 for explosions.
        this.bombSprites = new PImage[8];
        this.explosionRange = new int[4];
    }

    /**
    * Set bomb sprites.
    *
    * @param images PImages of bomb
    */
    public void setBombSprites(PImage[] images) {
        this.bombSprites = images;
    }

    /**
    * Set explosion sprites.
    * @param centre Explosion centre sprite
    * @param hori Explosion horizaontal sprite
    * @param verti Explosion vertical sprite
    */
    public void setExplosionSprites(PImage centre,  PImage hori, PImage verti) {
        this.explosionCentre = centre;
        this.explosionHorizaontal = hori;
        this.explosionVertical = verti;
    }

    /**
    * Return this bomb explosion count down.
    * 
    * @return Count down of this bomb explosion
    */
    public int getCountDown() {
        return this.countDown;
    }

    /**
    * Check if the bomb need to removed from map.
    * 
    * @return True: remove. False: not remove
    */
    public boolean cleanBomb() {
        if (this.countDown > 0) {
            return false;
        } else {
            return true;
        }
    }

    /**
    * Check if the coordinate in the explosion range.
    * 
    * @param explosionRange Explosion range
    * @param x passed in object x-coordinate
    * @param y passed in object y-coordinate
    * @return True: in explosion range. False: not in explosion range.
    */
    public boolean cleanMovingObj(int[] explosionRange, int x, int y) {
        if (this.x == x) {
            if (y >= this.y - explosionRange[0] * 32 && y <= this.y + explosionRange[1] * 32) {
                return true;
            }
        }

        if (this.y == y) {
            if (x >= this.x - explosionRange[2] * 32 && x <= this.x + explosionRange[3] * 32) {
                return true;
            }
        }

        return false;
    }

    /**
    * Check if player in the explosion range.
    * 
    * @param manager Manager class of this game.
    *
    * @return True: Player in the explosion range. False: Player not in the explosion range.
    */
    public boolean checkPlayerInExplosion(Manager manager) {
        if (this.countDown > 30) {
            return false;
        }

        if (cleanMovingObj(this.explosionRange, manager.getBombGuy().getX(), manager.getBombGuy().getY() + 16)) {

            manager.getBombGuy().loseLife();

            return true;
        }
        return false;
    }

    /**
    * Check if enemies in the explosion range.
    * If in remove enemies.
    * 
    * @param manager Manager class of this game.
    */
    public void checkEnemiesInExplosion(Manager manager) {
        if (this.countDown > 30) {
            return;
        }
        
        if (manager.getEnemies().size() < 1) {
            return;
        }

        Iterator<Enemy> itr = manager.getEnemies().iterator();
        while (itr.hasNext()) {
            Enemy enemy = itr.next();
            if (cleanMovingObj(this.explosionRange, enemy.getX(), enemy.getY() + 16)) {
                itr.remove();
            }            
        } 
    }

    /**
    * Set explosion range.
    * 
    * @param explosionRange List of explosion range
    */
    public void setExplosionRange(int[] explosionRange) {
        this.explosionRange = explosionRange;
    }

    /**
    * Return explosion range.
    * 
    * @return List of explosion range
    */
    public int[] getExplosionRange() {
        return this.explosionRange;
    }

    /**
     * Updates the bomb countdown every frame.
     */
    public void tick() {
        this.countDown--;
    }

    /**
     * Draws the bomb to the screen.
     * 
     * @param app The window to draw onto.
     */
    public void draw(PApplet app) {
        if (this.countDown > 30) {
            app.image(this.bombSprites[7 - (this.countDown - 30) / 15], this.x, this.y);
        } else {
            app.image(this.explosionCentre, this.x, this.y);

            if (this.explosionRange[0] > 0) {
                app.image(this.explosionVertical, this.x, this.y - 32);
                if (this.explosionRange[0] > 1) {
                    app.image(this.explosionVertical, this.x, this.y - 64);
                }
            }

            if (this.explosionRange[1] > 0) {
                app.image(this.explosionVertical, this.x, this.y + 32);
                if (this.explosionRange[1] > 1) {
                    app.image(this.explosionVertical, this.x, this.y + 64);
                }
            }

            if (this.explosionRange[2] > 0) {
                app.image(this.explosionHorizaontal, this.x - 32, this.y);
                if (this.explosionRange[2] > 1) {
                    app.image(this.explosionHorizaontal, this.x - 64, this.y);
                }
            }

            if (this.explosionRange[3] > 0) {
                app.image(this.explosionHorizaontal, this.x + 32, this.y);
                if (this.explosionRange[3] > 1) {
                    app.image(this.explosionHorizaontal, this.x + 64, this.y);
                }
            }
            
        }
    }
}