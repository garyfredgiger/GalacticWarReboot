package galacticwarreboot.attributes;

import galacticwarreboot.Constants;

public class AttributeHealth extends AttributeBase
{
  private int currentHealth;
  private int healthCapacity;
  
  public AttributeHealth()
  {
    this(Constants.SHIP_INITIAL_HEALTH, Constants.SHIP_INITIAL_HEALTH);
  }

  public AttributeHealth(int initialHealthValue, int initialHealthCapacity)
  {
    // NOTE: limit needs to be set before value
    setLimit(initialHealthCapacity);
    setValue(initialHealthValue);
  }
  
  @Override
  public int getValue()
  {
    return currentHealth;
  }

  @Override
  public void setValue(int value)
  {
    if (value > healthCapacity)
    {
      value = healthCapacity;
    }
    
    if (value < 0)
    {
      value = 0;
    }
    
    currentHealth = value;
  }

  @Override
  public void incrementByAmount(int amount)
  {
    if (amount < 0)
    {
      return;
    }
    
    currentHealth += amount;
    
    if (currentHealth > healthCapacity)
    {
      currentHealth = healthCapacity;
    }
  }

  @Override
  public void decrementByAmount(int amount)
  {
    if (amount < 0)
    {
      return;
    }
    
    currentHealth -= amount;
    
    if (currentHealth < 0)
    {
      currentHealth = 0;
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
    return (currentHealth > 0 ? true : false);
  }

  @Override
  public void setLimit(int limit)
  {
    if (limit < 0)
    {
      healthCapacity = 0;
    }
    
    healthCapacity = limit;
  }

  @Override
  public int getLimit()
  {
    return healthCapacity;
  }
}
