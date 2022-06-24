
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Uzzi
 */
public class Board extends JPanel implements ActionListener {

    private Timer timer;
    private int delay = 10;
    private Paddle paddle;
    private ArrayList<Ball> balls = new ArrayList<>();
    private BrickMap map;
    PowerUpGenerator powerFactory = new PowerUpGenerator();
    PowerUp p = new PowerUp();
    int totalBricks = 24;
    private Image img, menuImg, explodeImg, gameOverImg, winImg;
    int contact = 25, score = 0, contactBrick;
    boolean split = false;
    int counter = 10;
    boolean present;
    boolean firePowerUp = false;
    private boolean play = true;
    private boolean mainMenu = true;
    private FileActions fA = new FileActions();
    boolean firstPowerUp = true;
    boolean explosion = false;
    int explodeX, explodeY, explodeW, explodeH, count = 50;

    public Board() {
        img = new ImageIcon("src/main/java/Wall/bg1.jpg").getImage();
        menuImg = new ImageIcon("src/main/java/Wall/MENU.jpg").getImage();
        explodeImg = new ImageIcon("src/main/java/Wall/Explosion.png").getImage();
        gameOverImg = new ImageIcon("src/main/java/Wall/gameOver.jpg").getImage();
        winImg = new ImageIcon("src/main/java/Wall/winImg.jpg").getImage();
        addKeyListener(new TAdapter());
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        paddle = new Paddle("src/main/java/Paddles/MediumPaddle.png", 460, 630);
        map = new BrickMap(3, 8);
        timer = new Timer(delay, this);
        timer.start();
    }

    @Override
    public void paint(Graphics g) {

        if (this.mainMenu) {
            this.paintMainMenu(g);
        } else {
            //background
            g.drawImage(img, 0, 0, null);
            g.setColor(Color.black);

            //score and lives
            g.setColor(Color.red);
            g.setFont(new Font("serif", Font.BOLD, 25));
            g.drawString("Score: " + score, 595, 30);
            g.drawString("Lives Left: " + paddle.getLives(), 400, 30);

            //paddle
            g.setColor(Color.magenta);
            g.drawImage(paddle.getImage(), paddle.getX() - paddle.getImage().getWidth(null) / 2, paddle.getY() - paddle.getImage().getHeight(null) / 2, null);            

            //ball
            if (balls.size() == 0) {
                balls.add(new Ball());
            }
            for (int i = 0; i < balls.size(); i++) {
                Ball ball = balls.get(i);

                g.drawImage(ball.getImage(), ball.getX() - ball.getImage().getWidth(null) / 2, ball.getY() - ball.getImage().getHeight(null) / 2, null);              
            }

            // Brick MAP
            map.paint((Graphics2D) g);

            // explosion
            if (explosion) {
                g.drawImage(explodeImg, explodeX - (explodeW / 2), explodeY - (explodeH / 2), null);
                if (count < 0) {
                    explosion = false;
                    count = 50;
                }
            }

            // power ups
            g.drawImage(p.getImage(), p.getX() - p.getImage().getWidth(null) / 2, p.getY() - p.getImage().getHeight(null) / 2, null);

            //Fire
            for (int i = 0; i < paddle.getBullets().size(); i++) {
                Fire fire = (Fire) paddle.getBullets().get(i);
                if (fire.isVisible()) {
                    g.drawImage(fire.getImage(), fire.getX() - fire.getImage().getWidth(null) / 2, fire.getY() - fire.getImage().getHeight(null) / 2, null);
                }
            }

            //Check for Win, if game won, display related details
            if (allGreenBricks()) {
                play = false;
                stopBalls();
                g.drawImage(winImg, 0, 0, null);
            }
        }

        //Checks if any ball on screen
        if (!isBallPresent()) {

            //Checks if life of player is zero
            if (paddle.getLives() == 0) {

                //If no ball on screen and no lives left, end game and display loosing details
                play = false;
                stopBalls();
                g.drawImage(gameOverImg, 0, 0, null);

            } else {
                //If no ball on screen and player has lives to loose, reduce one life of the player.
                paddle.decLives();
                balls.add(new Ball());

            }
        }

        g.dispose();

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        repaint();
        contact--;
        count--;
        contactBrick--;
        for (int i = 0; i < balls.size(); i++) {
            Ball ball = balls.get(i);

            if (isBallPaddleCollision(ball)) {
                Rectangle intersection = ball.getBounds().intersection(paddle.getBounds());
                if (intersection.width >= intersection.height) {
                    if (contact < 1) {
                        ball.reboundY();
                        contact = 25;

                    }
                }
                if (intersection.height >= intersection.width) {
                    ball.reboundX();

                    ball.moveWithPaddleSide(paddle);
                }

                ball.move();//Ball moves
            }

        }

        //Checks if game is going on
        if (play) {
            paddle.move();// If game is going on, moves the paddle
            for (int m = 0; m < balls.size(); m++) {
                Ball ball = balls.get(m);
                //If ball is stuck on the paddle, it moves with the paddle
                if (ball.ballStuck) {
                    ball.moveWithPaddle(paddle);
                } else {
                    ball.move();
                }
            }
        }

        // move power up
        p.move();

        // Split ball
        if (isSplit()) {
            splitBalls();
        }

        //move Bullets
        moveBullets();

        // brick ball collision
        brickBallCollision();

        //power up paddle Collision
        powerUpPaddleCollision();

    }

