
import java.awt.Rectangle;
import java.util.Random;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Uzzi
 */
public class Ball extends GameObjects{
    
    public int ballX =  -3;
    public int ballY = -4;
    public boolean ballStuck = true;
    public Ball()
    {
        super("src/main/java/Ball/fireball.png",460,590);
    }
    public Ball(int x, int y)
    {
        super("src/main/java/Ball/fireball.png",x,y);
        ballStuck = false;
        
        Random rand= new Random();
        ballX=(2+(rand.nextInt(3)))*-1;
        ballY =(2+(rand.nextInt(3)))*-1;
    }
    public void move()
    {
        if(!ballStuck)
        {
            this.x += ballX;
            this.y += ballY;
           
            if (x < w / 2) {
                reboundX();
            }

            if (x > boardWidth - w / 2) {
                reboundX();
            }
            if (y < getHeight() / 2) {
                reboundY();
            }
        }
    }
    public void moveWithPaddleSide(Paddle player){

        this.x+=player.dx;
    }
    public Rectangle getBounds() {
        
	return new Rectangle(x, y, 20, 20);
    }
    public void moveWithPaddle(Paddle paddle){
        
        this.x= paddle.getX();
        this.y=paddle.getY()-80;
    }
    public void reboundX()
    {
        ballX = -ballX;
    }
    public void reboundY()
    {
        ballY=-ballY;
    }
    //Stops the ball on screen
    public void stopBall(){
       ballStuck=true;
    }


}
