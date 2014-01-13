package galacticwarreboot.powerups;

import java.awt.image.ImageObserver;

import galacticwarreboot.Constants;
import galacticwarreboot.entities.PowerupEntity;

public class PowerupSuperShield extends PowerupEntity
{
  public PowerupSuperShield(ImageObserver imageObserver)
  {
    super(imageObserver);

    this.setPowerupType(Constants.PowerUpType.POWERUP_SUPER_SHIELD);
    this.setAttributeType(Constants.AttributeType.ATTRIBUTE_SUPER_SHIELD);
    this.setValue(Constants.POWERUP_SUPER_SHIELD_VALUE);
    //System.out.println("POWERUP Super Shield Spawned");
  }
}
