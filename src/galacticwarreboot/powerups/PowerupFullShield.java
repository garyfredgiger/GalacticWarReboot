package galacticwarreboot.powerups;

import java.awt.image.ImageObserver;

import galacticwarreboot.Constants;
import galacticwarreboot.entities.PowerupEntity;

public class PowerupFullShield extends PowerupEntity
{

  public PowerupFullShield(ImageObserver imageObserver)
  {
    super(imageObserver);

    this.setPowerupType(Constants.PowerUpType.POWERUP_FULL_SHIELD);
    this.setAttributeType(Constants.AttributeType.ATTRIBUTE_SHIELD);
    this.setValue(Constants.SHIP_STARTING_SHIELD);
    System.out.println("POWERUP Full Shield Spawned");
  }

}
