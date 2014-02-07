package galacticwarreboot.attributes;

import galacticwarreboot.Constants;
import galacticwarreboot.interfaces.IAttribute;

public class AttributeTheBomb implements IAttribute
{
  private int theBomb;
  
  public AttributeTheBomb()
  {
    initialize();
  }

  @Override
  public void initialize()
  {
    setValue(Constants.SHIP_STARTING_THE_BOMBS);
  }

  @Override
  public int getValue()
  {
    return theBomb;
  }

  @Override
  public void setValue(int value)
  {    
    if (value < 0)
    {
      value = 0;
    }
    
    theBomb = value;
  }

  @Override
  public void incrementByAmount(int amount)
  {
    if (amount < 0)
    {
      return;
    }
    
    // NOTE: there is no upper limit on the number of bombs that the player can hold
    theBomb++;
  }

  @Override
  public void decrementByAmount(int amount)
  {
    if (amount < 0)
    {
      return;
    }

    theBomb--;
    
    if (theBomb < 0)
    {
      //theBomb = Constants.SHIP_STARTING_THE_BOMBS;
      theBomb = 0;
    }
  }

  @Override
  public void toggleFlag()
  {
    // TODO Auto-generated method stub
  }

  @Override
  public boolean isEquipped()
  {
    return ((theBomb > 0) ? true : false);
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
