package galacticwarreboot.entities;

import galacticwarreboot.Constants;
import galacticwarreboot.ScoreManager;
import galacticwarreboot.UFOEntityManager;
import game.framework.entities.EntityImage;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.ImageObserver;

public class UFOShorty extends UFOEntity
{
  private long     ufoHealth;
  private long    lastHitTime;

  public UFOShorty(ImageObserver observer, EntityImage ufoShortyImage, UFOEntityManager manager, int upperHorizontalLimit, int lowerHorizontalLimit, int leftVerticalLimit, int rightVerticalLimit)
  {
    super(observer, ufoShortyImage.getImage(), manager, upperHorizontalLimit, lowerHorizontalLimit, leftVerticalLimit, rightVerticalLimit);
    this.setEnemyType(Constants.EnemyTypes.UFO_SHORTY);
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
  public void updatePosition(double delta)
  {
    if (isAlive())
    {
      if (movingRight)
      {
        if (this.position.x > endingXPosition)
        {
          ufoWentOffScreen = true;
        }
      }

      if (!movingRight)
      {
        // If the entity is moving left, check the case where it moves off the right side of the screen, when it does kill it off
        if ((this.position.x + this.getWidth()) < endingXPosition)
        {
          ufoWentOffScreen = true;
        }
      }
    }

    super.updatePosition(delta);
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
