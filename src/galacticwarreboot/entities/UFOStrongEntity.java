package galacticwarreboot.entities;

import galacticwarreboot.Constants;
import galacticwarreboot.UFOEntityManager;
import game.framework.entities.EntityImage;

import java.awt.image.ImageObserver;

public class UFOStrongEntity extends UFOEntity
{
  private EntityImage[] ufoImages;
  private int ufoStrength;
  private long lastHitTime;
  
  public UFOStrongEntity(ImageObserver observer, EntityImage[] ufoImages, UFOEntityManager manager, int upperHorizontalLimit, int lowerHorizontalLimit, int leftVerticalLimit, int rightVerticalLimit)
  {
    super(observer, ufoImages[3].getImage(), manager, upperHorizontalLimit, lowerHorizontalLimit, leftVerticalLimit, rightVerticalLimit);
    this.ufoImages = ufoImages;
    ufoStrength = 3;
    lastHitTime = System.currentTimeMillis();
  }
  
  @Override
  public boolean shouldFireShot()
  {
    if (System.currentTimeMillis() < (lastShotTime + Constants.UFO_SUPER_SHOT_INTERVAL))
    {
      return false;
    }

    lastShotTime = System.currentTimeMillis();
    return true;
  }
  
  @Override
  public void kill()
  {
    // Give the UFO a little time to recover between hits. This will give the appearance of strength
    if (System.currentTimeMillis() < (lastHitTime + Constants.UFO_TIME_BETWEEN_SHIELD_HITS))
    {
      return;
    }
    
    ufoStrength--;  
    lastHitTime = System.currentTimeMillis();
    
    if (ufoStrength >= 0)
    {
      this.image = ufoImages[ufoStrength].getImage();
    }
    else
    {
      super.kill();
    }
  }
}
