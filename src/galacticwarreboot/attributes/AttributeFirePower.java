package galacticwarreboot.attributes;

import galacticwarreboot.Constants;
import galacticwarreboot.interfaces.IAttribute;

public class AttributeFirePower implements IAttribute
{
  private int firePower;

  public AttributeFirePower()
  {
    initialize();
  }

  @Override
  public void initialize()
  {
    setValue(Constants.SHIP_STARTING_FIREPOWER);
  }

  @Override
  public int getValue()
  {
    return firePower;
  }

  @Override
  public void setValue(int value)
  {
    if (value > Constants.SHIP_STARTING_FIREPOWER)
    {
      value = Constants.SHIP_STARTING_FIREPOWER;
    }

    if (value < 0)
    {
      value = 0;
    }

    firePower = value;
  }

  @Override
  public void incrementByAmount(int amount)
  {
    if (amount < 0)
    {
      return;
    }

    firePower += amount;

    if (firePower > Constants.SHIP_MAX_FIREPOWER)
    {
      firePower = Constants.SHIP_MAX_FIREPOWER;
    }
  }

  @Override
  public void decrementByAmount(int amount)
  {
    if (amount < 0)
    {
      return;
    }

    firePower--;

    if (firePower < Constants.SHIP_MIN_FIREPOWER)
    {
      firePower = Constants.SHIP_MIN_FIREPOWER;
    }
  }

  @Override
  public void toggleFlag()
  {
    // NOTE: Not used in for this attribute 
  }

  @Override
  public boolean isEquipped()
  {
    // NOTE: Not used in for this attribute
    return false;
  }

  @Override
  public void setLimit(int limit)
  {}

  @Override
  public int getLimit()
  {
    return 0;
  }
}
