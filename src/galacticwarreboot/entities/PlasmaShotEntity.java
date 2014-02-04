package galacticwarreboot.entities;

import galacticwarreboot.Constants;
import java.awt.image.ImageObserver;

public class PlasmaShotEntity extends PlayerShotEntity
{
  public PlasmaShotEntity(ImageObserver imageObserver)
  {
    super(imageObserver, Constants.PlayerShotType.PLASMA);
  }
}
