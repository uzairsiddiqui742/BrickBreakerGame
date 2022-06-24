
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import javax.swing.ImageIcon;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Uzzi
 */
public abstract class Brick extends GameObjects {
    
    public Brick(String path)
    {
        super(path);
    }
    
    public void draw(Graphics2D g,int x,int y)
    {
        g.drawImage(this.image, x, y, null);
        g.setColor(new Color(255,0,0));
    }
    
    //Sets path of a brick image
    public abstract void setBrickImage();
}
