package examples.jframe.asteroidsclone;

import java.awt.image.ImageObserver;

import game.framework.entities.EntityImage;
import game.framework.primitives.Position2D;
import game.framework.utilities.GameEngineConstants;

public class PlayerEntity extends EntityImage
{
  private int health;
  private int shield;
  private Position2D homePosition;
//  private double homePositionX;
//  private double homePositionY;

  public PlayerEntity(ImageObserver imageObserver, GameEngineConstants.EntityTypes type)
  {
    super(imageObserver, type);
    homePosition = new Position2D();
  }

  public void defineHomePosition(double homePositionX, double homePositionY)
  {
    homePosition.set(homePositionX, homePositionY);
  }

  /*
   * (non-Javadoc)
   * @see game.framework.entities.Entity#reset()
   * 
   * Called when a new game is started since this method restores full health
   */
  @Override
  public void reset()
  {
    health = Constants.PLAYER_STARTING_HEALTH;       
    shield = Constants.PLAYER_STARTING_SHIELD;
    moveToHomePosition();
    super.reset();
  }

  public void moveToHomePosition()
  {
    position.set(homePosition);
  }

  public void incrementHealthAmount(int newIncrement)
  {
    health += newIncrement;
  }

  public int getHealthAmount()
  {
    return health;
  }

  public boolean hasHealthRemaining()
  {
    return (health > 0 ? true : false);
  }

  public void incrementShieldAmount(int newIncrement)
  {
    shield += newIncrement;
  }

  public void decrementShieldAmount()
  {
    shield--;
  }
  
  public int getShieldAmount()
  {
    return shield;
  }

  public boolean hasShieldRemaining()
  {
    return (shield > 0 ? true : false);
  }
  
  @Override
  public void kill()
  {
    // Decrement the number of lives and update the additional lives flag 
    health--;

    // Don't quite kill the player yet if there is still health left
    if (health > 0)
    {
      return;
    }

    // A call to the parent method will clear both the visible and dead flags of the player entity.
    super.kill();
  }
}
