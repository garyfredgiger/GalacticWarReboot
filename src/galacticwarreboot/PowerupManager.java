package galacticwarreboot;

import java.awt.Image;
import java.awt.image.ImageObserver;

import galacticwarreboot.Constants.AttributeType;
import galacticwarreboot.entities.PlayerEntity;
import galacticwarreboot.entities.PowerupEntity;
import galacticwarreboot.powerups.Powerup1000Points;
import galacticwarreboot.powerups.Powerup250Points;
import galacticwarreboot.powerups.Powerup500Points;
import galacticwarreboot.powerups.PowerupFirePower;
import galacticwarreboot.powerups.PowerupFullHealth;
import galacticwarreboot.powerups.PowerupFullShield;
import galacticwarreboot.powerups.PowerupHealth;
import galacticwarreboot.powerups.PowerupIncreasedHealthCapacity;
import galacticwarreboot.powerups.PowerupIncreasedShieldCapacity;
import galacticwarreboot.powerups.PowerupShield;
import galacticwarreboot.powerups.PowerupSuperShield;
import galacticwarreboot.powerups.PowerupTheBomb;
import galacticwarreboot.powerups.PowerupThrust;
import game.framework.primitives.Position2D;
import game.framework.utilities.GameEngineConstants;
import game.framework.utilities.GameUtility;

public class PowerupManager
{
  private int           whichProbability;
  private ImageObserver imageObserver;

  public PowerupManager(ImageObserver io)
  {
    imageObserver = io;
    whichProbability = Constants.POWERUP_SPAWN_PROBABILITY_FOR_LOWER_LEVELS;
  }

  public void updateSpawnProbability(int currentLevel)
  {
    whichProbability = ((currentLevel >= Constants.LEVEL_TO_USE_SPAWN_PROBABILITY_FOR_HIGHER_LEVELS) ? Constants.POWERUP_SPAWN_PROBABILITY_FOR_HIGHER_LEVELS : Constants.POWERUP_SPAWN_PROBABILITY_FOR_LOWER_LEVELS);
  }
  
  public int getProbability()
  {
    return whichProbability;
  }
  
