package demolition;

import java.util.ArrayList;
import java.util.HashMap;

import processing.core.PImage;
import processing.core.PApplet;

/**
 * Handles the action of every object in this game.
 */
public class Map {
    /* Bomb guy number in the text map*/ 
    private int bombGuyNum;
    /* Text map from config json file*/ 
    private char[][] textMap;
    /* A hashmap stored every stillObject in this may with key:Coordinate and value: StillObject*/ 
    private HashMap<Coordinate, StillObject> stillObjects;
    /* ArrayList stored the Coordinate of yellow enemies in this map */ 
    private ArrayList<Coordinate> yellowEnemiesCoordinate;
    /*  ArrayList stored the Coordinate of red enemies in this mape*/ 
    private ArrayList<Coordinate> redEnemiesCoordinate;
    /* The Coordinate of bomb guy in this map*/ 
    private Coordinate bombGuyCoordinate;
    /* The Coordinate of goal tile in this map*/ 
    private Coordinate goalCoordinate;

    /**
     * Map tiles sprites
     */
    private PImage emptySprite;
    private PImage solidSprite;
    private PImage goalSprite;
    private PImage brokenSprite;
    
    /**
     * Creates a new Map object.
     */
    public Map() {
        this.textMap = new char[13][15];
        this.stillObjects = new HashMap<Coordinate, StillObject>();
        this.yellowEnemiesCoordinate = new ArrayList<Coordinate>();
        this.redEnemiesCoordinate = new ArrayList<Coordinate>();
        this.bombGuyCoordinate = new Coordinate(0, 0);
        this.bombGuyNum = 0;
    }

    /**
     * Initializion the map.
     * Reset every objects as empty.
     */
    public void initialization() {
        this.textMap = new char[13][15];
        this.stillObjects = new HashMap<Coordinate, StillObject>();
        this.yellowEnemiesCoordinate = new ArrayList<Coordinate>();
        this.redEnemiesCoordinate = new ArrayList<Coordinate>();
        this.bombGuyCoordinate = new Coordinate(0, 0);
        this.bombGuyNum = 0;
    }

    /**
     * Set the text map of this level, according to the config file
     * 
     * @param textMap 2D array of the text map.
     */
    public void setTextMap(char[][] textMap) {
        this.textMap = textMap;
    }

    /**
     * Collect every objects' initial positon in current level according to this.textMap.
     */
    public void collectPosition() {
        for (int i = 0; i < 13; i++) {
            for (int j = 0; j < 15; j++) {
                char word = this.textMap[i][j];
                Coordinate coordinate = new Coordinate(j * 32, i * 32 + 64);

                if (word == 'W') {                   
                    StillObject obj = new StillObject(coordinate.getX(), coordinate.getY(), "solid", false);
                    this.stillObjects.put(coordinate, obj);
                } else if (word == 'B') {  
                    StillObject obj = new StillObject(coordinate.getX(), coordinate.getY(), "broken", false);
                    this.stillObjects.put(coordinate, obj);
                } else if (word == ' ') {
                    StillObject obj = new StillObject(coordinate.getX(), coordinate.getY(), "empty", true);
                    this.stillObjects.put(coordinate, obj);
                } else if (word == 'G') {
                    StillObject obj = new StillObject(coordinate.getX(), coordinate.getY(), "goal", true);
                    this.stillObjects.put(coordinate, obj);
                    this.goalCoordinate = coordinate;
                } else if (word == 'P') {
                    StillObject obj = new StillObject(coordinate.getX(), coordinate.getY(), "empty", true);
                    this.stillObjects.put(coordinate, obj);

                    if (this.bombGuyNum < 1) {
                        this.bombGuyCoordinate = coordinate;
                        this.bombGuyNum++;
                    }                   
                } else if (word == 'Y') {
                    StillObject obj = new StillObject(coordinate.getX(), coordinate.getY(), "empty", true);
                    this.stillObjects.put(coordinate, obj);
                    this.yellowEnemiesCoordinate.add(coordinate);
                    
                } else if (word == 'R') {
                    StillObject obj = new StillObject(coordinate.getX(), coordinate.getY(), "empty", true);
                    this.stillObjects.put(coordinate, obj);
                    this.redEnemiesCoordinate.add(coordinate);
                    // Enemy enemy = new Enemy(coordinate[0], coordinate[1], 'Y');
                    // enemies.add(enemy);
                } else {
                    continue;
                }
            }
        }
    }

    /**
    * Return this bomb guy current Coordinate.
    * 
    * @return this bomb guy current Coordinate
    */
    public Coordinate getBombGuyCoordinate() {
        return this.bombGuyCoordinate;
    }

    /**
    * Return yellow enemies current Coordinates.
    * 
    * @return a list of yellow enemies current Coordinate
    */
    public ArrayList<Coordinate> getYellowEnemiesCoordinate() {
        return this.yellowEnemiesCoordinate;
    }

    /**
    * Return red enemies current Coordinates.
    * 
    * @return a list of red enemies current Coordinate
    */
    public ArrayList<Coordinate> getRedEnemiesCoordinate() {
        return this.redEnemiesCoordinate;
    }

