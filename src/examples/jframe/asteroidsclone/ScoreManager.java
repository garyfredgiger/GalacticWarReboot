package examples.jframe.asteroidsclone;

public class ScoreManager
{
  private static int currentScore;

  public static void reset()
  {
    currentScore = 0;
  }

  public static void incrementScore(int value)
  {
    currentScore += value;    
  }

  public static int getScore()
  {
    return currentScore;
  }
}
