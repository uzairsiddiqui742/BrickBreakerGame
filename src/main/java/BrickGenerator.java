/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Uzzi
 */
public class BrickGenerator {
    
    public Brick getBrick(int choice,int row, int col)
    {
        if(choice==1)
        {
            return new Brick1("src/main/java/Brick1/1.png");
        }
        else if(choice==2)
        {
            return new Brick2("src/main/java/Brick2/1.png");
        }
        else if(choice==3)
        {
            return new Brick3("src/main/java/Brick3/1.png");
        }
        else if(choice == 4)
        {
            return new Brick4("src/main/java/Brick4/1.png");
        }
        return null;
        
    }
}