    // Ball Paddle Collision 
    public boolean isBallPaddleCollision(Ball ball) {
        return (ball.getBounds().intersects(paddle.getBounds()));
    }

    public boolean isSplit() {
        return split;
    }

    //Stops all balls on Screen
    public void stopBalls() {
        for (int i = 0; i < balls.size(); i++) {
            Ball ball = balls.get(i);
            ball.stopBall();
        }
    }

    public boolean allGreenBricks() {
        boolean is = false;
        for (int i = 0; i < map.map.length; i++) {
            for (int j = 0; j < map.map[0].length; j++) {
                if (map.map[i][j] == 1 || map.map[i][j] == 2 && map.map[i][j] == 3) {
                    is = false;
                    break;
                } else if (map.map[i][j] == 4) {
                    is = true;
                }
            }
            if (is == false) {
                break;
            }

        }
        return is;
    }

    // check if ball is present on the screen
    public boolean isBallPresent() {

        for (int i = 0; i < balls.size(); i++) {
            Ball ball = balls.get(i);
            if (ball.getY() <= 680) {
                present = true;
                break;
            } else {
                present = false;
            }
        }
        return present;
    }
    // power up paddle Collision

    public void powerUpPaddleCollision() {
        if ((new Rectangle(p.getX(), p.getY(), p.getWidth(), p.getHeight()).intersects(paddle.getX() - (paddle.getWidth() / 2), paddle.getY() - (paddle.getHeight() / 2), paddle.getWidth(), paddle.getHeight()))) {
            if (p.getPath().equalsIgnoreCase("src/main/java/PowerUps/DecreasePowerUp.png")) {
                p.setPath("");
                paddle.reducePaddle();
            } else if (p.getPath().equalsIgnoreCase("src/main/java/PowerUps/IncreasePowerUp.png")) {
                p.setPath("");
                paddle.enlargePaddle();
            } else if (p.getPath().equalsIgnoreCase("src/main/java/PowerUps/FastPowerUp.png")) {
                p.setPath("");
                paddle.incPaddleSpeed();
            } else if (p.getPath().equalsIgnoreCase("src/main/java/PowerUps/lifegainPowerUp.png")) {
                p.setPath("");
                paddle.incLives();
            } else if (p.getPath().equalsIgnoreCase("src/main/java/PowerUps/SlowPowerUp.png")) {
                p.setPath("");
                paddle.decPaddleSpeed();
            } else if (p.getPath().equalsIgnoreCase("src/main/java/PowerUps/SplitballPowerUp.png")) {
                p.setPath("");
                split = true;
            } else if (p.getPath().equalsIgnoreCase("src/main/java/PowerUps/FirePowerUp.png")) {
                p.setPath("");
                firePowerUp = true;
            }
        }
    }

