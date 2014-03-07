package galacticwarreboot.powerups;

import java.awt.image.ImageObserver;

import galacticwarreboot.Constants;
import galacticwarreboot.ScoreManager;
import galacticwarreboot.entities.PowerupEntity;

public class Powerup1000Points extends PowerupEntity
{
  public Powerup1000Points(ImageObserver imageObserver)
  {
    super(imageObserver);

    this.setPowerupType(Constants.PowerUpType.POWERUP_1000);
    this.setValue(Constants.POWERUP_1000_VALUE);
  }

  @Override
  public void kill()
  {
    ScoreManager.incrementScore(this.getValue());
    super.kill();
  }
}
