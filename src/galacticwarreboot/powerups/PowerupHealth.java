package galacticwarreboot.powerups;

import java.awt.image.ImageObserver;

import galacticwarreboot.Constants;
import galacticwarreboot.entities.PowerupEntity;

public class PowerupHealth extends PowerupEntity
{
  public PowerupHealth(ImageObserver imageObserver)
  {
    super(imageObserver);

    this.setPowerupType(Constants.PowerUpType.POWERUP_HEALTH);
    this.setAttributeType(Constants.AttributeType.ATTRIBUTE_HEALTH);
    this.setValue(Constants.POWERUP_HEALTH_VALUE);
    System.out.println("POWERUP Health Spawned");
  }
}
