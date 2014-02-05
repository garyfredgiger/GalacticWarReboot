package galacticwarreboot.entities;

import java.awt.Graphics2D;
import java.awt.image.ImageObserver;

import galacticwarreboot.Constants;
import galacticwarreboot.Constants.AttributeType;
import galacticwarreboot.attributes.PlayerAttributes;
import game.framework.entities.EntityImage;
import game.framework.primitives.Position2D;
import game.framework.utilities.GameEngineConstants;

public class PlayerEntity extends EntityImage
{
  private PlayerAttributes attributes;
  private Position2D homePosition; 

  // Variables that indicate whether to draw the shields and thrust given the current user key strokes.
  private boolean drawShield;
  private boolean drawThrust;

  EntityImage[] spaceshipImages;
  
  // TODO: Add the image array to the 
  public PlayerEntity(ImageObserver imageObserver, EntityImage[] spaceshipImages)
  {
    super(imageObserver, GameEngineConstants.EntityTypes.PLAYER);
    homePosition = new Position2D();
    
    // Be safe, initialize the variables
    attributes = new PlayerAttributes();
    //attributes.initialize();

    this.spaceshipImages = spaceshipImages;
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

  public void setlimit(Constants.AttributeType powerupType, int value)
  {
    attributes.setLimit(powerupType, value);
  }

  public int getLimit(Constants.AttributeType powerupType)
  {
    return attributes.getValue(powerupType);
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
  
  public void applyPlayerControlsToDisplayRespectiveImages(boolean shield, boolean thrust)
  {
    drawShield = shield; 
    drawThrust = thrust;
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
  
  @Override
  public void draw(Graphics2D g)
  {
    // Draw the player ship
    super.draw(g);
    
    if (isAlive() && isVisible())
    {
      // If the player is pressing the shield button, display the shield around the player ship
      if (drawShield)
      {
        if (this.isEquipped(Constants.AttributeType.ATTRIBUTE_SHIELD))
        {
          g.drawImage(spaceshipImages[Constants.IMAGE_SPACESHIP_SHIELD_INDEX].getImage(), at, imageObserver);
        }
      }
      
      // If the player is applying thrust, draw the respective thrust image 
      if (drawThrust)
      {
        if (this.getValue(AttributeType.ATTRIBUTE_THRUST) == Constants.SHIP_INCREASED_ACCELERATION)
        {
          g.drawImage(spaceshipImages[Constants.IMAGE_SPACESHIP_THRUST2_INDEX].getImage(), at, imageObserver);
        }
        else
        {
          g.drawImage(spaceshipImages[Constants.IMAGE_SPACESHIP_THRUST1_INDEX].getImage(), at, imageObserver);
        }
      }
    }
  }
}
