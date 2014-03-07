package galacticwarreboot.attributes;

import galacticwarreboot.Constants;
import galacticwarreboot.interfaces.IAttribute;

public class AttributeHealth implements IAttribute
{
  private int currentHealth;
  private int healthCapacity;

  public AttributeHealth()
  {
    initialize();
  }

  @Override
  public void initialize()
  {
    setLimit(Constants.SHIP_INITIAL_HEALTH);
    setValue(Constants.SHIP_INITIAL_HEALTH);
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
