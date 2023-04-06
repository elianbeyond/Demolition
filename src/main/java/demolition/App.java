package demolition;

// Import PApplet from the processing package
import processing.core.PApplet;

/**
 * Handles the Window of the program.
 */
public class App extends PApplet {

    /**
     * The width of the window.
     */
    public static final int WIDTH = 480;

    /**
     * The height of the window.
     */
    public static final int HEIGHT = 480;

    /**
     * The number of frames per second.
     */
    public static final int FPS = 60;

    /* A management class contral the game*/ 
    private Manager manager;
    
    /**
     * Creates a new App object with a Manager class.
     */
    public App() {
        this.manager = new Manager();
    }

    /**
     * Sets up parameters for the project, namely its width and height.
     */
    public void settings() {
        size(WIDTH, HEIGHT);
    }

    /**
     * Sets up the program, setting the frame rate.
     * 
     * Loads in all images.
     */
    public void setup() {
        frameRate(FPS);
        
        // Load images during setup
        this.manager.loadImages(this);
        this.manager.getConfig();
        this.manager.initialization(this);
    }

    /**
     * Draws and updates the program.
     */
    public void draw() {
        background(239, 129, 0);
        
        if (this.manager.gameOver()) {
            this.text("GAME OVER", 160, 240);  
        } else if (this.manager.gameWin(this)) {
            this.text("YOU WIN", 176, 240); 
        } else {
            this.manager.tick();
            this.manager.draw(this);
        }    
    }

    /**
     * Called every frame if a key is down.
     * 
     * You can access the key with the keyCode variable.
     */
    public void keyPressed() {
        if (this.keyCode == 37) {
            this.manager.pressLeft();
        } else if (this.keyCode == 39) {
            this.manager.pressRight();
        } else if (this.keyCode == 38) {
            this.manager.pressUp();
        } else if (this.keyCode == 40) {
            this.manager.pressDown();
        } else if (this.keyCode == 32) {
            this.manager.pressSpace();
        }
    }

    /**
     * Runs the program.
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        PApplet.main("demolition.App");
    }
}
