
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Uzzi
 */
public class Paddle extends GameObjects{
    
    public int dx= 0;
    private int paddleSpeed=6;
    private int Lives = 3;
    private ArrayList<Fire> bullets= new ArrayList<Fire>();
  
    public Paddle(String path, int x, int y)
    {
        super(path,x,y);
    }
    
    public void move()
    {
        this.x += dx;

        
        if (x < getWidth()/2) {
            x = getWidth()/2;
        }

        if(x>boardWidth-getWidth()/2){
            x=boardWidth-getWidth()/2;
        }
    }
    
    // Allows the paddle to fire bullets to destroy the bricks
    public void Fire(){
        bullets.add(new Fire(x,y - (h / 4)));
    } 
    
    //Returns the ArrayList of bullets
    public ArrayList getBullets(){
        return bullets;
    }
    public Rectangle getBounds() {
        
	return new Rectangle(x-(w/2), y-(h/2), w, h);
    }
    
    //Increases the size of the paddle
    public void enlargePaddle() {
        if (imagePath.equalsIgnoreCase("src/main/java/Paddles/MediumPaddle.png")) {
           setPath("src/main/java/Paddles/LongPaddle.png");
        }
        else if (imagePath.equalsIgnoreCase("src/main/java/Paddles/ShortPaddle.png")) {
           setPath( "src/main/java/Paddles/MediumPaddle.png");
        }
    }

    //Reduces the size of the paddle
    public void reducePaddle(){
        if (imagePath.equalsIgnoreCase("src/main/java/Paddles/LongPaddle.png")) {
            setPath("src/main/java/Paddles/MediumPaddle.png");
        }
        else if (imagePath.equalsIgnoreCase("src/main/java/Paddles/MediumPaddle.png")) {
            setPath("src/main/java/Paddles/ShortPaddle.png");
        }
    }
    
    //Increases speed of the paddle
    public void incPaddleSpeed(){
        paddleSpeed+=2;
    }

    //Decreases speed of the paddle
    public void decPaddleSpeed(){
        paddleSpeed-=2;
    }
    //Increases lives of the player
    public void incLives(){
        Lives++;
    }

    //Decreases Lives of the player
    public void decLives(){
        Lives--;
    }
    //Resets paddle speed to default
    public void  resetPaddleSpeed(){
        paddleSpeed=5;
    }

    //reset paddle to default size
    public void resetPaddle(){
        if (imagePath.equalsIgnoreCase("src/main/java/Paddles/LongPaddle.png")) {
          setPath("src/main/java/Paddles/MediumPaddle.png");
        }
        else if (imagePath.equalsIgnoreCase("src/main/java/Paddles/ShortPaddle.png")) {
            setPath("src/main/java/Paddles/MediumPaddle.png");
        }
    }
    //Returns the lives of the player
    public int getLives(){
        return Lives;
    }
    //Refills the lives of the player
    public void refillLives(){
        Lives= 3;
    }
    
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            dx = -paddleSpeed;
        }

        if (key == KeyEvent.VK_RIGHT) {
            dx = paddleSpeed;
        }
    }

    
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            dx = 0;
        }

        if (key == KeyEvent.VK_RIGHT) {
            dx = 0;
        }
    }
}
