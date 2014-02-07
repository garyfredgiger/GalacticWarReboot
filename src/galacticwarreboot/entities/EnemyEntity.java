package galacticwarreboot.entities;

import java.awt.image.ImageObserver;

import galacticwarreboot.Constants;
import galacticwarreboot.Constants.EnemyTypes;
import game.framework.entities.EntityImage;
import game.framework.utilities.GameEngineConstants.EntityTypes;

public class EnemyEntity extends EntityImage
{
  private int pointValue;
  private EnemyTypes enemyType;

  public EnemyEntity(ImageObserver observer)
  {
    super(observer, EntityTypes.ENEMY);
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

  public void setEnemyType(EnemyTypes type)
  {
    enemyType = type;
  }

  public Constants.EnemyTypes getEnemyType()
  {
    return enemyType;
  }
}
