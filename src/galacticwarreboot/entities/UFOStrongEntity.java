package galacticwarreboot.entities;

import galacticwarreboot.Constants;
import galacticwarreboot.ScoreManager;
import galacticwarreboot.UFOEntityManager;
import game.framework.entities.EntityImage;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.ImageObserver;

public class UFOStrongEntity extends UFOEntity
{
  private EntityImage[] ufoImages;
  private int ufoStrength;
  private long lastHitTime;
  private Image shieldImage;
  private boolean ufoWentOffScreen;
  
  public UFOStrongEntity(ImageObserver observer, EntityImage[] ufoImages, UFOEntityManager manager, int upperHorizontalLimit, int lowerHorizontalLimit, int leftVerticalLimit, int rightVerticalLimit)
  {
    super(observer, ufoImages[0].getImage(), manager, upperHorizontalLimit, lowerHorizontalLimit, leftVerticalLimit, rightVerticalLimit);
    System.out.println("Launching Super UFO.");
    this.ufoImages = ufoImages;
    ufoStrength = 3;
    this.shieldImage = ufoImages[ufoStrength].getImage();
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
      case 2:
      case 1:
        this.shieldImage = ufoImages[ufoStrength].getImage();
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
        // The transform was already applied to the call to the parent draw mwthod.
        //this.transform();
        g.drawImage(this.shieldImage, at, imageObserver);
      }
    }
  }
}
