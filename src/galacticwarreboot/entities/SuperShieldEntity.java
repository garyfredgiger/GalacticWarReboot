package galacticwarreboot.entities;

import galacticwarreboot.Constants;
import galacticwarreboot.Constants.PlayerShotType;

import java.awt.image.ImageObserver;

public class SuperShieldEntity extends PlayerShotEntity
{
  public SuperShieldEntity(ImageObserver imageObserver)
  {
    super(imageObserver, Constants.PlayerShotType.SUPER_SHIELD);
  }
}
