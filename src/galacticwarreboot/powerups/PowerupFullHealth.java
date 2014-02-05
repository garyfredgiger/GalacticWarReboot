package galacticwarreboot.powerups;

import java.awt.image.ImageObserver;

import galacticwarreboot.Constants;
import galacticwarreboot.entities.PowerupEntity;

public class PowerupFullHealth extends PowerupEntity
{
  public PowerupFullHealth(ImageObserver imageObserver)
  {
    super(imageObserver);
    
    this.setPowerupType(Constants.PowerUpType.POWERUP_FULL_HEALTH);
    this.setAttributeType(Constants.AttributeType.ATTRIBUTE_HEALTH);
    this.setValue(Constants.SHIP_INITIAL_HEALTH);
    //System.out.println("POWERUP Full Health Spawned");
  }
}
