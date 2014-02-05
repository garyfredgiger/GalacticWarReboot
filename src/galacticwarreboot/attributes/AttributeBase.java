package galacticwarreboot.attributes;

import galacticwarreboot.interfaces.IAttribute;

public abstract class AttributeBase implements IAttribute
{
//  @Override
//  public abstract void add(PowerupBase powerup);
  
  @Override
  public abstract int getValue();

  @Override
  public abstract void setValue(int value);
  
  @Override
  public abstract void incrementByAmount(int amount);
  
  @Override
  public abstract void decrementByAmount(int amount);
  
  @Override
  public abstract void toggleFlag();
  
  @Override
  public abstract boolean isEquipped();
  
  @Override
  public abstract void setLimit(int limit);
  
  @Override
  public abstract int getLimit();
}
