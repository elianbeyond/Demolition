package demolition;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Random;
import java.util.Iterator;

import processing.data.JSONObject;
import processing.data.JSONArray;
import processing.core.PImage;
import processing.core.PApplet;
import processing.core.PFont;

/**
 * Handles the action of every object in this game.
 */
public class Manager {
    /* Map Object*/ 
    private Map map;
    /* BombGuy object*/ 
    private BombGuy bombGuy;
    /* Enemies list*/ 
    private ArrayList<Enemy> enemies;
    /* Bomb list*/ 
    private ArrayList<Bomb> bombs;

    /* This game's current level index*/ 
    private int levelIndex;
    /* This game's total levels index number*/ 
    private int totalLevelsIndex;
    /* BombGuy's total lives*/ 
    private int lives;
    /* This game's seconds Timer*/ 
    private int gameTimer;
    /* This game's count down show on the screen*/ 
    private int gameCountDown;
    /* This game's text map*/ 
    private char[][] textMap;
    /* This game's BombGuy's Timer*/ 
    private int bombGuyTimer;
    /* This game's enemies' Timer*/ 
    private int enemyTimer;
    /* Random number*/ 
    private Random rand;
    /* This game's text font show on the screen*/ 
    private PFont font;

    /**
     * Player sprites
     */
    private PImage[] playerLeftSprites;
    private PImage[] playerRightSprites;
    private PImage[] playerUpSprites;
    private PImage[] playerDownSprites;

    /**
     * Bomb and explosion sprites
     */
    private PImage[] bombSprites;
    private PImage explosionCentre;
    private PImage explosionHorizaontal;
    private PImage explosionVertical;

    /**
     * Red enemies sprites
     */
    private PImage[] redLeftSprites;
    private PImage[] redRightSprites;
    private PImage[] redUpSprites;
    private PImage[] redDownSprites;

    /**
     * Yellow enemies sprites
     */
    private PImage[] yellowLeftSprites;
    private PImage[] yellowRightSprites;
    private PImage[] yellowUpSprites;
    private PImage[] yellowDownSprites;

    /**
     * Map objects sprites
     */
    private PImage brokenSprite;
    private PImage emptySprite;
    private PImage goalSprite;
    private PImage solidSprite;

    /**
     * Game icon sprites
     */
    private PImage playerIcon;
    private PImage clockIcon;

    /**
     * Creates a new Manager object.
     */
    public Manager() {
        this.map = new Map();
        this.bombGuy = new BombGuy(0, 0, 3);
        this.enemies = new ArrayList<Enemy>();
        this.bombs = new ArrayList<Bomb>();

        this.levelIndex = 0;
        this.lives = 3;
        this.textMap = new char[13][15];
        this.bombGuyTimer = 0;
        this.enemyTimer = 0;
        this.gameTimer = 0;
        this.rand = new Random();

        this.bombSprites = new PImage[8];

        this.playerLeftSprites = new PImage[4];
        this.playerRightSprites = new PImage[4];
        this.playerUpSprites = new PImage[4];
        this.playerDownSprites = new PImage[4];

        this.redLeftSprites = new PImage[4];
        this.redRightSprites = new PImage[4];
        this.redUpSprites = new PImage[4];
        this.redDownSprites = new PImage[4];

        this.yellowLeftSprites = new PImage[4];
        this.yellowRightSprites = new PImage[4];
        this.yellowUpSprites = new PImage[4];
        this.yellowDownSprites = new PImage[4];
    }

    /**
     * Get game's enemies list.
     * 
     * @return this game's enemies list.
     */
    public ArrayList<Enemy> getEnemies() {
        return this.enemies;
    }

    /**
     * Get game's Bomb guy.
     * 
     * @return Get game's Bomb guy.
     */
    public BombGuy getBombGuy() {
        return this.bombGuy;
    }

    /**
     * Read game config Json file and save the valid data.
     * 
     * @return valid data for this game in config file.
     */
    public String readJsonFile() {
        String data = "";

        try {
            File f = new File("config.json");
            Scanner scan = new Scanner(f);

            data = scan.useDelimiter("\\A").next();

        } catch(FileNotFoundException e) {
            e.printStackTrace();
        }

        return data;
    }

