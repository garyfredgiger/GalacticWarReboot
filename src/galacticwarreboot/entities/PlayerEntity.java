package galacticwarreboot.entities;

import java.awt.image.ImageObserver;

import galacticwarreboot.Constants;
import galacticwarreboot.attributes.PlayerAttributes;
import game.framework.entities.EntityImage;
import game.framework.primitives.Position2D;
import game.framework.utilities.GameEngineConstants;

public class PlayerEntity extends EntityImage
{
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
  
  @Override
  public void kill()
  {
    // Don't quite kill the player yet if there is still health left
    if (attributes.getValue(Constants.AttributeType.ATTRIBUTE_HEALTH) > 0)
    {
      return;
    }

    // A call to the parent method will clear both the visible and dead flags of the player entity.
    super.kill();
  }
}
