package galacticwarreboot.powerups;

import java.awt.image.ImageObserver;

import galacticwarreboot.Constants;
import galacticwarreboot.entities.PowerupEntity;

// TODO: Not sure how this will be used.

public class PowerupIncreasedHealthCapacity extends PowerupEntity
{
  public PowerupIncreasedHealthCapacity(ImageObserver imageObserver)
  {
    super(imageObserver);

    this.setPowerupType(Constants.PowerUpType.POWERUP_INCREASE_HEALTH_CAPACITY);
    this.setAttributeType(Constants.AttributeType.ATTRIBUTE_HEALTH);
    this.setValue(Constants.SHIP_HEALTH_CAPACITY_INCREASE_TO_20);
  }
}
