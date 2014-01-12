package galacticwarreboot.powerups;

import java.awt.image.ImageObserver;

import galacticwarreboot.Constants;
import galacticwarreboot.entities.PowerupEntity;

public class PowerupTheBomb extends PowerupEntity
{
  public PowerupTheBomb(ImageObserver imageObserver)
  {
    super(imageObserver);

    this.setPowerupType(Constants.PowerUpType.POWERUP_THE_BOMB);
    this.setAttributeType(Constants.AttributeType.ATTRIBUTE_THE_BOMB);
    this.setValue(Constants.POWERUP_THE_BOMB_VALUE);
    System.out.println("POWERUP The Bomb Spawned");
  }
}
