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
  }
  
  @Override
  public void kill()
  {
    ScoreManager.incrementScore(this.getValue());
    super.kill();
  }
}
