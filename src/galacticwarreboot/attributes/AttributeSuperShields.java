package galacticwarreboot.attributes;

import galacticwarreboot.Constants;
import galacticwarreboot.interfaces.IAttribute;

public class AttributeSuperShields implements IAttribute
{
  private int superShield;

  public AttributeSuperShields()
  {
    initialize();
  }

  @Override
  public void initialize()
  {
    setValue(Constants.SHIP_STARTING_SUPER_SHIELD);
  }

  @Override
  public int getValue()
  {
    return superShield;
  }

  /*
   * Sets the value for the super shields.
   * Note that there is currently no upper limit for the super shields 
   */
  @Override
  public void setValue(int value)
  {
    if (value < 0)
    {
      value = 0;
    }

    superShield = value;
  }

  @Override
  public void incrementByAmount(int amount)
  {
    // If there is a negative value, simply return.
    if (amount < 0)
    {
      return;
    }

    superShield += amount;
  }

  @Override
  public void decrementByAmount(int amount)
  {
    // If there is a negative value, simply return.
    if (amount < 0)
    {
      return;
    }

    superShield -= amount;
  }

  @Override
  public void toggleFlag()
  {
    // NOTE: Not being used for this power-up
  }

  @Override
  public boolean isEquipped()
  {
    return (superShield > 0 ? true : false);
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
