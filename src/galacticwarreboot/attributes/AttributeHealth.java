package galacticwarreboot.attributes;

import galacticwarreboot.Constants;

public class AttributeHealth extends AttributeBase
{
  private int health;
  
  public AttributeHealth()
  {
    setValue(0);
  }

  public AttributeHealth(int level)
  {
    setValue(level);
  }
  
  @Override
  public int getValue()
  {
    return health;
  }

  @Override
  public void setValue(int value)
  {
    if (value > Constants.SHIP_STARTING_HEALTH)
    {
      value = Constants.SHIP_STARTING_HEALTH;
    }
    
    if (value < 0)
    {
      value = 0;
    }
    
    health = value;
  }

  @Override
  public void incrementByAmount(int amount)
  {
    if (amount < 0)
    {
      return;
    }
    
    health += amount;
    
    if (health > Constants.SHIP_STARTING_HEALTH)
    {
      health = Constants.SHIP_STARTING_HEALTH;
    }
  }

  @Override
  public void decrementByAmount(int amount)
  {
    if (amount < 0)
    {
      return;
    }
    
    health -= amount;
    
    if (health < 0)
    {
      health = 0;
    }
  }

  @Override
  public void toggleFlag()
  {
    // NOTE: This is not used for this powerup
  }

  @Override
  public boolean isEquipped()
  {
    return (health > 0 ? true : false);
  }
}
