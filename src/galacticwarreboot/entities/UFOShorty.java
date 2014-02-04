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
    System.out.println("Launching UFO Shorty.");
    lastHitTime = System.currentTimeMillis();
    this.setEnemyType(Constants.EnemyTypes.UFO_SHORTY);
    ufoHealth = Constants.UFO_SHORTY_HIT_POINTS;
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
          System.out.println("updatePosition - Shorty::UFO Just went off RIGHT side of screen. Ready to Kill it off. Setting ufoWentOffScreen flag.");
          ufoWentOffScreen = true;
        }
      }

      if (!movingRight)
      {
        // If the entity is moving left, check the case where it moves off the right side of the screen, when it does kill it off
        if ((this.position.x + this.getWidth()) < endingXPosition)
        {
          System.out.println("updatePosition - Shorty::UFO Just went off LEFT side of screen. Setting ufoWentOffScreen flag.");
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
      System.out.println("UFO SHORTY KILL::UFO is now off screen. Killing it!");
      super.kill();
    }

    // Give the UFO a little time to recover between hits. This will give the appearance of strength
    if (System.currentTimeMillis() < (lastHitTime + Constants.UFO_SHORTY_TIME_BETWEEN_HITS))
    {
      System.out.println("UFO Shorty Taking Damage!");
      return;
    }

    ufoHealth--;
    lastHitTime = System.currentTimeMillis();
    System.out.println("UFO Shorty loses health! Now at " + ufoHealth);
    if (ufoHealth <= 0)
    {
      super.kill();
    }
  }
}
