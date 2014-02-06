package galacticwarreboot.attributes;

import galacticwarreboot.Constants;

public class AttributesShields extends AttributeBase
{
  private int currentShield;
  private int shieldCapacity;

  public AttributesShields()
  {
    this(Constants.SHIP_INITIAL_SHIELD, Constants.SHIP_INITIAL_SHIELD);
  }

  public AttributesShields(int initialShieldValue, int initialShieldCapacity)
  {
    // NOTE: limit needs to be set before value
    setLimit(initialShieldCapacity);
    setValue(initialShieldValue);
  }

  @Override
  public int getValue()
  {
    return currentShield;
  }

  @Override
  public void setValue(int value)
  {
    if (value > shieldCapacity)
    {
      value = shieldCapacity;
    }
    
    if (value < 0)
    {
      value = 0;
    }
    
    currentShield = value;
  }

  @Override
  public void incrementByAmount(int amount)
  {
    if (amount < 0)
    {
      return;
    }
    
    currentShield += amount;
    
    if (currentShield > shieldCapacity)
    {
      currentShield = shieldCapacity;
    }
  }

  @Override
  public void decrementByAmount(int amount)
  {
    if (amount < 0)
    {
      return;
    }

    currentShield -= amount;
    
    if (currentShield < 0)
    {
      currentShield = 0;
    }
  }
  
  @Override
  public void toggleFlag()
  {
    // Not used for this powerup
  }

  @Override
  public boolean isEquipped()
  {
    return (currentShield > 0 ? true : false);
  }

  @Override
  public void setLimit(int limit)
  {
    if (limit < 0)
    {
      shieldCapacity = 0;
    }
  
    shieldCapacity = limit;
  }

  @Override
  public int getLimit()
  {
    return shieldCapacity;
  }
}
