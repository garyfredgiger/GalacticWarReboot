package galacticwarreboot.interfaces;

public interface IAttribute
{  
  // TODO: Possible need an initialize method that can be called to reset the attribute
  
  public int getValue();
  
  public void setValue(int value);

  // Sets the limit of the attribute (e.g. health or shield capacity) 
  public void setLimit(int limit);
  
  // Gets the limit of the attribute (e.g. health or shield capacity)
  public int getLimit();

  // Increment the attribute by a certain amount
  public void incrementByAmount(int amount);

  // Decrement the attribute by a certain amount
  public void decrementByAmount(int amount);

  public void toggleFlag();

  public boolean isEquipped();
}
