package galacticwarreboot.powerups;

import galacticwarreboot.Constants;
import galacticwarreboot.entities.PowerupEntity;

import java.awt.image.ImageObserver;

public class PowerupThrust extends PowerupEntity
{
  public PowerupThrust(ImageObserver imageObserver)
  {
    super(imageObserver);

    this.setPowerupType(Constants.PowerUpType.POWERUP_THRUST);
    this.setAttributeType(Constants.AttributeType.ATTRIBUTE_THRUST);
    this.setValue(Constants.SHIP_DEFAULT_ACCELERATION);
  }
}