    //Forms two new balls from every ball
    public void splitBalls() {
        int loop = balls.size();

        for (int i = 0; i < loop; i++) {

            Ball ball = balls.get(i);
            balls.add(new Ball(ball.getX(), ball.getY()));
            balls.add(new Ball(ball.getX(), ball.getY()));
        }
        split = false;
    }

    //Moves all bullets on Screen
    public void moveBullets() {
        for (int i = 0; i < paddle.getBullets().size(); i++) {
            if (firePowerUp) {
                ((Fire) paddle.getBullets().get(i)).move();
            }
            if (!(((Fire) (paddle.getBullets().get(i))).isVisible())) {
                paddle.getBullets().remove(i);
            }

        }

    }

    // Brick Ball Collision
    public void brickBallCollision() {
        counter--;
        for (int i = 0; i < map.map.length; i++) {
            for (int j = 0; j < map.map[0].length; j++) {

                if (map.map[i][j] > 0) {
                    int brickX = j * map.brickWidth + 80;
                    int brickY = i * map.brickHeight + 50;
                    int brickwidth = map.brickWidth;
                    int brickheight = map.brickHeight;

                    Rectangle brickRect = new Rectangle(brickX, brickY, brickwidth, brickheight);

                    for (int k = 0; k < balls.size(); k++) {
                        Ball ball = balls.get(k);
                        Rectangle ballRect = ball.getBounds();
                        //Checks if Ball touches a brick
                        if (ballRect.intersects(brickRect)) {
                            int b = this.totalBricks;
                            //If ball touches a brick,brick removed and ball rebounds.
                            totalBricks = map.setBrickValue(i, j, totalBricks);
                            explodeX = brickX;
                            explodeY = brickY;
                            explodeW = brickwidth;
                            explodeH = brickheight;

                            // fall power up
                            if (firstPowerUp) {
                                p.setX(brickX);
                                p.setY(brickY);
                                firstPowerUp = false;
                            } else {
                                if (p.getY() > 700) {
                                    p.setX(brickX);
                                    p.setY(brickY);
                                }
                            }
                            if (map.check && !(map.brick[i][j] instanceof Brick4)) {
                                explosion = true;
                                int r = (int) (Math.random() * 7) + 1;
                                switch (r) {

                                    case 1:

                                        if (counter <= 0) {
                                            p.setPath(powerFactory.getPowerUp("Decrease"));
                                        }
                                        counter = 10;
                                        break;
                                    case 2:
                                        if (counter <= 0) {
                                            p.setPath(powerFactory.getPowerUp("Increase"));
                                        }
                                        counter = 10;
                                        break;
                                    case 3:
                                        if (counter <= 0) {
                                            p.setPath(powerFactory.getPowerUp("Fast"));
                                        }
                                        counter = 10;
                                        break;
                                    case 4:
                                        if (counter <= 0) {
                                            p.setPath(powerFactory.getPowerUp("Fire"));
                                        }
                                        counter = 10;
                                        break;
                                    case 5:
                                        if (counter <= 0) {
                                            p.setPath(powerFactory.getPowerUp("Life"));
                                        }
                                        counter = 10;
                                        break;
                                    case 6:
                                        if (counter <= 0) {
                                            p.setPath(powerFactory.getPowerUp("Slow"));
                                        }
                                        counter = 10;
                                        break;
                                    case 7:
                                        if (counter <= 0) {
                                            p.setPath(powerFactory.getPowerUp("Split"));
                                        }
                                        counter = 10;
                                        break;

                                }

                            }
                            
                            if (b != this.totalBricks) {
                                this.score += 5;
                            }

                            //Ball bounces
                            if (ballRect.x + ballRect.getWidth() - ball.ballX <= brickRect.x || ballRect.x - ball.ballX >= brickRect.x + brickRect.width) {
                                ball.reboundX();

                            } else {
                                ball.reboundY();
                            }
                        }

                    }
                    for (int k = 0; k < paddle.getBullets().size(); k++) {
                        Fire bullet = (Fire) paddle.getBullets().get(k);
                        Rectangle bulletRect = bullet.getBounds();

                        if (bulletRect.intersects(brickRect) && (this.map.map[i][j] != 0)) {
                            int b = this.totalBricks;
                            //If bullet touches a brick,brick removed 
                            totalBricks = map.setBrickValue(i, j, totalBricks);
                            if (b != this.totalBricks) {
                                this.score += 5;
                            }
                            // fall power up
                            if (p.getY() > 700) {
                                p.setX(brickX);
                                p.setY(brickY);
                            }

                            if (map.check && !(map.brick[i][j] instanceof Brick4)) {

                                int r = (int) (Math.random() * 8) + 1;
                                switch (r) {

                                    case 1:

                                        if (counter <= 0) {
                                            p.setPath(powerFactory.getPowerUp("Decrease"));
                                        }
                                        counter = 100;
                                        break;
                                    case 2:
                                        if (counter <= 0) {
                                            p.setPath(powerFactory.getPowerUp("Increase"));
                                        }
                                        counter = 100;
                                        break;
                                    case 3:
                                        if (counter <= 0) {
                                            p.setPath(powerFactory.getPowerUp("Fast"));
                                        }
                                        counter = 100;
                                        break;
                                    case 4:
                                        if (counter <= 0) {
                                            p.setPath(powerFactory.getPowerUp("Fire"));
                                        }
                                        counter = 100;
                                        break;
                                    case 5:
                                        if (counter <= 0) {
                                            p.setPath(powerFactory.getPowerUp("Life"));
                                        }
                                        counter = 100;
                                        break;
                                    case 6:
                                        if (counter <= 0) {
                                            p.setPath(powerFactory.getPowerUp("Slow"));
                                        }
                                        counter = 100;
                                        break;
                                    case 7:
                                        if (counter <= 0) {
                                            p.setPath(powerFactory.getPowerUp("Split"));
                                        }
                                        counter = 100;
                                        break;

                                }

                            }

                            //remove bullet after breaking brick
                            paddle.getBullets().remove(bullet);
                        }

                    }

                }
            }
        }

    }

