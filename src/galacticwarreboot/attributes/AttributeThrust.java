package galacticwarreboot.attributes;

import galacticwarreboot.Constants;
import galacticwarreboot.interfaces.IAttribute;

public class AttributeThrust implements IAttribute
{
  private int thrust;
  
  public AttributeThrust()
  {
    setValue(0);
  }

  public AttributeThrust(int level)
  {
    //System.out.println("AttributeThrust Constructor called with a value of " + level +" added to Ship");
    setValue(level);
  }
  
  @Override
  public int getValue()
  {
    return thrust;
  }

  @Override
  public void setValue(int value)
  {
    //System.out.println("setValue Called with value of Increased Thrust of " + value + "");

    switch(value)
    {
      case Constants.SHIP_INCREASED_ACCELERATION:
        //System.out.println("Increased Thrust of " + Constants.SHIP_INCREASED_ACCELERATION +" added to Ship");
        thrust = Constants.SHIP_INCREASED_ACCELERATION;
        break;
        
      default:
        //System.out.println("Increased Thrust of " + Constants.SHIP_DEFAULT_ACCELERATION +" added to Ship");
        thrust = Constants.SHIP_DEFAULT_ACCELERATION;
    }
  }

  @Override
  public void incrementByAmount(int amount)
  {
    // NOTE: This method will not be used for this attribute
  }

  @Override
  public void decrementByAmount(int amount)
  {
    // NOTE: This method will not be used for this attribute
  }

  @Override
  public void toggleFlag()
  {
    // NOTE: This method will not be used for this attribute
  }

  @Override
  public boolean isEquipped()
  {
    // NOTE: The ship always has a thruster. The issue is what is the power of the thruster
    return true;
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
