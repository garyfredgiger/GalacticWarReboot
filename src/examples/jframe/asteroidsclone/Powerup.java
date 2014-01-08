package examples.jframe.asteroidsclone;

import java.awt.image.ImageObserver;

import game.framework.entities.EntityImage;
import game.framework.utilities.GameEngineConstants.EntityTypes;

public class Powerup extends EntityImage
{
  private Constants.PowerUpType powerupType;

  public Powerup(ImageObserver imageObserver)
  {
    super(imageObserver, EntityTypes.POWER_UP);

    powerupType = Constants.PowerUpType.UNDEFINED;
  }
  
  public void setPowerupType(Constants.PowerUpType type)
  {
    powerupType = type;
  }

  public Constants.PowerUpType getPowerupType()
  {
    return powerupType;
  }
}
