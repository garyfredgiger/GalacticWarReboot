package examples.jframe.asteroidsclone;

import java.awt.image.ImageObserver;

public class SuperShield extends PlayerShot
{
  public SuperShield(ImageObserver imageObserver)
  {
    super(imageObserver, Constants.PlayerShotType.SUPER_SHIELD);
  }
}