  // Create a random powerup at the supplied sprite location
  //private void spawnPowerup(double xPos, double yPos)
  public PowerupEntity spawnPowerup(Position2D position, PlayerEntity player, int currentLevel)
  {
    // Only a few tiny sprites spit out a power-up
    int n = GameUtility.random.nextInt(Constants.POWERUP_TOTAL_EVENTS_TO_SPAWN);

    if (n > whichProbability)
    {
      return null;
    }

    PowerupEntity powerup = null;
    Constants.PowerUpType randomPowerupType = Constants.PowerUpType.randomPowerupType();

    switch (randomPowerupType)
    {
      // The cases in this switch statement will be the special cases where certian powerups are generate if certain conditions are true      

      case POWERUP_FULL_HEALTH:

        // Only spawn a health or full health if the player has less than full health.
        // TODO: Consider only spawning full health if the player has less than half health.
        if (player.getValue(Constants.AttributeType.ATTRIBUTE_HEALTH) < player.getLimit(Constants.AttributeType.ATTRIBUTE_HEALTH))
        {
          // Since the player current health is less than the max, generate a full health power-up
          powerup = createPowerup(randomPowerupType, null, position, -1);
          break;
        }

        // Otherwise, spawn a different power-up
        powerup = createPowerup(Constants.PowerUpType.POWERUP_250, null, position, -1);
        break;

      case POWERUP_FULL_SHIELD:

        // Only spawn full health if the player has less than full health.
        // TODO: Consider only spawning full health if the player has less than half health.
        if (player.getValue(Constants.AttributeType.ATTRIBUTE_SHIELD) < player.getLimit(Constants.AttributeType.ATTRIBUTE_SHIELD))
        {
          // Since the player current health is less than the max, generate a full health power-up
          powerup = createPowerup(randomPowerupType, null, position, -1);
          break;
        }

        // Otherwise, spawn a different power-up
        powerup = createPowerup(Constants.PowerUpType.POWERUP_500, null, position, -1);
        break;

      case POWERUP_THRUST:

        // If the player is not equipped with the best thruster so far, check if the player is eligible for a thruster power-up increase 
        if (!player.isEquipped(AttributeType.ATTRIBUTE_THRUST))
        {
          // Only spawn thrust increase power-up if the player does not already have increased thrust.
          if (player.getValue(AttributeType.ATTRIBUTE_THRUST) == Constants.SHIP_DEFAULT_ACCELERATION)
          {
            // If the level is available for the thruster 2 increase, set flags and vars to spawn thruster 2 powerup
            if (currentLevel >= Constants.POWERUP_THRUST_2_MIN_LEVEL)
            {
              powerup = createPowerup(Constants.PowerUpType.POWERUP_THRUST, ImageManager.getImage(Constants.FILENAME_POWERUP_ENGINE_2), position, Constants.SHIP_INCREASED_ACCELERATION_2);
              break;
            }
          }
          else if (player.getValue(AttributeType.ATTRIBUTE_THRUST) == Constants.SHIP_INCREASED_ACCELERATION_2)
          {
            // If the level is available for the thruster 2 increase, set flags and vars to spawn thruster 2 powerup
            if (currentLevel >= Constants.POWERUP_THRUST_3_MIN_LEVEL)
            {
              powerup = createPowerup(Constants.PowerUpType.POWERUP_THRUST, ImageManager.getImage(Constants.FILENAME_POWERUP_ENGINE_3), position, Constants.SHIP_INCREASED_ACCELERATION_3);
              break;
            }
          }
        }

        // Otherwise, spawn a different power-up
        powerup = createPowerup(Constants.PowerUpType.POWERUP_HEALTH, null, position, -1);
        break;

      case POWERUP_FIREPOWER:

        if (player.getValue(AttributeType.ATTRIBUTE_FIREPOWER) < Constants.SHIP_MAX_FIREPOWER)
        {
          powerup = createPowerup(randomPowerupType, null, position, -1);
          break;
        }

        // Otherwise, spawn a different power-up
        powerup = createPowerup(Constants.PowerUpType.POWERUP_SUPER_SHIELD, null, position, -1);
        break;

      case POWERUP_INCREASE_HEALTH_CAPACITY:

        switch (player.getHealthCapacity())
        {
        // If players current health capacity is the initial value, spawn the next level. 
          case Constants.SHIP_INITIAL_HEALTH:

            if (ScoreManager.getScore() > Constants.HEALTH_CAPACITY_INCREASE_TO_20_SCORE_LIMIT)
            {
              powerup = createPowerup(randomPowerupType, ImageManager.getImage(Constants.FILENAME_POWERUP_INCREASE_HEALTH_CAPACITY_20), position, Constants.SHIP_HEALTH_CAPACITY_INCREASE_TO_20);
              break;
            }

          case Constants.SHIP_HEALTH_CAPACITY_INCREASE_TO_20:

            if (ScoreManager.getScore() > Constants.HEALTH_CAPACITY_INCREASE_TO_40_SCORE_LIMIT)
            {
              powerup = createPowerup(randomPowerupType, ImageManager.getImage(Constants.FILENAME_POWERUP_INCREASE_HEALTH_CAPACITY_40), position, Constants.SHIP_HEALTH_CAPACITY_INCREASE_TO_40);
              break;
            }

          default:
            // Create another power-up since it is pointless to create an increase shield powerup.
            powerup = createPowerup(Constants.PowerUpType.POWERUP_SUPER_SHIELD, null, position, -1);
        }
        break;

      case POWERUP_INCREASE_SHIELD_CAPACITY:

        switch (player.getShieldCapacity())
        {
          // If players current shield capacity is the initial value, increase to the next level. 
          case Constants.SHIP_INITIAL_SHIELD:

            if (ScoreManager.getScore() > Constants.SHIELD_CAPACITY_INCREASE_TO_20_SCORE_LIMIT)
            {
              powerup = createPowerup(randomPowerupType, ImageManager.getImage(Constants.FILENAME_POWERUP_INCREASE_SHIELD_CAPACITY_20), position, Constants.SHIP_HEALTH_CAPACITY_INCREASE_TO_20);
              break;
            }

            // If players current shield capacity is the 2nd level value, increase to the next level.
          case Constants.SHIP_SHIELD_CAPACITY_INCREASE_TO_20:

            // Only increase it if the respective score level is reached 
            if (ScoreManager.getScore() > Constants.SHIELD_CAPACITY_INCREASE_TO_40_SCORE_LIMIT)
            {
              powerup = createPowerup(randomPowerupType, ImageManager.getImage(Constants.FILENAME_POWERUP_INCREASE_SHIELD_CAPACITY_40), position, Constants.SHIP_HEALTH_CAPACITY_INCREASE_TO_40);
              break;
            }

          default:
            // Create another power-up since it is pointless to create an increase shield powerup.
            powerup = createPowerup(Constants.PowerUpType.POWERUP_THE_BOMB, null, position, -1);
        }

        break;

      // These are common power-ups that have no special conditions that need to be in place before being spawned
      case POWERUP_SHIELD:
      case POWERUP_HEALTH:
      case POWERUP_SUPER_SHIELD:
      case POWERUP_THE_BOMB:
      case POWERUP_250:
      case POWERUP_500:
      case POWERUP_1000:
      default:

        powerup = createPowerup(randomPowerupType, null, position, -1);
    }

    return powerup;
  }

