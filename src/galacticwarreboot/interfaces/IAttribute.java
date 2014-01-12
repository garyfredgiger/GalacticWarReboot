package galacticwarreboot.interfaces;

public interface IAttribute
{
  //public void add(PowerupBase powerup);
  
  public int getValue();

  public void setValue(int value);

  public void incrementByAmount(int amount);
  
  public void decrementByAmount(int amount);

  public void toggleFlag();

  public boolean isEquipped();
}
