package galacticwarreboot.powerups;

import java.awt.image.ImageObserver;

import galacticwarreboot.Constants;
import galacticwarreboot.entities.PowerupEntity;

public class PowerupShield extends PowerupEntity
{

  public PowerupShield(ImageObserver imageObserver)
  {
    super(imageObserver);

    this.setPowerupType(Constants.PowerUpType.POWERUP_SHIELD);
    this.setAttributeType(Constants.AttributeType.ATTRIBUTE_SHIELD);
    this.setValue(Constants.POWERUP_SHIELD_VALUE);
    System.out.println("POWERUP Shield Spawned");
  }

}
