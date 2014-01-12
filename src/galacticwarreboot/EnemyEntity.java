package galacticwarreboot;

import java.awt.image.ImageObserver;

import game.framework.entities.EntityImage;
import game.framework.utilities.GameEngineConstants;

public class EnemyEntity extends EntityImage
{
  private int pointValue;
  private Constants.EnemyTypes enemyType;

  public EnemyEntity(ImageObserver observer)
  {
    super(observer, GameEngineConstants.EntityTypes.ENEMY);
  }

  public void doLogic()
  {}

  public void setPointValue(int value)
  {
    pointValue = value;
  }

  public int getPointValue()
  {
    return pointValue;
  }

  public void setEnemyType(Constants.EnemyTypes type)
  {
    enemyType = type;
  }

  public Constants.EnemyTypes getEnemyType()
  {
    return enemyType;
  }
}
