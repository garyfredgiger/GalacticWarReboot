package galacticwarreboot.powerups;

import java.awt.image.ImageObserver;

import galacticwarreboot.Constants;
import galacticwarreboot.ScoreManager;
import galacticwarreboot.entities.PowerupEntity;

public class Powerup250Points extends PowerupEntity
{
  private int i;
  public Powerup250Points(ImageObserver imageObserver)
  {
    super(imageObserver);

    this.setPowerupType(Constants.PowerUpType.POWERUP_250);
    this.setValue(Constants.POWERUP_250_VALUE);
    //System.out.println("POWERUP 250 Points Spawned");
    i = 0;
  }
  
  @Override
  public void kill()
  {
    i++;
    ScoreManager.incrementScore(this.getValue());
    System.out.println("Kill Called: " + i + " Powerup 250 Value: " + this.getValue());
    super.kill();
  }
}
