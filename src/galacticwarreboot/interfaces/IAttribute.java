package galacticwarreboot.interfaces;

public interface IAttribute
{
  //public void add(PowerupBase powerup);
  
  public int getValue();

  // TODO: Think about adding another method setLimit(int upperlimit), which can be used to set attribute max values such as health and shields for instance.
  
  public void setValue(int value);

  public void incrementByAmount(int amount);
  
  public void decrementByAmount(int amount);

  public void toggleFlag();

  public boolean isEquipped();
}
