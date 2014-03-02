package galacticwarreboot.entities;

import galacticwarreboot.Constants;
import galacticwarreboot.ImageManager;
import galacticwarreboot.ScoreManager;
import galacticwarreboot.UFOEntityManager;
import game.framework.utilities.GameUtility;

import java.awt.image.ImageObserver;

public class UFOEntity extends EnemyEntity
{
  protected int            endingXPosition;
  protected int            upperHorizontalLimit;
  protected int            lowerHorizontalLimit;
  protected int            leftVerticalLimit;
  protected int            rightVerticalLimit;
  protected boolean        movingRight;

  protected long           lastShotTime;
  protected long           lastHitTime;
  protected boolean        ufoWentOffScreen;

  protected int            ufoHealth;
  
  // TODO: Is this only needed in the constructor?
  private UFOEntityManager manager;

  public UFOEntity(ImageObserver observer, UFOEntityManager manager, int upperHorizontalLimit, int lowerHorizontalLimit, int leftVerticalLimit, int rightVerticalLimit)
  {
    super(observer);

    if (manager == null)
    {
      throw new NullPointerException();
    }

    this.manager = manager;
    this.setImage(ImageManager.getImage(Constants.FILENAME_UFO));

    this.upperHorizontalLimit = upperHorizontalLimit;
    this.lowerHorizontalLimit = lowerHorizontalLimit;
    this.leftVerticalLimit = leftVerticalLimit;
    this.rightVerticalLimit = rightVerticalLimit;
    ufoWentOffScreen = false;

    assignUFOInitialVelocityAndStartingPosition();
    ufoHealth = 1;

    this.setEnemyType(Constants.EnemyTypes.UFO);
    this.setPointValue((GameUtility.random.nextInt(10) + 1) * 100);

    lastShotTime = System.currentTimeMillis();    
  }

  public int getUfoHealth()
  {
    return ufoHealth;
  }

  public boolean shouldFireShot()
  {
    if (System.currentTimeMillis() < (lastShotTime + Constants.UFO_SHOT_INTERVAL))
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
      /*
       * Check when the UFO moves off either the left or right side of the screen
       */

      // If the entity is moving right, check the case where it moves off the right side of the screen, when it does kill it off 
      if (movingRight)
      {
        if (this.position.x > endingXPosition)
        {
          //System.out.println("updatePosition - UFO Entity::UFO Just went off RIGHT side of screen. Ready to Kill it off.");
          ufoWentOffScreen = true;
          this.kill();
        }
      }

      if (!movingRight)
      {
        // If the entity is moving left, check the case where it moves off the right side of the screen, when it does kill it off
        if ((this.position.x + this.getWidth()) < endingXPosition)
        {
          //System.out.println("updatePosition - UFO Entity::UFO Just went off LEFT side of screen. Ready to Kill it off.");
          ufoWentOffScreen = true;
          this.kill();
        }
      }

      // Check if the UFO has reached either of its horizontal limits, if it has change the direction
      if ((this.position.y < lowerHorizontalLimit) || ((this.position.y + this.getHeight()) > upperHorizontalLimit))
      {
        velocity.y = -velocity.y;
      }
    }

    super.updatePosition(delta);
  }

  @Override
  public void kill()
  {
    //System.out.println("UFO Entity Kill. Resetting manager.");
    if (manager != null)
    {
      manager.reset();
    }
    
    if (!ufoWentOffScreen)
    {
      ScoreManager.incrementScore(this.getPointValue());
    }
    
    super.kill();
  }
  
  /*
   * Private methods
   */
  private void assignUFOInitialVelocityAndStartingPosition()
  {
    // Assign this entity a random velocity
    this.velocity = GameUtility.computeRandomVelocity();
    velocity.scaleThisVector(Constants.UFO_SPEED);

    // If the x component of the velocity is positive, the UFO will move from left to right (i.e., moving left)
    if (velocity.x > 0)
    {
      // Therefore the ending limit will be right side of the screen.
      endingXPosition = rightVerticalLimit;
      movingRight = true;
    }
    // If the x component of the velocity is negative, the UFO will move from right to left
    else
    {
      // Therefore the ending limit will be left side of the screen.
      endingXPosition = leftVerticalLimit;
      movingRight = false;
    }

    // Start the UFO off the right side of the screen
    if (endingXPosition == rightVerticalLimit)
    {
      this.position.x = leftVerticalLimit - this.getWidth();
    }
    // Start the UFO off the left side of the screen
    else
    {
      this.position.x = rightVerticalLimit + this.getWidth();
    }

    this.position.y = lowerHorizontalLimit + (int) (GameUtility.random.nextDouble() * ((upperHorizontalLimit - lowerHorizontalLimit) + 1));
  }
}
