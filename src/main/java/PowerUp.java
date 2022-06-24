
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
public class PowerUp extends GameObjects{
    
    public PowerUp()
    {
        super();
    }
    public PowerUp(int x , int y)
    {
        super(x,y);
    }
    public void move()
    {
        this.y += 4;
        
    }
    public Rectangle getBounds()
    {
        return new Rectangle(x-(w/2),y-(h/2),w,h);
    }
}
