package galacticwarreboot.entities;

import galacticwarreboot.ScoreManager;

import java.awt.image.ImageObserver;

public class AsteroidEntity extends EnemyEntity
{
  // TODO: Have the enemy type, but also have the asteroid type

  public AsteroidEntity(ImageObserver observer)
  {
    super(observer);
  }

  @Override
  public void kill()
  {
    ScoreManager.incrementScore(this.getPointValue());
    super.kill();
  }
}
