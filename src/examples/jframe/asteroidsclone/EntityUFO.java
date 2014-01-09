package examples.jframe.asteroidsclone;

import game.framework.utilities.GameUtility;

import java.awt.Image;
import java.awt.image.ImageObserver;

public class EntityUFO extends EntityEnemy
{
  private int              endingXPosition;
  private int              upperHorizontalLimit;
  private int              lowerHorizontalLimit;
  private int              leftVerticalLimit;
  private int              rightVerticalLimit;
  private boolean          movingRight;
  private long             lastShotTime;

  // TODO: Is this only needed in the constructor?
  private UFOEntityManager manager;

  public EntityUFO(ImageObserver observer, Image ufoImage, UFOEntityManager manager, int upperHorizontalLimit, int lowerHorizontalLimit, int leftVerticalLimit, int rightVerticalLimit)
  //public EntityUFO(ImageObserver observer, Image ufoImage, int upperHorizontalLimit, int lowerHorizontalLimit, int leftVerticalLimit, int rightVerticalLimit)
  {
    super(observer);

    if (manager == null)
    {
      throw new NullPointerException();
    }

    this.manager = manager;
    this.setImage(ufoImage);

    this.upperHorizontalLimit = upperHorizontalLimit;
    this.lowerHorizontalLimit = lowerHorizontalLimit;
    this.leftVerticalLimit = leftVerticalLimit;
    this.rightVerticalLimit = rightVerticalLimit;

    assignUFOInitialVelocityAndStartingPosition();

    this.setEnemyType(Constants.EnemyTypes.UFO);
    this.setPointValue((GameUtility.random.nextInt(10) + 1) * 100);

    lastShotTime = System.currentTimeMillis();
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
          this.kill();
        }
      }

      if (!movingRight)
      {
        // If the entity is moving left, check the case where it moves off the right side of the screen, when it does kill it off
        if ((this.position.x + this.getWidth()) < endingXPosition)
        {
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
  public void kill()
  {
    if (manager != null)
    {
      manager.reset();
    }
    super.kill();
  }
}
