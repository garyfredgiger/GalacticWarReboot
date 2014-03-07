package galacticwarreboot.entities;

import java.awt.image.ImageObserver;

import galacticwarreboot.Constants;
import galacticwarreboot.Constants.PlayerShotType;
import game.framework.entities.EntityImage;
import game.framework.utilities.GameEngineConstants;

public class PlayerShotEntity extends EntityImage
{
  protected Constants.PlayerShotType shotType;

  public PlayerShotEntity(ImageObserver imageObserver)
  {
    this(imageObserver, Constants.PlayerShotType.UNDEFINED);
  }

  public PlayerShotEntity(ImageObserver imageObserver, Constants.PlayerShotType shotType)
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
