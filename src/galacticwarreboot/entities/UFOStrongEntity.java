package galacticwarreboot.entities;

import galacticwarreboot.Constants;
import galacticwarreboot.ImageManager;
import galacticwarreboot.UFOEntityManager;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.ImageObserver;

public class UFOStrongEntity extends UFOEntity
{
  private Image shieldImage;
  private boolean ufoHullHit;

  // TODO: Since there is an image manager, the parameter ufoImages may go away
  public UFOStrongEntity(ImageObserver observer, UFOEntityManager manager, int upperHorizontalLimit, int lowerHorizontalLimit, int leftVerticalLimit, int rightVerticalLimit)
  {
    super(observer, ImageManager.getImage(Constants.FILENAME_UFO), manager, upperHorizontalLimit, lowerHorizontalLimit, leftVerticalLimit, rightVerticalLimit);
    setEnemyType(Constants.EnemyTypes.UFO_STRONG);

    ufoHealth = 3;
    shieldImage = ImageManager.getImage(Constants.FILENAME_UFO_SHIELD_STRONG);
    lastHitTime = System.currentTimeMillis();
    ufoHullHit = false;
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
  public void kill()
  {
    if (ufoWentOffScreen)
    {
      //System.out.println("UFOStrongEntity::kill() - UFO Went Off Screen.");
      super.kill();
    }

    // Give the UFO a little time to recover between hits. This will give the appearance of strength
    if (System.currentTimeMillis() < (lastHitTime + Constants.UFO_TIME_BETWEEN_SHIELD_HITS))
    {
      return;
    }

    // Each time the UFO gets hit
    ufoHealth--;
    lastHitTime = System.currentTimeMillis();
    switch(ufoHealth)
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
