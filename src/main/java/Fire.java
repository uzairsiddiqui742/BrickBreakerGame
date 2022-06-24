
import java.awt.Rectangle;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Uzzi
 */
public class Fire extends GameObjects{
    
    private int fireSpeed = -5;
    public Fire(int x,int y)
    {
        super("src/main/java/Fire/Fire.png",x,y);
    }
    public void move()
    {
        this.y += fireSpeed;
        //If the bullet goes beyond the screen, it becomes invisible
        if (y<0) {
            visible = false;
        }
    }
    
    //Determines whether a bullet is visible or not
    public boolean isVisible(){
        return visible;
    }
    // Returns the Bounds of the Bullet
    public Rectangle getBounds() {
        return new Rectangle(x, y, w+2, h+2);
    }
}
