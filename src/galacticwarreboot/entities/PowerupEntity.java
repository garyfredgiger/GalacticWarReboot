package galacticwarreboot.entities;

import java.awt.image.ImageObserver;

import galacticwarreboot.Constants;
import galacticwarreboot.Constants.AttributeType;
import galacticwarreboot.Constants.PowerUpType;
import game.framework.entities.EntityImage;
import game.framework.utilities.GameEngineConstants.EntityTypes;

public class PowerupEntity extends EntityImage
{
  private int value;
  private Constants.PowerUpType powerupType;
  private Constants.AttributeType correspondingAttributeMapping;
  
  // TODO: In addition to the type, this needs to have a value as well
  
  public PowerupEntity(ImageObserver imageObserver)
  {
    super(imageObserver, EntityTypes.POWER_UP);

    //powerupType = Constants.PowerUpType.UNDEFINED;
    correspondingAttributeMapping = Constants.AttributeType.UNDEFINED;
  }

  public Constants.AttributeType getAttributeType()
  {
    return correspondingAttributeMapping;
  }
  
  public Constants.PowerUpType getPowerupType()
  {
    return powerupType;
  }
  
  public int getValue()
  {
    return this.value;
  }
  
  protected void setAttributeType(Constants.AttributeType type)
  {
    correspondingAttributeMapping = type;
  }
  
  protected void setPowerupType(Constants.PowerUpType type)
  {
    powerupType = type;
  }
  
  protected void setValue(int value)
  {
    this.value = value;
  }
}