    /**
     * Configure the game by the data wrote in config file.
     * Set game levels, game time, bomb guy lives, text map file path.
     */
    public void getConfig() {
        String jsonText = readJsonFile();

        JSONObject jsObj = JSONObject.parse(jsonText);

        JSONArray levels = (JSONArray) jsObj.get("levels");

        JSONObject level = levels.getJSONObject(this.levelIndex);

        String path = (String) level.get("path");

        this.lives = (int) jsObj.get("lives");

        this.gameCountDown = (int) level.get("time");

        this.totalLevelsIndex = levels.size();
        
        try {
            File f = new File(path);
            Scanner scan = new Scanner(f);
            int row = 0;
            while(scan.hasNextLine()){
                String data = scan.nextLine();
                
                for (int i = 0; i < data.length(); i++) {
                    this.textMap[row][i] = data.charAt(i);
                }
                row++;
            }
            
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Initializion the game.
     * Put every objects back at the origin position as decribed in text map.
     * Reset the time and enemies list.
     */
    public void initialization(PApplet app) {
        this.map.initialization();
        this.map.setTextMap(this.textMap);
        this.map.collectPosition();
        this.bombGuy.setCoordinate(this.map.getBombGuyCoordinate().getX(), this.map.getBombGuyCoordinate().getY() - 16);
        this.bombGuy.setDirection('D');
        this.bombGuy.setLives(this.bombGuy.getLives());
        this.bombGuyTimer = 0;
        this.enemyTimer = 0;
        this.enemies = new ArrayList<Enemy>();
        this.bombs = new ArrayList<Bomb>();

        for (Coordinate coordinate : this.map.getRedEnemiesCoordinate()) {
            Enemy enemy = new Enemy(coordinate.getX(), coordinate.getY() - 16, 'R');
            this.enemies.add(enemy);
            
        }

        for (Coordinate coordinate : this.map.getYellowEnemiesCoordinate()) {
            Enemy enemy = new Enemy(coordinate.getX(), coordinate.getY()  - 16, 'Y');
            this.enemies.add(enemy);
            
        }

        this.setSprite(app);
    }

    /**
     * Load Moving Object's images.
     * 
     * @param app The window to draw onto.
     */
    public void loadMovingObjSprites(PApplet app) {
        for (int i = 1; i < 5; i++) {
            this.redLeftSprites[i - 1] = app.loadImage(String.format("src/main/resources/red_enemy/red_left%d.png", i));
            this.yellowLeftSprites[i - 1] = app.loadImage(String.format("src/main/resources/yellow_enemy/yellow_left%d.png", i));
            this.playerLeftSprites[i - 1] = app.loadImage(String.format("src/main/resources/player/player_left%d.png", i));

            this.redRightSprites[i - 1] = app.loadImage(String.format("src/main/resources/red_enemy/red_right%d.png", i));
            this.yellowRightSprites[i - 1] = app.loadImage(String.format("src/main/resources/yellow_enemy/yellow_right%d.png", i));
            this.playerRightSprites[i - 1] = app.loadImage(String.format("src/main/resources/player/player_right%d.png", i));

            this.redUpSprites[i - 1] = app.loadImage(String.format("src/main/resources/red_enemy/red_up%d.png", i));
            this.yellowUpSprites[i - 1] = app.loadImage(String.format("src/main/resources/yellow_enemy/yellow_up%d.png", i));
            this.playerUpSprites[i - 1] = app.loadImage(String.format("src/main/resources/player/player_up%d.png", i));

            this.redDownSprites[i - 1] = app.loadImage(String.format("src/main/resources/red_enemy/red_down%d.png", i));
            this.yellowDownSprites[i - 1] = app.loadImage(String.format("src/main/resources/yellow_enemy/yellow_down%d.png", i));
            this.playerDownSprites[i - 1] = app.loadImage(String.format("src/main/resources/player/player%d.png", i));
        }
    }

    /**
     * Load Bomb Object's images.
     * 
     * @param app The window to draw onto.
     */
    public void loadBombSprites(PApplet app) {
        for (int i = 1; i < 9; i++) {
            this.bombSprites[i - 1] = app.loadImage(String.format("src/main/resources/bomb/bomb%d.png", i));
        }

        this.explosionCentre = app.loadImage("src/main/resources/explosion/centre.png");
        this.explosionHorizaontal = app.loadImage("src/main/resources/explosion/horizontal.png");
        this.explosionVertical = app.loadImage("src/main/resources/explosion/vertical.png");
    }

    /**
     * Load Map Object's images.
     * 
     * @param app The window to draw onto.
     */
    public void loadMapSprites(PApplet app) {
        this.solidSprite = app.loadImage("src/main/resources/wall/solid.png");
        this.brokenSprite = app.loadImage("src/main/resources/broken/broken.png");
        this.emptySprite = app.loadImage("src/main/resources/empty/empty.png");
        this.goalSprite = app.loadImage("src/main/resources/goal/goal.png");
    }

    /**
     * Load Game Icon images.
     * 
     * @param app The window to draw onto.
     */
    public void loadIconSprites(PApplet app) {
        this.clockIcon = app.loadImage("src/main/resources/icons/clock.png");
        this.playerIcon = app.loadImage("src/main/resources/icons/player.png");
    }

     /**
     * Load every objects images in this game.
     * 
     * @param app The window to draw onto.
     */
    public void loadImages(PApplet app) {
        this.map.loadMapSprite(app);
        this.loadMovingObjSprites(app);
        this.loadBombSprites(app);
        this.loadMapSprites(app);
        this.loadIconSprites(app);
        this.font = app.createFont("src/main/resources/PressStart2P-Regular.ttf", 18);
        app.textFont(this.font);
        app.fill(0, 0, 0);
    }

    /**
     * Set every objects sprites in this game.
     * 
     * @param app The window to draw onto.
     */
    public void setSprite(PApplet app) {
        
        this.map.setSprite(app);

        this.bombGuy.setLeftSprites(this.playerLeftSprites[0], this.playerLeftSprites[1], 
        this.playerLeftSprites[2], this.playerLeftSprites[3]);

        this.bombGuy.setRightSprites(this.playerRightSprites[0], this.playerRightSprites[1], 
        this.playerRightSprites[2], this.playerRightSprites[3]);

        this.bombGuy.setUpSprites(this.playerUpSprites[0], this.playerUpSprites[1], 
        this.playerUpSprites[2], this.playerUpSprites[3]);

        this.bombGuy.setDownSprites(this.playerDownSprites[0], this.playerDownSprites[1], 
        this.playerDownSprites[2], this.playerDownSprites[3]);

        for (Enemy enemy : this.enemies) {
            if (enemy.getColor() == 'R') {
                enemy.setLeftSprites(this.redLeftSprites[0], this.redLeftSprites[1], 
                this.redLeftSprites[2],this.redLeftSprites[3]);

                enemy.setRightSprites(this.redRightSprites[0], this.redRightSprites[1], 
                this.redRightSprites[2],this.redRightSprites[3]);

                enemy.setUpSprites(this.redUpSprites[0], this.redUpSprites[1], 
                this.redUpSprites[2],this.redUpSprites[3]);
                
                enemy.setDownSprites(this.redDownSprites[0], this.redDownSprites[1], 
                this.redDownSprites[2],this.redDownSprites[3]);

            } else if (enemy.getColor() == 'Y') {
                enemy.setLeftSprites(this.yellowLeftSprites[0], this.yellowLeftSprites[1], 
                this.yellowLeftSprites[2],this.yellowLeftSprites[3]);

                enemy.setRightSprites(this.yellowRightSprites[0], this.yellowRightSprites[1], 
                this.yellowRightSprites[2],this.yellowRightSprites[3]);

                enemy.setUpSprites(this.yellowUpSprites[0], this.yellowUpSprites[1], 
                this.yellowUpSprites[2],this.yellowUpSprites[3]);
                
                enemy.setDownSprites(this.yellowDownSprites[0], this.yellowDownSprites[1], 
                this.yellowDownSprites[2],this.yellowDownSprites[3]);
            }
        }
    }

    /**
     * Called in App when the left key is pressed.
     * Bomb Guy try to move to left.
     */
    public void pressLeft() {
        if (this.map.isMovable(this.bombGuy.getX() - 32, this.bombGuy.getY() + 16)) {
            this.bombGuy.moveLeft();
        }
        this.bombGuyTimer = 0;    
    }

    /**
     * Called in App when the right key is pressed.
     * Bomb Guy try to move to right.
     */
    public void pressRight() {
        if (this.map.isMovable(this.bombGuy.getX() + 32, this.bombGuy.getY() + 16)) {
            this.bombGuy.moveRight();
        } 
        this.bombGuyTimer = 0;
    }

    /**
     * Called in App when the up key is pressed.
     * Bomb Guy try to move to up.
     */
    public void pressUp() {
        if (this.map.isMovable(this.bombGuy.getX(), this.bombGuy.getY() - 16)) {
            this.bombGuy.moveUp();
        } 
        this.bombGuyTimer = 0;
    }

    /**
     * Called in App when the down key is pressed.
     * Bomb Guy try to move to down.
     */
    public void pressDown() {
        if (this.map.isMovable(this.bombGuy.getX(), this.bombGuy.getY() + 48)) {
            this.bombGuy.moveDown();
        } 
        this.bombGuyTimer = 0;
    }

    /**
     * Yellow enemies move clockwise.
     * @param enemy The enemy going to move
     */
    public void yellowEnemyMove(Enemy enemy) {
        if (enemy.getDirection() == 'L') {
            if (this.map.isMovable(enemy.getX() - 32, enemy.getY() + 16)) {
                enemy.moveLeft();
            } else {
                enemy.setDirection('U');
                this.yellowEnemyMove(enemy);
            }
        } else if (enemy.getDirection() == 'R') {
            if (this.map.isMovable(enemy.getX() + 32, enemy.getY() + 16)) {
                enemy.moveRight();
            } else {
                enemy.setDirection('D');
                this.yellowEnemyMove(enemy);
            }
        } else if (enemy.getDirection() == 'U') {
            if (this.map.isMovable(enemy.getX(), enemy.getY() - 16)) {
                enemy.moveUp();
            } else {
                enemy.setDirection('R');
                this.yellowEnemyMove(enemy);
            }
        } else if (enemy.getDirection() == 'D') {
            if (this.map.isMovable(enemy.getX(), enemy.getY() + 48)) {
                enemy.moveDown();
            } else {
                enemy.setDirection('L');
                this.yellowEnemyMove(enemy);
            }
        }   
    }

    /**
     * Red enemies move randomly.
     * 0:left
     * 1:top
     * 2:bot
     * 3:right
     * @param enemy The enemy going to move.
     * @param randNum Radom deriction number.
     */
    public void enemyRandMove(Enemy enemy, int randNum) {
        if (randNum == 0) {
            enemy.moveLeft();
        } else if (randNum == 1) {
            enemy.moveDown();
        } else if (randNum == 2) {
            enemy.moveUp();
        } else if (randNum == 3) {
            enemy.moveRight();
        }
    }

    /**
     * Check if the give direction movable.
     * @param direction The enemy going to move
     * @param x Moving enemy x-coordinate.
     * @param y Moving enemy y-coordinate.
     */
    public boolean directionIsMovable(int direction, int x, int y) {
        if (direction == 0) {
            return this.map.isMovable(x - 32, y + 16);
        } else if (direction == 3) {
            return this.map.isMovable(x + 32, y + 16);
        } else if (direction == 2) {
            return this.map.isMovable(x, y - 16);
        } else if (direction == 1) {
            return this.map.isMovable(x, y + 48);
        }
        return false;
    }

    /**
     * Red enemies choose a random direction to move.
     * @param direction The enemy going to move
     * @param randomDirection Radom deriction number.
     */
    public void chooseRandDirectionMove(Enemy enemy, int randomDirection) {
        while (true) {
            if (directionIsMovable(randomDirection, enemy.getX(), enemy.getY())) {
                this.enemyRandMove(enemy, randomDirection);
                break;
            }
            randomDirection++;

            if (randomDirection > 3) {
                randomDirection = 0;
            }
        }   
    }

    /**
     * Yellow enemies move randomly.
     * @param enemy The enemy going to move
     */
    public void redEnemyMove(Enemy enemy) {
        int randomDirection = this.rand.nextInt(4);

        if (enemy.getDirection() == 'L') {
            if (this.map.isMovable(enemy.getX() - 32, enemy.getY() + 16)) {
                enemy.moveLeft();
            } else {
                this.chooseRandDirectionMove(enemy, randomDirection);
            }
        } else if (enemy.getDirection() == 'R') {
            if (this.map.isMovable(enemy.getX() + 32, enemy.getY() + 16)) {
                enemy.moveRight();
            } else {
                this.chooseRandDirectionMove(enemy, randomDirection);
            }
        } else if (enemy.getDirection() == 'U') {
            if (this.map.isMovable(enemy.getX(), enemy.getY() - 16)) {
                enemy.moveUp();
            } else {
                this.chooseRandDirectionMove(enemy, randomDirection);
            }
        } else if (enemy.getDirection() == 'D') {
            if (this.map.isMovable(enemy.getX(), enemy.getY() + 48)) {
                enemy.moveDown();
            } else {
                this.chooseRandDirectionMove(enemy, randomDirection);
            }
        }   
    }

    /**
     * Called in App when the space key is pressed.
     * Bomb Guy put a bomb at current position.
     */
    public void pressSpace() {
        Bomb bomb = new Bomb(this.bombGuy.getX(), this.bombGuy.getY() + 16);
        bomb.setBombSprites(this.bombSprites);
        bomb.setExplosionSprites(this.explosionCentre, this.explosionHorizaontal, this.explosionVertical);
        bombs.add(bomb);
    }

    /**
     * Check if Bomb Guy passed current level.
     * 
     * @return Bomb Guy passed current level.
     */
    public boolean passLevel() {
        Coordinate goalCoordinate = this.map.getGoalCoordinate();
        if (this.bombGuy.getX() == goalCoordinate.getX() && 
        this.bombGuy.getY() + 16 == goalCoordinate.getY()) {
            return true;
        }
        return false;
    }

    /**
     * Updates the all objects on the map every frame.
     */
    public void tick() {
        this.bombGuyTimer++;
        this.bombGuy.tick();
        this.gameTimer++;

        if (this.gameTimer % 60 == 0) {
            this.gameCountDown--;
        }

        if (this.enemies.size() > 0) {
            this.enemyTimer++;
            if (this.enemyTimer == 60) {
                for (Enemy enemy : this.enemies){
                    if (enemy.getColor() == 'Y') {
                        yellowEnemyMove(enemy);
                    } else if (enemy.getColor() == 'R') {
                        redEnemyMove(enemy);
                    }
                    enemy.tick();
                }
                this.enemyTimer = 0;
            }
        }
        
        if (this.bombs.size() > 0) {
            Iterator<Bomb> itr = this.bombs.iterator();
            while (itr.hasNext()) {
                Bomb bomb = itr.next();
                bomb.tick();
                if (bomb.cleanBomb()) {
                    itr.remove();
                }
            } 
        }     
    }

    /**
     * Check if game is over.
     * If Bomb guy lost all lives.
     * If time count down to 0.
     * 
     * @return If game is over.
     */
    public boolean gameOver() {
        if (this.bombGuy.getLives() <= 0 || this.gameCountDown <= 0) {
           return true;
        }
        return false;
    }

    /**
     * Check if game is win.
     * If Bomb guy passed all levels.
     * 
     * @return If game is win.
     */
    public boolean gameWin(PApplet app) {
        if (this.passLevel()) {
            this.levelIndex++;
            if (this.levelIndex > this.totalLevelsIndex - 1) {
                return true;
            }
            this.getConfig();
            this.initialization(app);
        }
        return false;
    }

    /**
     * Draws the every objects in this game to the screen.
     * 
     * @param app The window to draw onto.
     */
    public void draw(PApplet app) {
        app.image(this.playerIcon, 128, 16);
        app.image(this.clockIcon, 256, 16);
        app.text(this.bombGuy.getLives(), 170, 42);
        app.text(this.gameCountDown, 298, 42);

        this.gameWin(app);
        
        this.map.draw(app);
        if (this.bombs.size() > 0) {
            for (Bomb bomb : this.bombs) {
                if (bomb.getCountDown() == 30) {
                    int[] explosionRange = this.map.tick(bomb.getX(), bomb.getY(), bomb.getCountDown());
                    bomb.setExplosionRange(explosionRange);
                }
                
                bomb.checkEnemiesInExplosion(this);
                bomb.draw(app);
                if (bomb.checkPlayerInExplosion(this)) {
                    this.initialization(app); 
                    if (this.getBombGuy().getLives() == 0) {
                        this.initialization(app); 
                    }
                    return;
                }
            }
        }

        this.bombGuy.draw(app, (this.bombGuyTimer / 12) % 4);

        if (this.enemies.size() > 0) {
            for (Enemy enemy : this.enemies) {
                enemy.draw(app, (this.enemyTimer / 12) % 4);
                if (enemy.checkPlayerInTouch(this)) {
                    this.initialization(app); 
                    if (this.getBombGuy().getLives() == 0) {
                        this.initialization(app); 
                    }
                    return;
                }
            }
        }
    }

}