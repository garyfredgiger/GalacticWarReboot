package galacticwarreboot.attributes;

import galacticwarreboot.Constants;

public class AttributeHealth extends AttributeBase
{
  private int currentHealth;
  private int healthLimit;
  
  // TODO: May need to add another method to define the limit of the attribute
  
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
    return currentHealth;
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
    
    if (currentHealth > Constants.SHIP_STARTING_HEALTH)
    {
      currentHealth = Constants.SHIP_STARTING_HEALTH;
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
}
