package galacticwarreboot.attributes;

import galacticwarreboot.Constants;

public class AttributeHealth extends AttributeBase
{
  private int currentHealth;
  private int healthCapacity;
  
  // TODO: May need to add another method to define the limit of the attribute
  
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
    //if (value > Constants.SHIP_STARTING_HEALTH)
    if (value > healthCapacity)
    {
      //value = Constants.SHIP_STARTING_HEALTH;
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
    
    //if (currentHealth > Constants.SHIP_STARTING_HEALTH)
    if (currentHealth > healthCapacity)
    {
      //currentHealth = Constants.SHIP_STARTING_HEALTH;
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
    System.out.println("Limit is: " + limit);
    if (limit < 0)
    {
      healthCapacity = 0;
    }
    
    healthCapacity = limit;
    System.out.println("healthCapacity is now: " + healthCapacity);
  }

  @Override
  public int getLimit()
  {
    return healthCapacity;
  }
}
