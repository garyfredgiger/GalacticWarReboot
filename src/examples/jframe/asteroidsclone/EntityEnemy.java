package examples.jframe.asteroidsclone;

import java.awt.image.ImageObserver;

import game.framework.entities.EntityImage;
import game.framework.utilities.GameEngineConstants;

public class EntityEnemy extends EntityImage
{
  private int pointValue;
  private int enemyType;

  public EntityEnemy(ImageObserver observer)
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

  public void setEnemyType(int type)
  {
    enemyType = type;
  }

  public int getEnemyType()
  {
    return enemyType;
  }
}
