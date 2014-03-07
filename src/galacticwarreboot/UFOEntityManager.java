package galacticwarreboot;

import java.awt.image.ImageObserver;

import galacticwarreboot.entities.UFOEntity;
import galacticwarreboot.entities.UFOShorty;
import galacticwarreboot.entities.UFOStrongEntity;
import game.framework.entities.EntityImage;
import game.framework.primitives.Position2D;
import game.framework.utilities.GameEngineConstants;
import game.framework.utilities.GameUtility;

public class UFOEntityManager
{
  /*
   * Class instance variables
   */
  private boolean ufoInFlight;
  private long    lastUFOKilledTime;
  private long    minTimeBetweenUFOLaunches;
  private double  currentProbabilityTolaunchANewUFO;
  private int     randomProbability;

  public UFOEntityManager()
  {
    ufoInFlight = false;
    lastUFOKilledTime = System.currentTimeMillis();
    currentProbabilityTolaunchANewUFO = Constants.UFO_SPAWN_PROBABILITY;
    minTimeBetweenUFOLaunches = Constants.UFO_MIN_TIME_BETWEEN_LAUNCHES;
  }

  /*
   *  Allows the probability for generating a UFO to cross the screen to be changed during game play.
   *  This can be done as a function of game difficulty or level number (e.g., as higher levels are
   *  reached increase the probability of UFO occurrences).  
   */
  public void setNewProbabilityToLaunchUFO(int prob)
  {
    if ((prob <= 0) && (prob > Constants.UFO_TOTAL_EVENTS_TO_SPAWN))
    {
      return;
    }

    currentProbabilityTolaunchANewUFO = prob;
  }

  /*
   * 
   */
  public void setNewMinTimeBetweenUFOLaunches(long minTimeInMs)
  {
    if (minTimeInMs <= 0)
    {
      return;
    }

    minTimeBetweenUFOLaunches = minTimeInMs;
  }

  /*
   * Determine if a UFO should be launched
   */
  public UFOEntity ufoShouldBeLaunched(int level, ImageObserver imageObserver, int screenWidth, int screenHeight)
  {
    // Check that there is no current UFO in flight. If there is, simply exit this method
    if (ufoInFlight)
    {
      return null;
    }

    // Check if the minimum time has elapsed from the last time a UFO was launched to see if another UFO should be launched.
    if (System.currentTimeMillis() < (lastUFOKilledTime + minTimeBetweenUFOLaunches))
    {
      return null;
    }

    if (level < Constants.GAME_UFO_MIN_UFO_LAUNCH_LEVEL)
    {
      return null;
    }

    // If there is no current UFO in flight, the minimum level has been reached and the minimum 
    // time has elapsed since the last UFO was launched, check if a new UFO should be launched.
    randomProbability = GameUtility.random.nextInt(Constants.UFO_TOTAL_EVENTS_TO_SPAWN);
    if (randomProbability < currentProbabilityTolaunchANewUFO)
    {
      // Determine which type of UFO to launch and prepare it for launch
      Constants.EnemyTypes ufoTypeToLaunch = Constants.EnemyTypes.UFO;  // By default we are doing to launch the regular UFO

      // If the current level is between level to launch a super ufo and the shorty, see if a super ufo should be launched
      if ((level >= Constants.GAME_LAUNCH_SUPER_UFO_LEVEL) && (level < Constants.GAME_LAUNCH_SHORTY_UFO_LEVEL))
      {
        ufoTypeToLaunch = (GameUtility.random.nextBoolean() ? Constants.EnemyTypes.UFO_STRONG : Constants.EnemyTypes.UFO);
      }
      else if (level >= Constants.GAME_LAUNCH_SHORTY_UFO_LEVEL)
      {
        // Starting with level GAME_LAUNCH_SHORTY_UFO_LEVEL we only want to launch strong or shorty UFOs to make if difficult for the player
        ufoTypeToLaunch = (GameUtility.random.nextBoolean() ? Constants.EnemyTypes.UFO_STRONG : Constants.EnemyTypes.UFO_SHORTY);
      }

      // Set the flag that indicates a new UFO is in flight
      ufoInFlight = true;

      switch (ufoTypeToLaunch)
      {
        case UFO_STRONG:
          return new UFOStrongEntity(imageObserver, this, (int) (screenHeight * 0.10), (int) (screenHeight * 0.90), 0, screenWidth);

        case UFO_SHORTY:
          return new UFOShorty(imageObserver, this, (int) (screenHeight * 0.10), (int) (screenHeight * 0.90), 0, screenWidth);

        default:
          return new UFOEntity(imageObserver, this, (int) (screenHeight * 0.10), (int) (screenHeight * 0.90), 0, screenWidth);
      }
    }

    // The probability to launch a UFO was not satisfied, do nothing.
    return null;
  }

  // Fire a shot at the player ship
  public EntityImage stockUFOBullet(Position2D ufoPos, Position2D playerPos, Constants.EnemyTypes ufoType, ImageObserver imageObserver)
  {
    EntityImage bullet = new EntityImage(imageObserver, GameEngineConstants.EntityTypes.ENEMY_SHOT);

    switch (ufoType)
    {
      case UFO_SHORTY:
        // Compute bullet heading given current position of player and this ufo entity
        bullet.setVelocity(GameUtility.computeUnitVectorBetweenTwoPositions(ufoPos, playerPos).createScaledVector(Constants.UFO_SHORTY_SHOT_SPEED));
        bullet.setImage(ImageManager.getImage(Constants.FILENAME_UFO_SHORTY_SHOT));
        bullet.setLifespan((int) (GameEngineConstants.DEFAULT_UPDATE_RATE * Constants.UFO_SHORTY_BULLET_LIFE_SPAN_IN_SECS));
        break;

      default:
        // Compute bullet heading given current position of player and this ufo entity
        bullet.setVelocity(GameUtility.computeUnitVectorBetweenTwoPositions(ufoPos, playerPos).createScaledVector(Constants.UFO_SHOT_SPEED));
        bullet.setImage(ImageManager.getImage(Constants.FILENAME_UFO_SHOT));
        bullet.setLifespan((int) (GameEngineConstants.DEFAULT_UPDATE_RATE * Constants.UFO_BULLET_LIFE_SPAN_IN_SECS));
    }

    // Set the bullet's starting position
    double x = ufoPos.x - bullet.getWidth() / 2;
    double y = ufoPos.y - bullet.getHeight() / 2;
    bullet.setPosition(x, y);

    return bullet;
  }

  /*
   * This is used for debugging purposes 
   */
  public double getProbability()
  {
    return randomProbability;
  }

  /*
   * Once the UFO has finished its flight, this is called to clear the UFO flight flag
   */
  public void reset()
  {
    lastUFOKilledTime = System.currentTimeMillis();
    ufoInFlight = false;
  }
}
