package galacticwarreboot.entities;

import java.awt.Graphics2D;
import java.awt.image.ImageObserver;

import galacticwarreboot.Constants;
import galacticwarreboot.ImageManager;
import galacticwarreboot.Constants.AttributeType;
import galacticwarreboot.attributes.PlayerAttributes;
import game.framework.entities.EntityImage;
import game.framework.primitives.Position2D;
import game.framework.utilities.GameEngineConstants;

public class PlayerEntity extends EntityImage
{
  private PlayerAttributes attributes;
  private Position2D       homePosition;

  // Variables that indicate whether to draw the shields and thrust given the current user key strokes.
  private boolean          drawShield;
  private boolean          drawThrust;

  // TODO: Add the image array to the 
  public PlayerEntity(ImageObserver imageObserver)
  {
    super(imageObserver, GameEngineConstants.EntityTypes.PLAYER);
    homePosition = new Position2D();

    attributes = new PlayerAttributes();
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

  public void setLimit(Constants.AttributeType attributeType, int value)
  {
    attributes.setLimit(attributeType, value);
    //attributes.displayAttributes();
  }

  public int getLimit(Constants.AttributeType attributeType)
  {
    return attributes.getLimit(attributeType);
  }

  public void setValue(Constants.AttributeType powerupType, int value)
  {
    attributes.setValue(powerupType, value);
    //attributes.displayAttributes();
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

  public int getShieldCapacity()
  {
    return getLimit(Constants.AttributeType.ATTRIBUTE_SHIELD);
  }

  public int getHealthCapacity()
  {
    return getLimit(Constants.AttributeType.ATTRIBUTE_HEALTH);
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
          //g.drawImage(spaceshipImages[Constants.IMAGE_SPACESHIP_SHIELD_INDEX].getImage(), at, imageObserver);
          g.drawImage(ImageManager.getImage(Constants.FILENAME_SPACESHIP_SHIELD), at, imageObserver);
        }
      }

      // If the player is applying thrust, draw the respective thrust image 
      if (drawThrust)
      {
        switch (this.getValue(AttributeType.ATTRIBUTE_THRUST))
        {
          case Constants.SHIP_INCREASED_ACCELERATION_2:
            //g.drawImage(spaceshipImages[Constants.IMAGE_SPACESHIP_THRUST2_INDEX].getImage(), at, imageObserver);
            g.drawImage(ImageManager.getImage(Constants.FILENAME_SPACESHIP_THRUST2), at, imageObserver);
            break;

          case Constants.SHIP_INCREASED_ACCELERATION_3:
            //g.drawImage(spaceshipImages[Constants.IMAGE_SPACESHIP_THRUST3_INDEX].getImage(), at, imageObserver);
            g.drawImage(ImageManager.getImage(Constants.FILENAME_SPACESHIP_THRUST3), at, imageObserver);
            break;

          default:
            //g.drawImage(spaceshipImages[Constants.IMAGE_SPACESHIP_THRUST1_INDEX].getImage(), at, imageObserver);
            g.drawImage(ImageManager.getImage(Constants.FILENAME_SPACESHIP_THRUST1), at, imageObserver);
        }
      }
    }
  }
}
