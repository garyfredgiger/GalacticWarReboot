package examples.jframe.asteroidsclone;

import java.awt.image.ImageObserver;

import game.framework.entities.EntityImage;
import game.framework.utilities.GameEngineConstants;

public class PlayerShot extends EntityImage
{
  protected Constants.PlayerShotType shotType; 
  
  public PlayerShot(ImageObserver imageObserver)
  {
    this(imageObserver, Constants.PlayerShotType.UNDEFINED);
  }
  
  public PlayerShot(ImageObserver imageObserver, Constants.PlayerShotType shotType)
  {
    super(imageObserver, GameEngineConstants.EntityTypes.PLAYER_SHOT);
    setShotType(shotType);
  }

  public void setShotType(Constants.PlayerShotType type)
  {
    shotType = type;
  }

  public Constants.PlayerShotType getShotType()
  {
    return shotType;
  }
}
