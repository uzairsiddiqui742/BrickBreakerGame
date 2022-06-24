
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
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
public abstract class GameObjects {
    protected boolean visible= true;
    protected int x,y,w,h;
    protected int boardWidth = 842;
    protected String imagePath;

   
    protected Image image = new Image() {
        @Override
        public int getWidth(ImageObserver observer) {
           return 0;
        }

        @Override
        public int getHeight(ImageObserver observer) {
           return 0;
        }

        @Override
        public ImageProducer getSource() {
           return null;
        }

        @Override
        public Graphics getGraphics() {
          return null;
        }

        @Override
        public Object getProperty(String name, ImageObserver observer) {
            return null;
        }
    };
    public GameObjects(String path,int x,int y)
    {
        this.x = x;
        this.y = y;
        this.imagePath = path;
        ImageIcon imageIcon = new ImageIcon(this.imagePath);
        if (image != null) image = imageIcon.getImage();
        if (image != null) w = image.getWidth(null);
        if (image != null) h = image.getHeight(null);
    }
    public GameObjects(int x,int y)
    {
        this.x = x;
        this.y = y; 
        ImageIcon imageIcon = new ImageIcon(this.imagePath);
        if (image != null) image = imageIcon.getImage();
        if (image != null) w = image.getWidth(null);
        if (image != null) h = image.getHeight(null);
    }
    public GameObjects(String path)
    {
        
        this.imagePath = path;
        ImageIcon imageIcon = new ImageIcon(this.imagePath);
        if (image != null) image = imageIcon.getImage();
        if (image != null) w = image.getWidth(null);
        if (image != null) h = image.getHeight(null);
    }
    public GameObjects()
    {
        ImageIcon imageIcon = new ImageIcon(this.imagePath);
        if (image != null) image = imageIcon.getImage();
        if (image != null) w = image.getWidth(null);
        if (image != null) h = image.getHeight(null);
    }
   
    public abstract void move();
    
     public int getX() {
        return this.x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return this.y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return w;
    }

    public void setWidth(int w) {
        this.w = w;
    }

    public int getHeight() {
        return h;
    }

    public void setHeight(int h) {
        this.h = h;
    }

    public String getPath() {
        return imagePath;
    }

    public void setPath(String path){
        this.imagePath=path;
        ImageIcon imageIcon = new ImageIcon(this.imagePath);
        if (image != null) image = imageIcon.getImage();
        if (image != null) w = image.getWidth(null)+2;
        if (image != null) h = image.getHeight(null)+2;
        
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

}
