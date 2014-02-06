package galacticwarreboot.powerups;

import java.awt.image.ImageObserver;

import galacticwarreboot.Constants;
import galacticwarreboot.ScoreManager;
import galacticwarreboot.entities.PowerupEntity;

public class Powerup500Points extends PowerupEntity
{
  public Powerup500Points(ImageObserver imageObserver)
  {
    super(imageObserver);
    
    this.setPowerupType(Constants.PowerUpType.POWERUP_500);
    this.setValue(Constants.POWERUP_500_VALUE);
  }
  
  @Override
  public void kill()
  {
    ScoreManager.incrementScore(this.getValue());
    super.kill();
  }
}
