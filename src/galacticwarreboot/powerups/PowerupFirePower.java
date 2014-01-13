package galacticwarreboot.powerups;

import java.awt.image.ImageObserver;

import galacticwarreboot.Constants;
import galacticwarreboot.entities.PowerupEntity;

public class PowerupFirePower extends PowerupEntity
{
  public PowerupFirePower(ImageObserver imageObserver)
  {
    super(imageObserver);

    this.setPowerupType(Constants.PowerUpType.POWERUP_FIREPOWER);
    this.setAttributeType(Constants.AttributeType.ATTRIBUTE_FIREPOWER);
    this.setValue(Constants.POWERUP_FIREPOWER_VALUE);
    //System.out.println("POWERUP Fire Power Spawned");
  }
}
