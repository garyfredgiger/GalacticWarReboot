package galacticwarreboot.attributes;

import galacticwarreboot.Constants;

public class AttributeFirePower extends AttributeBase
{
  private int firePower;

  public AttributeFirePower()
  {
    setValue(0);
  }

  public AttributeFirePower(int level)
  {
    setValue(level);
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
  { 
  }

  @Override
  public int getLimit()
  {
    return 0;
  }
}
