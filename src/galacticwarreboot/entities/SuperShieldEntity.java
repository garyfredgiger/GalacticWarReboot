package galacticwarreboot.entities;

import galacticwarreboot.Constants.PlayerShotType;

import java.awt.image.ImageObserver;

public class SuperShieldEntity extends PlayerShotEntity
{
  public SuperShieldEntity(ImageObserver imageObserver)
  {
    super(imageObserver, PlayerShotType.SUPER_SHIELD);
  }
}
