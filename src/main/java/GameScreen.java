/**
 * The program Main contains the main program for the game to run
 * @date 15th April 2021
 * @version 2.0
 * @copyright
 */
import java.awt.Color;
import javax.swing.JFrame;

public class GameScreen {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        JFrame obj = new JFrame();
        Board game = new Board();
        obj.setBounds(10,10,850,700);
        obj.setTitle("Brick Breaker");
        obj.setResizable(false);
        obj.setVisible(true);
        obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        obj.add(game);
        
    }

}
