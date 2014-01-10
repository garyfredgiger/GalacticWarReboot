package examples.jframe.asteroidsclone;

import java.awt.image.ImageObserver;

public class PlasmaShot extends PlayerShot
{
  public PlasmaShot(ImageObserver imageObserver)
  {
    super(imageObserver, Constants.PlayerShotType.PLASMA);
  }
}
