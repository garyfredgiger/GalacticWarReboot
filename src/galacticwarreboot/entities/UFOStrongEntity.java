package galacticwarreboot.entities;

import galacticwarreboot.Constants;
import galacticwarreboot.ImageManager;
import galacticwarreboot.ScoreManager;
import galacticwarreboot.UFOEntityManager;
import game.framework.entities.EntityImage;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.ImageObserver;

public class UFOStrongEntity extends UFOEntity
{
  private int ufoStrength;
  private long lastHitTime;
  private Image shieldImage;
  private boolean ufoWentOffScreen;
  
  private boolean ufoHullHit;
  
  // TODO: Since there is an image manager, the parameter ufoImages may go away
  //public UFOStrongEntity(ImageObserver observer, EntityImage[] ufoImages, UFOEntityManager manager, int upperHorizontalLimit, int lowerHorizontalLimit, int leftVerticalLimit, int rightVerticalLimit)
  public UFOStrongEntity(ImageObserver observer, UFOEntityManager manager, int upperHorizontalLimit, int lowerHorizontalLimit, int leftVerticalLimit, int rightVerticalLimit)
  {
    super(observer, ImageManager.getImage(Constants.FILENAME_UFO), manager, upperHorizontalLimit, lowerHorizontalLimit, leftVerticalLimit, rightVerticalLimit);
    ufoStrength = 3;
    this.shieldImage = ImageManager.getImage(Constants.FILENAME_UFO_SHIELD_STRONG);
    lastHitTime = System.currentTimeMillis();
    this.setEnemyType(Constants.EnemyTypes.UFO_STRONG);
    ufoHullHit = false;
  }

  public int getUfoStrength()
  {
    return ufoStrength;
  }

  public void ufoHullWasHit()
  {
    ufoHullHit = true;
  }
  
  public boolean wasUfoHullHit()
  {
    return ufoHullHit;
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
      System.out.println("UFOStrongEntity::lkill() - UFO Went Off Screen.");
      super.kill();
    }

    // Give the UFO a little time to recover between hits. This will give the appearance of strength
    if (System.currentTimeMillis() < (lastHitTime + Constants.UFO_TIME_BETWEEN_SHIELD_HITS))
    {
      return;
    }

    ufoStrength--;
    lastHitTime = System.currentTimeMillis();
    switch(ufoStrength)
    {
      // Choose the proper shield to draw
      case 3:
        this.shieldImage = ImageManager.getImage(Constants.FILENAME_UFO_SHIELD_STRONG);
        break;
      case 2:
        this.shieldImage = ImageManager.getImage(Constants.FILENAME_UFO_SHIELD_OK);
        break;
      case 1:
        this.shieldImage = ImageManager.getImage(Constants.FILENAME_UFO_SHIELD_WEAK);
        break;
      // Shields gone, ufo gets hit one more time and it will be dead 
      case 0:
        this.shieldImage = null;
        break;

      default:
        super.kill();
    }
  }

  @Override
  public void draw(Graphics2D g)
  {
    super.draw(g);
    if (isAlive() && isVisible())
    {
      if (this.shieldImage != null)
      {
        // The transform was already applied to the call to the parent draw method.
        //this.transform();
        g.drawImage(this.shieldImage, at, imageObserver);
      }
    }
  }
}