    /**
    * Return goal tile Coordinates.
    * 
    * @return goal tile Coordinate
    */
    public Coordinate getGoalCoordinate() {
        return this.goalCoordinate;
    }

    /**
     * Load still objects images in this map.
     * 
     * @param app The window to draw onto.
     */
    public void loadMapSprite(PApplet app) {
        this.solidSprite = app.loadImage("src/main/resources/wall/solid.png");
        this.brokenSprite = app.loadImage("src/main/resources/broken/broken.png");
        this.emptySprite = app.loadImage("src/main/resources/empty/empty.png");
        this.goalSprite = app.loadImage("src/main/resources/goal/goal.png");
    }

    /**
     * Set still objects sprite in this map.
     * 
     * @param app The window to draw onto.
     */
    public void setSprite(PApplet app) {

        for (StillObject obj : this.stillObjects.values()) {
            if (obj.getType().equals("solid")) {
                obj.setSprite(this.solidSprite);
            } else if (obj.getType().equals("broken")) {
                obj.setSprite(this.brokenSprite);
            } else if (obj.getType().equals("empty")) {
                obj.setSprite(this.emptySprite);
            } else if (obj.getType().equals("goal")) {
                obj.setSprite(this.goalSprite);
            }
        }
    }

    /**
     * Check the give position is movable.
     * 
     * @param x Object x-coordinate.
     * @param y Object y-coordinate.
     */
    public boolean isMovable(int x, int y) {
        Coordinate coordinate = new Coordinate(x, y);
        if (this.stillObjects.get(coordinate) != null) {
            
            StillObject obj = this.stillObjects.get(coordinate);
            if (obj.isBroken()) {
                return true;
            }
            return false;
        }
        return true;
    }

    /**
     * Check the give position have undestroiable obj.
     * 
     * @param x Object x-coordinate.
     * @param y Object y-coordinate.
     * @return 0: empty tile; 1: broken tile; 2: solid tile
     */
    public int checkObstacle(int x , int y) {
        Coordinate coordinate = new Coordinate(x, y);
        StillObject obj = stillObjects.get(coordinate);

        if (obj == null) {
            return 0;
        }

        if (obj.getType().equals("empty")) {
            return 0;
        }

        
        if (obj.getType().equals("broken")) {
            return 1;
        }
        return 2;
    }

    /**
     * Remove broken tile on the give position.
     * 
     * @param x Broken tile x-coordinate.
     * @param y Broken tile y-coordinate.
     */
    public void removeBroke(int x , int y) {
        Coordinate coordinate = new Coordinate(x, y);
        StillObject obj = stillObjects.get(coordinate);
        obj.brokenToEmpty();
        obj.setSprite(this.emptySprite);
    }

    /**
     * Updates the all still objects after explosion on the map every frame.
     * 
     * @param x Bomb x-coordinate.
     * @param y Bomb tile y-coordinate.
     * @return 0: empty tile; 1: broken tile; 2: solid tile
     */
    public int[] tick(int x, int y, int countDown) {
        int[] explosionRange = new int[]{2, 2, 2, 2};
        int brokenRemoved = 0;

        if (countDown > 30) {
            return explosionRange;
        }

        //topV
        if (checkObstacle(x, y - 32) > 0) {
            if (checkObstacle(x, y - 32) == 1) {
                this.removeBroke(x, y - 32);
                explosionRange[0] = 1;
            } else {
                explosionRange[0] = 0;
            }    
        } else {
            if (checkObstacle(x, y - 64) > 0) {
                if (checkObstacle(x, y - 64) == 2) {
                    explosionRange[0] = 1;
                }
            }
        }
        //botV
        if (checkObstacle(x, y + 32) > 0) {
            if (checkObstacle(x, y + 32) == 1) {
                this.removeBroke(x, y + 32);
                explosionRange[1] = 1;
            } else {
                explosionRange[1] = 0;
            }  
        } else {
            if (checkObstacle(x, y + 64) > 0) {
                if (checkObstacle(x, y + 64) == 2) {
                    explosionRange[1] = 1;
                }
            } 
        }
        
        //leftH
        if (checkObstacle(x - 32, y) > 0) {
            if (checkObstacle(x - 32, y) == 1) {
                this.removeBroke(x - 32, y);
                explosionRange[2] = 1;
            } else {
                explosionRange[2] = 0;
            }
            
        } else {
            if (checkObstacle(x - 64, y) > 0) {
                if (checkObstacle(x - 64, y) == 2) {
                    explosionRange[2] = 1;
                }
            }
        }
        
        //RightH
        if (checkObstacle(x + 32, y) > 0) {
            if (checkObstacle(x + 32, y) == 1) {
                this.removeBroke(x + 32, y);
                explosionRange[3] = 1;
            } else {
                explosionRange[3] = 0;
            }
        } else {
            if (checkObstacle(x + 64, y) > 0) {
                if (checkObstacle(x + 64, y) == 2) {
                    explosionRange[3] = 1;
                }
            }
        }

        return explosionRange;
    }

    /**
     * Draws the every still objects on this map to the screen.
     * 
     * @param app The window to draw onto.
     */
    public void draw(PApplet app) {

        for (StillObject obj : this.stillObjects.values()) {
            obj.draw(app);
        }
    }

}
