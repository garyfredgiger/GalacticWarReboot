package examples.jframe.asteroidsclone;

import java.awt.image.ImageObserver;

import game.framework.entities.EntityImage;
import game.framework.primitives.Position2D;
import game.framework.utilities.GameEngineConstants;

public class PlayerEntity extends EntityImage
{
  private int health;
  private int shield;
  private int firePower;
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
    firePower = Constants.SHIP_STARTING_FIREPOWER;
    health = Constants.SHIP_STARTING_HEALTH;       
    shield = Constants.SHIP_STARTING_SHIELD;
    moveToHomePosition();
    super.reset();
  }

  public void moveToHomePosition()
  {
    position.set(homePosition);
  }

  /*
   * Methods regarding player's health
   */
  public void incrementHealthAmount(int newIncrement)
  {
    health += newIncrement;
    
    if (health > Constants.SHIP_STARTING_HEALTH)
    {
      health = Constants.SHIP_STARTING_HEALTH;
    }
  }

  public void decrementHealthAmount()
  {
    decrementHealthAmount(1);
  }
  
  public void decrementHealthAmount(int amount)
  {
    if (amount < 0)
    {
      return;
    }
    
    health -= amount;
  }
  
  public int getHealthAmount()
  {
    return health;
  }

  public boolean hasHealthRemaining()
  {
    return (health > 0 ? true : false);
  }

  /*
   * Methods regarding player's shields
   */
  public void incrementShieldAmount(int newIncrement)
  {
    shield += newIncrement;
    
    if (shield > Constants.SHIP_STARTING_SHIELD)
    {
      shield = Constants.SHIP_STARTING_SHIELD;
    }
  }

  public void decrementShieldAmount()
  {
    decrementShieldAmount(1);
  }
  
  public void decrementShieldAmount(int amount)
  {
    if (amount < 0)
    {
      return;
    }
    
    shield -= amount;
  }
  
  public int getShieldAmount()
  {
    return shield;
  }

  public boolean hasShieldRemaining()
  {
    return (shield > 0 ? true : false);
  }
  
  /*
   * Methods regarding player's firepower
   */
  public int getCurrentFirepower()
  {
    return firePower;
  }
  
  public void reduceFirepower()
  {
    firePower--;
    
    if (firePower < Constants.SHIP_MIN_FIREPOWER)
    {
      firePower = Constants.SHIP_MIN_FIREPOWER;
    }
  }
  
  public void increaseFirepower()
  {
    firePower++;
    
    if (firePower > Constants.SHIP_MAX_FIREPOWER)
    {
      firePower = Constants.SHIP_MAX_FIREPOWER;
    }
  }
  
  @Override
  public void kill()
  {
    // Decrement the number of lives and update the additional lives flag 
    //health--;

    // Don't quite kill the player yet if there is still health left
    if (health > 0)
    {
      return;
    }

    // A call to the parent method will clear both the visible and dead flags of the player entity.
    super.kill();
  }
}