    private class TAdapter extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent e) {
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_SPACE) {
            }

            if (key == KeyEvent.VK_ENTER) {

            }
            paddle.keyReleased(e);
        }

        @Override
        public void keyPressed(KeyEvent e) {
            //SPACE
            if (e.getKeyCode() == 32) {
                boolean moved = false;
                for (int m = 0; m < balls.size(); ++m) {
                    Ball ball = balls.get(m);
                    if (ball.ballStuck) {
                        ball.ballStuck = false;
                        ball.move();
                        moved = true;
                    }
                }
                if (Board.this.firePowerUp == true && !moved) {
                    Board.this.paddle.Fire();
                }

            }
            //ENTER
            if (e.getKeyCode() == 10) {
                Board.this.mainMenu = false;
                Board.this.NewGame();
            }
            // H
            if (e.getKeyCode() == 72) {
                Board.this.play = false;
                Board.this.NewGame();
                Board.this.mainMenu = false;
            }
            // L
            if (e.getKeyCode() == 76) {
                Board.this.fA.loadGame();
                Board.this.mainMenu = false;
            }
            // M
            if (e.getKeyCode() == 77) {
                System.exit(0);
            }
            // S
            if (e.getKeyCode() == 83) {
                Board.this.fA.saveGame();
                Board.this.mainMenu = true;
            }
            else {
                Board.this.paddle.keyPressed(e);
            }

        }

    }

    //starts a new game
    public void NewGame() {
        if (!this.play) {

            this.paddle.refillLives();
            this.play = true;
            firePowerUp = false;
            paddle.resetPaddle();
            paddle.resetPaddleSpeed();
            split = false;
            this.score = 0;
            p.setPath("");
            this.balls = new ArrayList();
            Paddle paddle = new Paddle("src/main/java/Paddles/MediumPaddle.png", 460, 630);
            this.score = 0;
            this.totalBricks = 24;
            this.map = new BrickMap(3, 8);
            this.repaint();
        }

    }

    //paints a menu screen
    public void paintMainMenu(Graphics g) {
        g.drawImage(menuImg, 0, 0, null);
    }

}
