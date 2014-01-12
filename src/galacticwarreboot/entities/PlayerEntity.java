package galacticwarreboot.entities;

import java.awt.image.ImageObserver;

import galacticwarreboot.Constants;
import galacticwarreboot.Constants.AttributeType;
import galacticwarreboot.attributes.PlayerAttributes;
import game.framework.entities.EntityImage;
import game.framework.primitives.Position2D;
import game.framework.utilities.GameEngineConstants;

public class PlayerEntity extends EntityImage
{
//  private int firePower; 
//  private int health;
//  private int shield;
//  private int superShield;  // The count of this indicates how many super shiled powerups the player current has
//  private int theBomb;
//  private boolean autoShield;

  private PlayerAttributes attributes;
  private Position2D homePosition; 

  public PlayerEntity(ImageObserver imageObserver)
  {
    super(imageObserver, GameEngineConstants.EntityTypes.PLAYER);
    homePosition = new Position2D();
    
    // Be safe, initialize the variables
    attributes = new PlayerAttributes();
    attributes.initialize();
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
    attributes.initialize();
    moveToHomePosition();
    super.reset();
  }

  public void moveToHomePosition()
  {
    position.set(homePosition);
  }

  public void setValue(Constants.AttributeType powerupType, int value)
  {
    attributes.setValue(powerupType, value);
  }

  public int getValue(Constants.AttributeType powerupType)
  {
    return attributes.getValue(powerupType);
  }

  public void incrementByAmount(Constants.AttributeType powerupType, int amount)
  {
    attributes.incrementByAmount(powerupType, amount);
  }

  public void decrementByAmount(Constants.AttributeType powerupType, int amount)
  {
    attributes.decrementByAmount(powerupType, amount);
  }

  public void toggleFlag(Constants.AttributeType powerupType)
  {
    attributes.toggleFlag(powerupType);
  }
  
  public boolean isEquipped(Constants.AttributeType powerupType)
  {
    return attributes.isEquipped(powerupType);
  }
  
//  /*
//   * Methods regarding player's health
//   */
//  public void incrementHealthAmount(int newIncrement)
//  {
//    health += newIncrement;
//    
//    if (health > Constants.SHIP_STARTING_HEALTH)
//    {
//      health = Constants.SHIP_STARTING_HEALTH;
//    }
//  }
//
//  public void decrementHealthAmount()
//  {
//    decrementHealthAmount(1);
//  }
//  
//  public void decrementHealthAmount(int amount)
//  {
//    if (amount < 0)
//    {
//      return;
//    }
//    
//    health -= amount;
//  }
//  
//  public int getHealthAmount()
//  {
//    return health;
//  }
//
//  public boolean hasHealthRemaining()
//  {
//    return (health > 0 ? true : false);
//  }

//  /*
//   * Methods regarding player's shields
//   */
//  public void incrementShieldAmount(int newIncrement)
//  {
//    shield += newIncrement;
//    
//    if (shield > Constants.SHIP_STARTING_SHIELD)
//    {
//      shield = Constants.SHIP_STARTING_SHIELD;
//    }
//  }
//
//  public void decrementShieldAmount()
//  {
//    decrementShieldAmount(1);
//  }
//  
//  public void decrementShieldAmount(int amount)
//  {
//    if (amount < 0)
//    {
//      return;
//    }
//    
//    shield -= amount;
//  }
//  
//  public int getShieldAmount()
//  {
//    return shield;
//  }
//
//  public boolean hasShieldRemaining()
//  {
//    return (shield > 0 ? true : false);
//  }
  
//  /*
//   * Methods regarding player's firepower
//   */
//  public int getCurrentFirepower()
//  {
//    return firePower;
//  }
//  
//  public void reduceFirepower()
//  {
//    firePower--;
//    
//    if (firePower < Constants.SHIP_MIN_FIREPOWER)
//    {
//      firePower = Constants.SHIP_MIN_FIREPOWER;
//    }
//  }
//  
//  public void increaseFirepower()
//  {
//    firePower++;
//    
//    if (firePower > Constants.SHIP_MAX_FIREPOWER)
//    {
//      firePower = Constants.SHIP_MAX_FIREPOWER;
//    }
//  }
  
  /*
   * Methods for the super shields
   */
//  public int getCurrentSuperShieldCount()
//  {
//    return superShield;
//  }
//  
//  public void reduceSuperShield()
//  {
//    superShield--;
//    
//    if (superShield < 0)
//    {
//      superShield = 0;
//      //superShield = Constants.SHIP_STARTING_SUPER_SHIELD;
//    }
//  }
//
//  // The player can have as many super shields as they keep collecting 
//  public void increaseSuperShield()
//  {
//    superShield++;
//  }

  /*
   * Power-ups for The Bomb
   */
//  public boolean hasTheBomb()
//  {
//    return ((theBomb > 0) ? true : false);
//  }
//  
//  public void reduceTheBomb()
//  {
//    theBomb--;
//    
//    if (theBomb < 0)
//    {
//      theBomb = Constants.SHIP_STARTING_THE_BOMBS;
//    }
//  }
//
//  public int getTheBombCount()
//  {
//    return theBomb;
//  }

//  // The player can have as many super shields as they keep collecting 
//  public void increaseTheBomb()
//  {
//    theBomb++;
//  }
  
  @Override
  public void kill()
  {
    // Don't quite kill the player yet if there is still health left
    //if (health > 0)
    if (attributes.getValue(Constants.AttributeType.ATTRIBUTE_HEALTH) > 0)
    {
      return;
    }

    // A call to the parent method will clear both the visible and dead flags of the player entity.
    super.kill();
  }
  
//  private void initializeAttributes()
//  {
//    firePower = Constants.SHIP_STARTING_FIREPOWER;
//    health = Constants.SHIP_STARTING_HEALTH;       
//    shield = Constants.SHIP_STARTING_SHIELD;
//    superShield = Constants.SHIP_STARTING_SUPER_SHIELD;
//    theBomb = Constants.SHIP_STARTING_THE_BOMBS;
    //autoShield = Constants.PLAYER_DEFAULT_AUTO_SHIELD_SETTING;
//  }
}
