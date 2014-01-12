package galacticwarreboot;

import game.framework.utilities.GameUtility;

public class UFOEntityManager
{
  /*
   * Class instance variables
   */
  private boolean             ufoInFlight;
  private long                lastUFOKilledTime;
  private long                minTimeBetweenUFOLaunches;
  private double              currentProbabilityTolaunchANewUFO;
  private int              randomProbability;

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
  public boolean ufoShouldBeLaunched(int level)
  {
    // Check that there is no current UFO in flight. If there is, simply exit this method
    if (ufoInFlight)
    {
      return false;
    }

    // Check if the minimum time has elapsed for launching a UFO.
    if (System.currentTimeMillis() < (lastUFOKilledTime + minTimeBetweenUFOLaunches))
    {
      return false;
    }

    if (level < Constants.UFO_MIN_UFO_LAUNCH_LEVEL)
    {
      return false;
    }

    // If there is no current UFO in flight, determine if a new UFO should be launched.
    randomProbability = GameUtility.random.nextInt(Constants.UFO_TOTAL_EVENTS_TO_SPAWN);
    if (randomProbability < currentProbabilityTolaunchANewUFO)
    {
      // Set the flag that indicates a new UFO is in flight
      ufoInFlight = true;
      //lastUFOKilledTime = System.currentTimeMillis();
      return true;
    }

    return false;
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
