/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Uzzi
 */
public class PowerUpGenerator {
    public String getPowerUp(String powerup)
    {
        if(powerup.equalsIgnoreCase("Decrease"))
        {
            return "src/main/java/PowerUps/DecreasePowerUp.png";
        }
        else if(powerup.equalsIgnoreCase("Increase"))
        {
            return "src/main/java/PowerUps/IncreasePowerUp.png";
        }
        else if(powerup.equalsIgnoreCase("Fast"))
        {
            return "src/main/java/PowerUps/FastPowerUp.png";
        }
        else if(powerup.equalsIgnoreCase("Life"))
        {
            return "src/main/java/PowerUps/lifegainPowerUp.png";
        }
        else if(powerup.equalsIgnoreCase("Slow"))
        {
            return "src/main/java/PowerUps/SlowPowerUp.png";
        }
        else if(powerup.equalsIgnoreCase("Split"))
        {
            return "src/main/java/PowerUps/SplitballPowerUp.png";
        }
        else if(powerup.equalsIgnoreCase("Fire"))
        {
            return "src/main/java/PowerUps/FirePowerUp.png";
        }
        return "";
    }
}
