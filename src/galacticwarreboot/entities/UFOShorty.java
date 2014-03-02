package galacticwarreboot.entities;

import galacticwarreboot.Constants;
import galacticwarreboot.ImageManager;
import galacticwarreboot.UFOEntityManager;
import java.awt.image.ImageObserver;

public class UFOShorty extends UFOEntity
{
  public UFOShorty(ImageObserver observer, UFOEntityManager manager, int upperHorizontalLimit, int lowerHorizontalLimit, int leftVerticalLimit, int rightVerticalLimit)
  {
    super(observer, manager, upperHorizontalLimit, lowerHorizontalLimit, leftVerticalLimit, rightVerticalLimit);
    this.setEnemyType(Constants.EnemyTypes.UFO_SHORTY);
    this.setImage(ImageManager.getImage(Constants.FILENAME_UFO_SHORTY));
    ufoHealth = Constants.UFO_SHORTY_HIT_POINTS;
    lastHitTime = System.currentTimeMillis();
  }

  @Override
  public boolean shouldFireShot()
  {
    if (System.currentTimeMillis() < (lastShotTime + Constants.UFO_SHORTY_SHOT_INTERVAL))
    {
      return false;
    }

    lastShotTime = System.currentTimeMillis();
    return true;
  }

  @Override
  public void kill()
  {
    if (ufoWentOffScreen)
    {
      super.kill();
    }

    // Give the UFO a little time to recover between hits. This will give the appearance of strength
    if (System.currentTimeMillis() < (lastHitTime + Constants.UFO_SHORTY_TIME_BETWEEN_HITS))
    {
      return;
    }

    ufoHealth--;
    lastHitTime = System.currentTimeMillis();
    if (ufoHealth <= 0)
    {
      super.kill();
    }
  }
}
