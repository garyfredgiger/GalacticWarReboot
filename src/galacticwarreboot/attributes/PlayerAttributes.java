package galacticwarreboot.attributes;

import galacticwarreboot.Constants;
import galacticwarreboot.Constants.AttributeType;
import galacticwarreboot.interfaces.IAttribute;

import java.util.HashMap;

public class PlayerAttributes
{
  /*
   *  To add additional power-ups, the following must be done:
   *  
   *  1) The object implementing the interface IAttribute should be added to the array availableAttributes in the ith element.
   *  2) The corresponding type of the attribute needs to be added to the array attributeTypes in the ith element.
   *  3) The default value of the attribute needs to be added to the array attributeDefaultValues in the the element.
   *  
   *  Once everything is in place, the rest of the code will take care of initialization.
   */
  private IAttribute[]                         availableAttributes    = new IAttribute[] { new AttributeFirePower(), new AttributeHealth(), new AttributesShields(), new AttributeSuperShields(), new AttributeTheBomb(), new AttributeThrust() };
  private AttributeType[]                      attributeTypes         = new AttributeType[] { AttributeType.ATTRIBUTE_FIREPOWER, AttributeType.ATTRIBUTE_HEALTH, AttributeType.ATTRIBUTE_SHIELD, AttributeType.ATTRIBUTE_SUPER_SHIELD, AttributeType.ATTRIBUTE_THE_BOMB, AttributeType.ATTRIBUTE_THRUST };
  private Integer[]                            attributeDefaultValues = new Integer[] { Constants.SHIP_STARTING_FIREPOWER, Constants.SHIP_INITIAL_HEALTH, Constants.SHIP_INITIAL_SHIELD, Constants.SHIP_STARTING_SUPER_SHIELD, Constants.SHIP_STARTING_THE_BOMBS, Constants.SHIP_DEFAULT_ACCELERATION };
  HashMap<Constants.AttributeType, IAttribute> powerups               = new HashMap<Constants.AttributeType, IAttribute>();

  public PlayerAttributes()
  {
    // Check that all arrays have the same dimensions, if not then throw an exception
    if ((availableAttributes.length == attributeTypes.length) && (attributeTypes.length == attributeDefaultValues.length))
    {
      initialize();
    }
    else
    {
      throw new ArrayIndexOutOfBoundsException("Lengths of arrays 'availableAttributes', 'attributeTypes' and 'attributeDefaultValues' are not the same.");
    }
  }

  public void initialize()
  {
    for (int i = 0; i < availableAttributes.length; i++)
    {
      powerups.put(attributeTypes[i], availableAttributes[i]);
      powerups.get(attributeTypes[i]).setValue(attributeDefaultValues[i]);
    }
    
    powerups.get(AttributeType.ATTRIBUTE_HEALTH).setLimit(Constants.SHIP_INITIAL_HEALTH);
    powerups.get(AttributeType.ATTRIBUTE_SHIELD).setLimit(Constants.SHIP_INITIAL_SHIELD);
    
    displayAttributes();
  }

  public void setLimit(Constants.AttributeType powerupType, int value)
  {
    powerups.get(powerupType).setLimit(value);
  }

  public int getLimit(Constants.AttributeType powerupType)
  {
    return powerups.get(powerupType).getLimit();
  }
  
  public void setValue(Constants.AttributeType powerupType, int value)
  {
    powerups.get(powerupType).setValue(value);
  }

  public int getValue(Constants.AttributeType powerupType)
  {
    return powerups.get(powerupType).getValue();
  }

  public void incrementByAmount(Constants.AttributeType powerupType, int amount)
  {
    powerups.get(powerupType).incrementByAmount(amount);
  }

  public void decrementByAmount(Constants.AttributeType powerupType, int amount)
  {
    powerups.get(powerupType).decrementByAmount(amount);
  }

  public void toggleFlag(Constants.AttributeType powerupType)
  {
    powerups.get(powerupType).toggleFlag();
  }

  public boolean isEquipped(Constants.AttributeType powerupType)
  {
    return powerups.get(powerupType).isEquipped();
  }
  
  public void displayAttributes()
  {
    System.out.println("Player Attribute Values: ");
    for (int i = 0; i < availableAttributes.length; i++)
    {
      System.out.println(attributeTypes[i].toString() + ":\t" +  powerups.get(attributeTypes[i]).getValue());
    }
    System.out.println("Health Capacity:\t" +  getLimit(Constants.AttributeType.ATTRIBUTE_HEALTH));
    System.out.println("Shield Capacity:\t" +  getLimit(Constants.AttributeType.ATTRIBUTE_SHIELD));
    System.out.println("Player Attribute Values DONE");
  }
}
