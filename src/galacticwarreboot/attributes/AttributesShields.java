package galacticwarreboot.attributes;

import galacticwarreboot.Constants;
import galacticwarreboot.interfaces.IAttribute;

public class AttributesShields implements IAttribute
{
  private int currentShield;
  private int shieldCapacity;

  public AttributesShields()
  {
    initialize();
  }

  @Override
  public void initialize()
  {
    setLimit(Constants.SHIP_INITIAL_SHIELD);
    setValue(Constants.SHIP_INITIAL_SHIELD);
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