  private PowerupEntity createPowerup(Constants.PowerUpType type, Image image, Position2D position, int value)
  {
    PowerupEntity powerup = null;

    // Note that the image parameter is still used even though a number of power-ups do not need it since it is 
    //      obvious which image to use. Other power-ups will need to have an image specified if it can have 
    //      different values such as the increased health and shield power-ups.

    switch (type)
    {
      case POWERUP_SHIELD:
        powerup = new PowerupShield(imageObserver);
        powerup.setImage(ImageManager.getImage(Constants.FILENAME_POWERUP_SHIELD));
        break;

      case POWERUP_FIREPOWER:
        powerup = new PowerupFirePower(imageObserver);
        powerup.setImage(ImageManager.getImage(Constants.FILENAME_POWERUP_GUN));
        break;

      case POWERUP_SUPER_SHIELD:
        powerup = new PowerupSuperShield(imageObserver);
        powerup.setImage(ImageManager.getImage(Constants.FILENAME_POWERUP_SUPER_SHIELD));
        break;

      case POWERUP_FULL_HEALTH:
        powerup = new PowerupFullHealth(imageObserver);
        powerup.setImage(ImageManager.getImage(Constants.FILENAME_POWERUP_FULL_HEALTH));
        break;

      case POWERUP_FULL_SHIELD:
        powerup = new PowerupFullShield(imageObserver);
        powerup.setImage(ImageManager.getImage(Constants.FILENAME_POWERUP_SHIELD_FULL_RESTORE));
        break;

      case POWERUP_THE_BOMB:
        powerup = new PowerupTheBomb(imageObserver);
        powerup.setImage(ImageManager.getImage(Constants.FILENAME_POWERUP_THE_BOMB));
        break;

      case POWERUP_THRUST:
        powerup = new PowerupThrust(imageObserver);
        powerup.setValue(value);
        if (image != null)
        {
          powerup.setImage(image);
        }
        break;

      case POWERUP_INCREASE_HEALTH_CAPACITY:
        powerup = new PowerupIncreasedHealthCapacity(imageObserver);
        powerup.setValue(value);
        if (image != null)
        {
          powerup.setImage(image);
        }
        break;

      case POWERUP_INCREASE_SHIELD_CAPACITY:
        powerup = new PowerupIncreasedShieldCapacity(imageObserver);
        powerup.setValue(value);
        if (image != null)
        {
          powerup.setImage(image);
        }
        break;

      case POWERUP_250:
        powerup = new Powerup250Points(imageObserver);
        powerup.setImage(ImageManager.getImage(Constants.FILENAME_POWERUP_250));
        break;

      case POWERUP_500:
        powerup = new Powerup500Points(imageObserver);
        powerup.setImage(ImageManager.getImage(Constants.FILENAME_POWERUP_500));
        break;

      case POWERUP_1000:
        powerup = new Powerup1000Points(imageObserver);
        powerup.setImage(ImageManager.getImage(Constants.FILENAME_POWERUP_1000));
        break;

      // Spawning a health power-up is the default case
      default:

        powerup = new PowerupHealth(imageObserver);
        powerup.setImage(ImageManager.getImage(Constants.FILENAME_POWERUP_HEALTH));
    }

    // Make the power-up have the same position as the entity
    powerup.setPosition(position);

    // Set rotation and direction angles of asteroid
    int faceAngle = GameUtility.random.nextInt((int) GameEngineConstants.DEGREES_IN_A_CIRCLE);
    int moveAngle = GameUtility.random.nextInt((int) GameEngineConstants.DEGREES_IN_A_CIRCLE);
    powerup.setFaceAngle(faceAngle);
    powerup.setMoveAngle(moveAngle);
    powerup.setRotationRate(Constants.POWERUP_ROTAITON_RATE);

    // Set velocity based on movement direction
    double angle = powerup.getMoveAngle() - 90;
    powerup.setVelocity(GameUtility.calcAngleMoveX(angle), GameUtility.calcAngleMoveY(angle));
    powerup.getVelocity().scaleThisVector(Constants.POWERUP_SPEED);

    // Set the lifespan
    powerup.setLifespan((int) (GameEngineConstants.DEFAULT_UPDATE_RATE * Constants.POWERUP_LIFE_SPAN_IN_SECS));

    return powerup;
  }
}
