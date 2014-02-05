package galacticwarreboot.attributes;

import galacticwarreboot.Constants;

public class AttributesShields extends AttributeBase
{
  private int shield;

  public AttributesShields()
  {
    setValue(0);
  }

  public AttributesShields(int level)
  {
    setValue(level);
  }
  
//  @Override
//  public void add(PowerupBase powerup)
//  {
//    
//  }

  @Override
  public int getValue()
  {
    return shield;
  }

  @Override
  public void setValue(int value)
  {
    if (value > Constants.SHIP_STARTING_SHIELD)
    {
      value = Constants.SHIP_STARTING_SHIELD;
    }
    
    if (value < 0)
    {
      value = 0;
    }
    
    shield = value;
  }

  @Override
  public void incrementByAmount(int amount)
  {
    if (amount < 0)
    {
      return;
    }
    
    shield += amount;
    
    if (shield > Constants.SHIP_STARTING_SHIELD)
    {
      shield = Constants.SHIP_STARTING_SHIELD;
    }
  }

  @Override
  public void decrementByAmount(int amount)
  {
    if (amount < 0)
    {
      return;
    }

    shield -= amount;
    
    if (shield < 0)
    {
      shield = 0;
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
    return (shield > 0 ? true : false);
  }

  @Override
  public void setLimit(int limit)
  {
    // TODO Auto-generated method stub
    
  }

  @Override
  public int getLimit()
  {
    // TODO Auto-generated method stub
    return 0;
  }
}
