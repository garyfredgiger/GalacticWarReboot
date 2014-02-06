package galacticwarreboot.powerups;

import java.awt.image.ImageObserver;

import galacticwarreboot.Constants;
import galacticwarreboot.entities.PowerupEntity;

// TODO: Not sure how this will be used. 

public class PowerupIncreasedShieldCapacity extends PowerupEntity
{
  public PowerupIncreasedShieldCapacity(ImageObserver imageObserver)
  {
    super(imageObserver);
    
    this.setPowerupType(Constants.PowerUpType.POWERUP_INCREASE_SHIELD_CAPACITY);
    this.setAttributeType(Constants.AttributeType.ATTRIBUTE_SHIELD);
    this.setValue(Constants.SHIP_SHIELD_CAPACITY_INCREASE_TO_20);
  }
}
