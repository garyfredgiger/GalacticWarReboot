package galacticwarreboot.attributes;

import galacticwarreboot.Constants;
import galacticwarreboot.interfaces.IAttribute;

public class AttributeThrust implements IAttribute
{
  private int thrust;

  public AttributeThrust()
  {
    initialize();
  }

  @Override
  public void initialize()
  {
    setValue(Constants.SHIP_DEFAULT_ACCELERATION);
  }

  @Override
  public int getValue()
  {
    return thrust;
  }

  @Override
  public void setValue(int value)
  {
    switch (value)
    {
      case Constants.SHIP_INCREASED_ACCELERATION_2:
        thrust = Constants.SHIP_INCREASED_ACCELERATION_2;
        break;

      case Constants.SHIP_INCREASED_ACCELERATION_3:
        thrust = Constants.SHIP_INCREASED_ACCELERATION_3;
        break;

      default:
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
    // If the player is equipped with the best thruster, return true...otherwise, return false. 
    return (thrust == Constants.SHIP_INCREASED_ACCELERATION_3 ? true : false);
  }

  @Override
  public void setLimit(int limit)
  {}

  @Override
  public int getLimit()
  {
    return 0;
  }

}
