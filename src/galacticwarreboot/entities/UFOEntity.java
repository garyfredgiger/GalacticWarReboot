package galacticwarreboot.entities;

import galacticwarreboot.Constants;
import galacticwarreboot.ImageManager;
import galacticwarreboot.ScoreManager;
import galacticwarreboot.UFOEntityManager;
import game.framework.primitives.Vector2D;
import game.framework.utilities.GameUtility;

import java.awt.Image;
import java.awt.image.ImageObserver;

public class UFOEntity extends EnemyEntity
{
  protected int            endingXPosition;
  protected int            topOfScreenVerticalLimit;
  protected int            bottomOfScreenVerticalLimit;
  protected int            leftVerticalLimit;
  protected int            rightVerticalLimit;
  protected boolean        movingRight;
  int                      initialHeadingAngle;

  protected long           lastShotTime;
  protected long           lastHitTime;
  protected boolean        ufoWentOffScreen;

  protected int            ufoHealth;

  // TODO: Is this only needed in the constructor?
  private UFOEntityManager manager;

  public UFOEntity(ImageObserver observer, UFOEntityManager manager, int topOfScreenVerticalLimit, int bottomOfScreenVerticalLimit, int leftVerticalLimit, int rightVerticalLimit)
  {
    this(observer, ImageManager.getImage(Constants.FILENAME_UFO), manager, topOfScreenVerticalLimit, bottomOfScreenVerticalLimit, leftVerticalLimit, rightVerticalLimit);
  }

  public UFOEntity(ImageObserver observer, Image ufoImage, UFOEntityManager manager, int topOfScreenVerticalLimit, int bottomOfScreenVerticalLimit, int leftVerticalLimit, int rightVerticalLimit)
  {
    super(observer);

    if (manager == null)
    {
      throw new NullPointerException();
    }

    this.manager = manager;
    this.setImage(ufoImage);

    this.topOfScreenVerticalLimit = topOfScreenVerticalLimit;
    this.bottomOfScreenVerticalLimit = bottomOfScreenVerticalLimit;
    this.leftVerticalLimit = leftVerticalLimit;
    this.rightVerticalLimit = rightVerticalLimit;
    ufoWentOffScreen = false;

    assignUFOInitialVelocityAndStartingPosition();
    ufoHealth = 1;

    this.setEnemyType(Constants.EnemyTypes.UFO);
    this.setPointValue((GameUtility.random.nextInt(10) + 1) * 100);

    lastShotTime = System.currentTimeMillis();

    System.out.println("Spawning a UFO of Type: " + this.getEnemyType().toString());
    System.out.println(toString());
  }

  public int getUfoHealth()
  {
    return ufoHealth;
  }

  @Override
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

      // Check if the UFO has reached either of its vertical limits, if it has change the direction
      if ((this.position.y < topOfScreenVerticalLimit) || ((this.position.y + this.getHeight()) > bottomOfScreenVerticalLimit))
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
    // First, pick a direction. The UFO will either move from left to right or right to left
    movingRight = GameUtility.random.nextBoolean();

    if (movingRight)
    {
      // If the UFO will be moving right, its ending x position will be the right side of the screen 
      endingXPosition = rightVerticalLimit;

      // Make the starting x position off the left side of the screen
      this.position.x = leftVerticalLimit - this.getWidth();

      // Since UFO is moving right, we want it to move at a vector between UFO_MOVING_RIGHT_MIN_ANGLE_IN_DEGREES degrees and UFO_MOVING_RIGHT_MAX_ANGLE_IN_DEGREES degrees 
      initialHeadingAngle = (int) ((Math.random() * (Constants.UFO_MOVING_RIGHT_MAX_ANGLE_IN_DEGREES - Constants.UFO_MOVING_RIGHT_MIN_ANGLE_IN_DEGREES)) + Constants.UFO_MOVING_RIGHT_MIN_ANGLE_IN_DEGREES);
    }
    else
    {
      // If the UFO will be moving left, its ending x position will be the left side of the screen 
      endingXPosition = leftVerticalLimit;

      // Make the starting x position off the right side of the screen
      this.position.x = rightVerticalLimit + this.getWidth();

      // Since UFO is moving left, we want it to move at a vector between UFO_MOVING_LEFT_MIN_ANGLE_IN_DEGREES degrees and UFO_MOVING_LEFT_MAX_ANGLE_IN_DEGREES degrees 
      initialHeadingAngle = (int) ((Math.random() * (Constants.UFO_MOVING_LEFT_MAX_ANGLE_IN_DEGREES - Constants.UFO_MOVING_LEFT_MIN_ANGLE_IN_DEGREES)) + Constants.UFO_MOVING_LEFT_MIN_ANGLE_IN_DEGREES);
    }

    // Create the unit vector given the heading angle. Note that since in the game world, unit vector 
    // [0, 1] is facing 3 O'Clock rather than noon, we must rotate CCW by 90 degrees to get the movement 
    // in the right orientation. 
    this.velocity = GameUtility.computeUnitVector(initialHeadingAngle);
    this.velocity.rotateThisVector(-90);
    this.velocity.scaleThisVector(Constants.UFO_SPEED);

    // Make the y position between the defined vertical bounds.
    this.position.y = topOfScreenVerticalLimit + (int) (GameUtility.random.nextDouble() * ((bottomOfScreenVerticalLimit - topOfScreenVerticalLimit) + 1));
  }

  // Debugging
  @Override
  public String toString()
  {
    String entitySnapshot = super.toString();

    entitySnapshot += "Vertical Bounds: [" + topOfScreenVerticalLimit + ", " + bottomOfScreenVerticalLimit + "]\n";
    entitySnapshot += "Horizontal Direction: " + (movingRight ? "Right" : "Left") + "\n";
    entitySnapshot += "Heading Angle: " + initialHeadingAngle + "\n";
    entitySnapshot += "Ending X Position: " + endingXPosition + "\n";

    return entitySnapshot;
  }
}
