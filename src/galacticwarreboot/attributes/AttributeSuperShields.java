package galacticwarreboot.attributes;

public class AttributeSuperShields extends AttributeBase
{
  private int superShield;

  public AttributeSuperShields()
  {
    setValue(0);
  }

  public AttributeSuperShields(int level)
  {
    setValue(level);
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
}
