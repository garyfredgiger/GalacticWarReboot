package galacticwarreboot.attributes;

import galacticwarreboot.Constants;
import galacticwarreboot.interfaces.IAttribute;

import java.util.HashMap;

public class PlayerAttributes
{
  // NOTE: To add a new attribute, simply add a declaration here
  AttributeFirePower                         attributeFirePower;
  AttributeHealth                            attributeHealth;
  AttributesShields                          attributeShields;
  AttributeSuperShields                      attributeSuperShields;
  AttributeTheBomb                           attributeTheBomb;

  HashMap<Constants.AttributeType, IAttribute> powerups = new HashMap<Constants.AttributeType, IAttribute>();

  public PlayerAttributes()
  {
    // Instantiate the class here
    attributeFirePower = new AttributeFirePower();
    attributeHealth = new AttributeHealth();
    attributeShields = new AttributesShields();
    attributeSuperShields = new AttributeSuperShields();
    attributeTheBomb = new AttributeTheBomb();

    // Add it to the hash map
    powerups.put(Constants.AttributeType.ATTRIBUTE_FIREPOWER, attributeFirePower);
    powerups.put(Constants.AttributeType.ATTRIBUTE_HEALTH, attributeHealth);
    powerups.put(Constants.AttributeType.ATTRIBUTE_SHIELD, attributeShields);
    powerups.put(Constants.AttributeType.ATTRIBUTE_SUPER_SHIELD, attributeSuperShields);
    powerups.put(Constants.AttributeType.ATTRIBUTE_THE_BOMB, attributeTheBomb);

    initialize();
  }

  public void initialize()
  {
    // Add a line here to initialize it
    powerups.get(Constants.AttributeType.ATTRIBUTE_FIREPOWER).setValue(Constants.SHIP_STARTING_FIREPOWER);
    powerups.get(Constants.AttributeType.ATTRIBUTE_HEALTH).setValue(Constants.SHIP_STARTING_HEALTH);
    powerups.get(Constants.AttributeType.ATTRIBUTE_SHIELD).setValue(Constants.SHIP_STARTING_SHIELD);
    powerups.get(Constants.AttributeType.ATTRIBUTE_SUPER_SHIELD).setValue(Constants.SHIP_STARTING_SUPER_SHIELD);
    powerups.get(Constants.AttributeType.ATTRIBUTE_THE_BOMB).setValue(Constants.SHIP_STARTING_THE_BOMBS);
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
}
