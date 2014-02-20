package galacticwarreboot;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.ImageObserver;
import java.util.Iterator;
import java.util.LinkedList;

import galacticwarreboot.Constants.AttributeType;
import galacticwarreboot.entities.AsteroidEntity;
import galacticwarreboot.entities.EnemyEntity;
import galacticwarreboot.entities.PlasmaShotEntity;
import galacticwarreboot.entities.PlayerEntity;
import galacticwarreboot.entities.PlayerShotEntity;
import galacticwarreboot.entities.PowerupEntity;
import galacticwarreboot.entities.SuperShieldEntity;
import galacticwarreboot.entities.UFOEntity;
import galacticwarreboot.entities.UFOShorty;
import galacticwarreboot.entities.UFOStrongEntity;
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
import game.framework.GameEngine;
import game.framework.entities.Entity2D;
import game.framework.entities.EntityImage;
import game.framework.entities.text.StaticText;
import game.framework.interfaces.IRender;
import game.framework.primitives.Position2D;
import game.framework.utilities.GameEngineConstants;
import game.framework.utilities.GameUtility;
import game.framework.utilities.input.GameInputMovement;

public class Asteroids extends GameEngine
{
  /*
   * Class member variables
   */

  // General constants
  private static final double DEGREES_IN_A_CIRCLE              = 360;

  // Constants used for enemy entities
  private static final int    NUMBER_OF_BIG_ASTEROID_IMAGES    = 5;
  private static final int    NUMBER_OF_MEDIUM_ASTEROID_IMAGES = 2;
  private static final int    NUMBER_OF_SMALL_ASTEROID_IMAGES  = 3;
  private static final int    NUMBER_OF_TINY_ASTEROID_IMAGES   = 4;

  private static final int    ASTEROID_DIMENSIONS              = 128;
  private static final int    ASTEROID_MIN_ROTATION_RATE       = 5;
  private static final int    ASTEROID_MAX_ROTATION_RATE       = 50;

  // TODO: Given different difficulty levels, it may be a good idea to have
  // separate velocity scales
  private static final int[]  BIG_ASTEROID_VELOCITY_SCALE      = new int[] { 10, 15, 25, 35, 50 };
  private static final int[]  MEDIUM_ASTEROID_VELOCITY_SCALE   = new int[] { 10, 25, 50, 60, 75, 90 };
  private static final int[]  SMALL_ASTEROID_VELOCITY_SCALE    = new int[] { 50, 75, 100, 110, 115, 125 };
  private static final int[]  TINY_ASTEROID_VELOCITY_SCALE     = new int[] { 50, 75, 125, 150, 175, 200 };

  /*
   * Class instance variables
   */

  // Variables used to control player entity
  EntityImage                 bulletImage;
  private GameInputMovement   playerMovement;
  private boolean             fireShot;
  private boolean             keyShield;
  private boolean             thrust;
  //private boolean             previousShieldState;
  private long                lastUFOCollisionTime;

  private boolean             requestSuperShield;
  private boolean             requestTheBomb;
  private boolean             prepareTheBomb;
  private boolean             executeTheBomb;

  private long                prepareBombTimer;
  private long                prepareBombTimerSecondCount;

  ImageObserver               imageObserver;

  // Variables used for enemy entities
  // TODO: Rather than having a bunch of image variables, possibly have an image store that stores the variables. It may not 
  //       reduce the amount of space, but it will definitely reduce the number of variables requried to manage the images.
  EntityImage[]               bigAsteroids;
  EntityImage[]               mediumAsteroids;
  EntityImage[]               smallAsteroids;
  EntityImage[]               tinyAsteroids;
  EntityImage[]               barImage                         = new EntityImage[2];
  EntityImage[]               barFrame                         = new EntityImage[3];
  EntityImage[]               shipImage                        = new EntityImage[5];

  EntityImage[]               powerupGun                       = new EntityImage[5];
  EntityImage                 powerupShield;
  EntityImage                 powerupHealth;
  EntityImage                 powerup250;
  EntityImage                 powerup500;
  EntityImage                 powerup1000;
  EntityImage                 powerupSuperShield;
  EntityImage                 powerupTheBomb;
  EntityImage                 powerupFullHealth;
  EntityImage                 powerupFullShield;
  //EntityImage                 powerupAutoShield;
  EntityImage[]               powerupThrust                    = new EntityImage[2];

  EntityImage                 powerupIncreaseHealthCapacity20;
  EntityImage                 powerupIncreaseHealthCapacity40;
  EntityImage                 powerupIncreaseShieldCapacity20;
  EntityImage                 powerupIncreaseShieldCapacity40;

  EntityImage[]               ufo                              = new EntityImage[4];
  EntityImage                 ufoShorty;
  EntityImage                 ufoShot;
  EntityImage                 ufoShortyShot;
  EntityImage                 superShield;
  EntityImage                 hudSuperShield;
  EntityImage                 hudTheBomb;

  // Variables used for game
  private int                 currentLevel;
  EntityImage                 background;

  //boolean launchStrongUFO = false;
  private int                 ufoTypeToLaunch;

  // DEBUG/CHEAT vars
  private boolean increaseGunRequest;
  private boolean decreaseGunRequest;
  
  // Other vars. Not sure how to classify them.
  boolean playCountdownTimerSound;
  
  /*
   *  Game state management variables
   */

  // Flags that keep track of the user requests for game state transitions
  private boolean             requestGameStart;

  // Variables used to keep track of game event state transitions
  //private boolean                      invokePlayerDeadState;

  // Timers used to control when state transitions are to occur 
  private long                timerPlayerDeadState             = 0;
  private long                timerGameStart                   = 0;
  private long                timerGameOver                    = 0;
  private long                timerNextLevel                   = 0;

  UFOEntityManager            ufoManager;

  /*
   * Timers used to manage player entity states
   */
  //private long                timerCollision                   = 0;

  /*
   * Bounding boxes used for messages displayed to the screen
   */
  private Rectangle2D         boundsIntroductionTitleMsg;
  private Rectangle2D         boundsIntroductionStateTitle;
  private Rectangle2D         boundsIntroductionPowerupsMsg;
  private Rectangle2D         boundsGameStartMsg;
  private Rectangle2D         boundsPlayerDeadMsg;
  private Rectangle2D         boundsGameOverMsg;
  private Rectangle2D         boundsGamePlayingHUDMsgs;
  private Rectangle2D         boundsNextLevelMsg;

  private StaticText          msgGameStartScreen;

  // Game Power-up Flags
  private boolean             firstIncreasedHealthPointMark;                                              // When user's score reaches above a certain score, he/she will get an increased health bonus from 10 to 20
  private boolean             secondIncreasedHealthPointMark;                                             // When user's score reaches above a certain score, he/she will get another increased health bonus from 20 to 40

  int                         whichProbability;

  /*
   * Sound variables
   */  
  SoundManager soundManager;
  
  public Asteroids(IRender renderer, ImageObserver imageObserver)
  {
    super(renderer);
    this.imageObserver = imageObserver;
  }

  public Asteroids(IRender renderer, ImageObserver imageObserver, int userDefinedScreenWidth, int userDefinedScreenHeight)
  {
    super(renderer, userDefinedScreenWidth, userDefinedScreenHeight);
    this.imageObserver = imageObserver;
  }

  /*
   * (non-Javadoc)
   * 
   * @see game.engine.jframe.GameJFrame#userGameInit()
   */

  @Override
  public void userGameInit()
  {
    soundManager = new SoundManager();
    
    // Debugging vars to be removed when the final game is released
    increaseGunRequest = false;
    decreaseGunRequest = false;    

    bigAsteroids = new EntityImage[NUMBER_OF_BIG_ASTEROID_IMAGES];
    mediumAsteroids = new EntityImage[NUMBER_OF_MEDIUM_ASTEROID_IMAGES];
    smallAsteroids = new EntityImage[NUMBER_OF_SMALL_ASTEROID_IMAGES];
    tinyAsteroids = new EntityImage[NUMBER_OF_TINY_ASTEROID_IMAGES];

    playerMovement = new GameInputMovement();

    // Load the image into their respective image containers for use in the game.
    bigAsteroids[0] = loadImage(Constants.FILENAME_BIG_ASTEROID_1);
    bigAsteroids[1] = loadImage(Constants.FILENAME_BIG_ASTEROID_2);
    bigAsteroids[2] = loadImage(Constants.FILENAME_BIG_ASTEROID_3);
    bigAsteroids[3] = loadImage(Constants.FILENAME_BIG_ASTEROID_4);
    bigAsteroids[4] = loadImage(Constants.FILENAME_BIG_ASTEROID_5);
    mediumAsteroids[0] = loadImage(Constants.FILENAME_MEDIUM_ASTEROID_1);
    mediumAsteroids[1] = loadImage(Constants.FILENAME_MEDIUM_ASTEROID_2);
    smallAsteroids[0] = loadImage(Constants.FILENAME_SMALL_ASTEROID_1);
    smallAsteroids[1] = loadImage(Constants.FILENAME_SMALL_ASTEROID_2);
    smallAsteroids[2] = loadImage(Constants.FILENAME_SMALL_ASTEROID_3);
    tinyAsteroids[0] = loadImage(Constants.FILENAME_TINY_ASTEROID_1);
    tinyAsteroids[1] = loadImage(Constants.FILENAME_TINY_ASTEROID_2);
    tinyAsteroids[2] = loadImage(Constants.FILENAME_TINY_ASTEROID_3);
    tinyAsteroids[3] = loadImage(Constants.FILENAME_TINY_ASTEROID_4);
    background = loadImage(Constants.FILENAME_BACKGROUND);

    barFrame[0] = loadImage(Constants.FILENAME_BAR_FRAME_10);
    barFrame[1] = loadImage(Constants.FILENAME_BAR_FRAME_20);
    barFrame[2] = loadImage(Constants.FILENAME_BAR_FRAME_40);

    barImage[0] = loadImage(Constants.FILENAME_BAR_HEALTH);
    barImage[1] = loadImage(Constants.FILENAME_BAR_SHIELD);

    powerupGun[0] = loadImage(Constants.FILENAME_POWERUP_GUN);
    powerupGun[1] = loadImage(Constants.FILENAME_POWERUP_GUN_2);
    powerupGun[2] = loadImage(Constants.FILENAME_POWERUP_GUN_3);
    powerupGun[3] = loadImage(Constants.FILENAME_POWERUP_GUN_4);
    powerupGun[4] = loadImage(Constants.FILENAME_POWERUP_GUN_5);

    powerupShield = loadImage(Constants.FILENAME_POWERUP_SHIELD);
    powerupHealth = loadImage(Constants.FILENAME_POWERUP_HEALTH);
    powerup250 = loadImage(Constants.FILENAME_POWERUP_250);
    powerup500 = loadImage(Constants.FILENAME_POWERUP_500);
    powerup1000 = loadImage(Constants.FILENAME_POWERUP_1000);
    powerupSuperShield = loadImage(Constants.FILENAME_POWERUP_SUPER_SHIELD);
    powerupThrust[0] = loadImage(Constants.FILENAME_POWERUP_ENGINE_2);
    powerupThrust[1] = loadImage(Constants.FILENAME_POWERUP_ENGINE_3);

    superShield = loadImage(Constants.FILENAME_SUPER_SHIELD);
    hudSuperShield = loadImage(Constants.FILENAME_HUD_SUPERSHIELD_ICON);
    powerupTheBomb = loadImage(Constants.FILENAME_POWERUP_THE_BOMB);
    hudTheBomb = loadImage(Constants.FILENAME_HUD_THE_BOMB_ICON);
    powerupFullHealth = loadImage(Constants.FILENAME_POWERUP_FULL_HEALTH);
    powerupFullShield = loadImage(Constants.FILENAME_POWERUP_SHIELD_FULL_RESTORE);
    //powerupAutoShield = loadImage(Constants.FILENAME_POWERUP_AUTO_SHIELD);

    powerupIncreaseHealthCapacity20 = loadImage(Constants.FILENAME_POWERUP_INCREASE_HEALTH_CAPACITY_20);
    powerupIncreaseHealthCapacity40 = loadImage(Constants.FILENAME_POWERUP_INCREASE_HEALTH_CAPACITY_40);
    powerupIncreaseShieldCapacity20 = loadImage(Constants.FILENAME_POWERUP_INCREASE_SHIELD_CAPACITY_20);
    powerupIncreaseShieldCapacity40 = loadImage(Constants.FILENAME_POWERUP_INCREASE_SHIELD_CAPACITY_40);

    ufo[0] = loadImage(Constants.FILENAME_UFO);
    ufo[1] = loadImage(Constants.FILENAME_UFO_SHIELD_WEAK);
    ufo[2] = loadImage(Constants.FILENAME_UFO_SHIELD_OK);
    ufo[3] = loadImage(Constants.FILENAME_UFO_SHIELD_STRONG);
    ufoShorty = loadImage(Constants.FILENAME_UFO_SHORTY);
    ufoShot = loadImage(Constants.FILENAME_UFO_SHOT);
    ufoShortyShot = loadImage(Constants.FILENAME_UFO_SHORTY_SHOT);
    bulletImage = loadImage(Constants.FILENAME_PLASMA_SHOT);

    shipImage[Constants.IMAGE_SPACESHIP_INDEX] = loadImage(Constants.FILENAME_SPACESHIP);
    shipImage[Constants.IMAGE_SPACESHIP_SHIELD_INDEX] = loadImage(Constants.FILENAME_SPACESHIP_SHIELD);
    shipImage[Constants.IMAGE_SPACESHIP_THRUST1_INDEX] = loadImage(Constants.FILENAME_SPACESHIP_THRUST1);
    shipImage[Constants.IMAGE_SPACESHIP_THRUST2_INDEX] = loadImage(Constants.FILENAME_SPACESHIP_THRUST2);
    shipImage[Constants.IMAGE_SPACESHIP_THRUST3_INDEX] = loadImage(Constants.FILENAME_SPACESHIP_THRUST3);

    fireShot = false;

    // Add the player entity
    PlayerEntity ship = new PlayerEntity(this.imageObserver, shipImage);
    ship.setImage(shipImage[0].getImage());
    ship.setRotationRate(0.0);
    ship.setFaceAngle(0);
    ship.setMoveAngle(ship.getFaceAngle() - 90);
    ship.defineHomePosition((screenWidth - ship.getWidth()) / 2, (screenHeight - ship.getHeight()) / 2);
    ship.setPosition((screenWidth - ship.getWidth()) / 2, (screenHeight - ship.getHeight()) / 2);
    ship.setVisible(false); // Only make ship visible when game is being played
    this.setNewPlayerEntity(ship);

    // Configure the entity lists to remove dead entities from the list
    this.removeDeadPlayerShotsFromEntityList();
    this.removeDeadEnemiesFromEntityList();
    this.removeDeadPowerupsFromEntityList();
    this.removeDeadEnemyShotsFromEntityList();

    this.state = GameEngineConstants.GameState.INTRODUCTION;
    requestGameStart = false;
    requestSuperShield = false;
    requestTheBomb = false;
    prepareTheBomb = false;
    executeTheBomb = false;
    //invokePlayerDeadState = false;

    ufoManager = new UFOEntityManager();
    lastUFOCollisionTime = System.currentTimeMillis();
    //launchStrongUFO = false;
    ufoTypeToLaunch = 0;

    msgGameStartScreen = new StaticText(Constants.MSG_GAME_START, Color.YELLOW, Constants.FONT_GAME_START_SCREEN, screenWidth, screenHeight);

    firstIncreasedHealthPointMark = false;
    secondIncreasedHealthPointMark = false;
  }

  private EntityImage loadImage(String imageFilename)
  {
    EntityImage imageEntity = new EntityImage(this.imageObserver, GameEngineConstants.EntityTypes.UNDEFINED);
    imageEntity.load(imageFilename);
    return imageEntity;
  }

  @Override
  public void userGameStart()
  {
//    try
//    {
//      System.out.println("userGameStart - Sleeping for a second to let sound stabalize.");
//      Thread.sleep(1000);
//    }
//    catch(Exception e)
//    {
//      e.printStackTrace();
//    }

//    if (!playingBackGroundMusic)
//    {
//      System.out.println("userGameStart - Background not playing. Starting music.");
//      //playingBackGroundMusic = true;
//      //mySoundSystem.play(BACKGROUND_MUSIC);
//    }
//    else
//    {
//      System.out.println("userGameStart - Background already playing.");
//    }
  }

  @Override
  public void userGamePreUpdate()
  {
    switch (state)
    {
      case INTRODUCTION:

//        if (!playingBackGroundMusic && !mySoundSystem.playing(BACKGROUND_MUSIC))
//        {
//          System.out.println("userGameStart - Background not playing. Starting music.");
//          playingBackGroundMusic = true;
//          mySoundSystem.play(BACKGROUND_MUSIC);
//        }
//        else
//        {
//          System.out.println("userGameStart - Background already playing.");
//        }

        if (requestGameStart)
        {
          // Stop the Intro Music
          requestGameStart = false;
          state = GameEngineConstants.GameState.GAME_START;
          timerGameStart = System.currentTimeMillis();
        }

        break;

      case GAME_START:

        // Pause the desired amount of time in milliseconds (specified in GAME_START_INTERVAL) before transitioning to the PLAYING state
        if (System.currentTimeMillis() > (Constants.GAME_START_INTERVAL + timerGameStart))
        {
          state = GameEngineConstants.GameState.PLAYING;
          //mySoundSystem.stop(BACKGROUND_MUSIC);
          this.resetGame();
        }

        break;

      case PLAYING:

        // For debugging purposes
        if (increaseGunRequest)
        {
          increaseGunRequest = false;
          ((PlayerEntity)getPlayer()).incrementByAmount(AttributeType.ATTRIBUTE_FIREPOWER, 1);
        }

        // For debugging purposes
        if (decreaseGunRequest)
        {
          decreaseGunRequest = false;
          ((PlayerEntity)getPlayer()).decrementByAmount(AttributeType.ATTRIBUTE_FIREPOWER, 1);
        }

        // If there is no more health remaining for the player ship, the player needs to be marked dead. 
        if (!((PlayerEntity) getPlayer()).isEquipped(Constants.AttributeType.ATTRIBUTE_HEALTH))
        {
          soundManager.quickPlay(SoundManager.SOUND_FILE_PLAYER_SHIP_EXPLOSION, true);
          state = GameEngineConstants.GameState.PLAYER_DEAD;
          timerPlayerDeadState = System.currentTimeMillis();
        }

        if (getEnemies().isEmpty())
        {
          soundManager.quickPlay(SoundManager.SOUND_FILE_NEXT_WAVE, true);
          state = GameEngineConstants.GameState.LEVEL_NEXT;
          timerNextLevel = System.currentTimeMillis();
        }

        if (ufoManager.ufoShouldBeLaunched(currentLevel))
        {
          if ((currentLevel >= Constants.GAME_LAUNCH_SUPER_UFO_LEVEL) && (currentLevel < Constants.GAME_LAUNCH_SHORTY_UFO_LEVEL))
          {
            ufoTypeToLaunch = GameUtility.random.nextInt(2);
          }
          else if (currentLevel >= Constants.GAME_LAUNCH_SHORTY_UFO_LEVEL)
          {
            // Starting with level GAME_LAUNCH_SHORTY_UFO_LEVEL we only want to launch strong or shorty UFOs
            ufoTypeToLaunch = GameUtility.random.nextInt(2);
            ufoTypeToLaunch = ufoTypeToLaunch + 1;
          }

          // TODO: Possibly use a factory pattern here to create a new UFO
          switch (ufoTypeToLaunch)
          {
            case 1:
              UFOStrongEntity newStrongUfo = new UFOStrongEntity(this.imageObserver, ufo, ufoManager, (int) (screenHeight * 0.90), (int) (screenHeight * 0.10), 0, screenWidth);
              addEnemy(newStrongUfo);
              break;

            case 2:
              UFOShorty newShortyUfo = new UFOShorty(this.imageObserver, ufoShorty, ufoManager, (int) (screenHeight * 0.90), (int) (screenHeight * 0.10), ufoShorty.getWidth(), screenWidth + ufoShorty.getWidth());
              addEnemy(newShortyUfo);
              break;

            default:

              UFOEntity newUfo = new UFOEntity(this.imageObserver, ufo[0].getImage(), ufoManager, (int) (screenHeight * 0.90), (int) (screenHeight * 0.10), 0, screenWidth);
              addEnemy(newUfo);
          }
        }

        // This will simulate a 3 second counter before detonating the bomb
        if (prepareTheBomb)
        {
          if (System.currentTimeMillis() > (1000 + prepareBombTimer))
          {
            if (prepareBombTimerSecondCount < 1)  // was <=
            {
              executeTheBomb = true;
              prepareTheBomb = false;
              playCountdownTimerSound = false;
            }
            else
            {
              playCountdownTimerSound = true;
              prepareBombTimer = System.currentTimeMillis();              
              prepareBombTimerSecondCount--;
              
            }
          }
        }

        if (executeTheBomb)
        {
          executeTheBomb = false;
          if (((PlayerEntity) getPlayer()).isEquipped(Constants.AttributeType.ATTRIBUTE_THE_BOMB))
          {
            executeTheBomb();
            ((PlayerEntity) getPlayer()).decrementByAmount(Constants.AttributeType.ATTRIBUTE_THE_BOMB, 1);;
          }
        }

        break;

      case LEVEL_NEXT:

        if (System.currentTimeMillis() > (Constants.NEXT_LEVEL_INTERVAL + timerNextLevel))
        {
          this.nextLevel();
          state = GameEngineConstants.GameState.PLAYING;
        }

        break;

      case PLAYER_DEAD:

        if (System.currentTimeMillis() > (Constants.PLAYER_DEAD_INTERVAL + timerPlayerDeadState))
        {
          state = GameEngineConstants.GameState.GAMEOVER;
          timerGameOver = System.currentTimeMillis();
        }

        break;

      case GAMEOVER:

        if (System.currentTimeMillis() > (Constants.GAMEOVER_INTERVAL + timerGameOver))
        {
          resetEntityLists();
          state = GameEngineConstants.GameState.INTRODUCTION;
        }

        break;

      default:
    }
  }

  @Override
  public void userGameUpdateEntity(Entity2D entity)
  {
    // Only update entities that are alive
    if (!entity.isAlive())
    {
      return;
    }

    if (entity instanceof EnemyEntity)
    {

      if (((EnemyEntity) entity).getEnemyType() == Constants.EnemyTypes.UFO)
      {
        if (((UFOEntity) entity).shouldFireShot())
        {
          soundManager.playSound(SoundManager.SOUND_RESOURCE_UFO_SHOOTING);
          addEnemyShot(stockUFOBullet(entity.getCenter(), getPlayer().getCenter()));
        }

        // We do not want to warp the UFO
        return;
      }

      if (((EnemyEntity) entity).getEnemyType() == Constants.EnemyTypes.UFO_STRONG)
      {
        if (((UFOEntity) entity).shouldFireShot())
        {
          soundManager.playSound(SoundManager.SOUND_RESOURCE_UFO_SHOOTING);
          addEnemyShot(stockUFOBullet(entity.getCenter(), getPlayer().getCenter()));
        }

        // We do not want to warp the UFO
        return;
      }

      if (((EnemyEntity) entity).getEnemyType() == Constants.EnemyTypes.UFO_SHORTY)
      {
        if (((UFOEntity) entity).shouldFireShot())
        {
          // TODO: See if this method stockUFOShortyBullet() can be combined with the method stockUFOBullet()
          soundManager.playSound(SoundManager.SOUND_RESOURCE_UFO_SHOOTING);
          addEnemyShot(stockUFOShortyBullet(entity.getCenter(), getPlayer().getCenter()));
        }

        // We do not want to warp the UFO
        return;
      }
    }

    GameUtility.warp(entity, screenWidth, screenHeight);
  }

  @Override
  public void userHandleEntityCollision(Entity2D entity1, Entity2D entity2)
  {
    // Handle player entity collision with an enemy
    if (entity1.getEntityType() == GameEngineConstants.EntityTypes.PLAYER)
    {
      boolean shieldsEngaged = false;

      if (keyShield && ((PlayerEntity) entity1).isEquipped(Constants.AttributeType.ATTRIBUTE_SHIELD))
      {
        shieldsEngaged = true;
      }

      // TODO: Add condition to check if player has auto shield and there is shield amount remaining.
      // NOTE: The alternative is to perform this check inside a method in the player entity class and simply call this method

      if (entity2.getEntityType() == GameEngineConstants.EntityTypes.ENEMY)
      {
        // At this point the enemy is either an asteroid or a UFO
        int damageAmount = 0;

        // Determine the amount of damage to the player ship.
        switch (((EnemyEntity) entity2).getEnemyType())
        {
          case UFO_SHORTY:
          case UFO_STRONG:
          case UFO:

            // If the player collides with a UFO determine the damage amount (if shield are not engaged double damage is done to the players health)
            damageAmount = (shieldsEngaged ? Constants.UFO_DAMAGE_AMOUNT : (2 * Constants.UFO_DAMAGE_AMOUNT));

            if (System.currentTimeMillis() > (Constants.PLAYER_TIME_BETWEEN_REGISTERING_UFO_COLLISIONS + lastUFOCollisionTime))
            {
              //System.out.println("Registering a collision with a UFO of type: " + ((EnemyEntity) entity2).getEnemyType());
              // TODO: Needs to go into a method call
              if (shieldsEngaged)
              {
                // If the player had shields and after the damage, the value is zero, play the sound for loosing shields
                if (((PlayerEntity) getPlayer()).getValue(AttributeType.ATTRIBUTE_SHIELD) == 0)
                {
                  soundManager.quickPlay(SoundManager.SOUND_FILE_PLAYER_SHIELDS_DEPLETED, true);
                  ((PlayerEntity) entity1).decrementByAmount(Constants.AttributeType.ATTRIBUTE_SHIELD, damageAmount);
                }
                else
                {
                  soundManager.playSound(SoundManager.SOUND_RESOURCE_PLAYER_SHIELDS_HIT);
                }
             }
             else
             {
                // TODO: Play sound of players hull getting hit here
                soundManager.playSound(SoundManager.SOUND_RESOURCE_PLAYER_HULL_HIT);
                ((PlayerEntity) entity1).decrementByAmount(Constants.AttributeType.ATTRIBUTE_HEALTH, damageAmount);
                ((PlayerEntity) entity1).decrementByAmount(Constants.AttributeType.ATTRIBUTE_FIREPOWER, 1);   // A collision with anything reduces the firepower by 1
                entity1.setVelocity(0, 0);                    // Stop the player ship when it hits an enemy. This adds some realism.
              }

              lastUFOCollisionTime = System.currentTimeMillis();
            }
            break;

          // Default case, an asteroid
          default:

            //System.out.println("Registering a collision with an asteroid of type: " + ((EnemyEntity) entity2).getEnemyType());
            
            // Regardless of whether the shields are engaged or not, the asteroid damage is the same, one unit
            damageAmount = Constants.DEFAULT_DAMAGE_AMOUNT;
            breakAsteroid((EntityImage) entity2);

            // TODO: Needs to go into a method call
            if (shieldsEngaged)
            {
              ((PlayerEntity) entity1).decrementByAmount(Constants.AttributeType.ATTRIBUTE_SHIELD, damageAmount);

              // If the player had shields and after the damage, the value is zero, play the sound for loosing shields
              if (((PlayerEntity) getPlayer()).getValue(AttributeType.ATTRIBUTE_SHIELD) == 0)
              {
                soundManager.quickPlay(SoundManager.SOUND_FILE_PLAYER_SHIELDS_DEPLETED, true);
                
              }
              // Otherwise reduce the player health by the damage amount, stop the player and reduce its fire power 
              else
              {
                soundManager.playSound(SoundManager.SOUND_RESOURCE_PLAYER_SHIELDS_HIT);
              }
            }
            else
            {
              //soundManager.playSound(SoundManager.SOUND_RESOURCE_PLAYER_HULL_HIT);
              ((PlayerEntity) entity1).decrementByAmount(Constants.AttributeType.ATTRIBUTE_HEALTH, damageAmount);
              ((PlayerEntity) entity1).decrementByAmount(Constants.AttributeType.ATTRIBUTE_FIREPOWER, 1);   // A collision with anything reduces the firepower by 1
              entity1.setVelocity(0, 0);                    // Stop the player ship when it hits an enemy. This adds some realism.
            }
        }

        // Only kill off the entity if it is either a UFO or ASTEROID, hence why it is in this condition body
        entity2.kill();
      }

      /*
       * Collision between player and enemy shot 
       */
      if (entity2.getEntityType() == GameEngineConstants.EntityTypes.ENEMY_SHOT)
      {
        // If shields are engaged reduce the shields by the damage amount 
        if (shieldsEngaged) // OR: Player had auto shield and shield amount 
        {
          soundManager.playSound(SoundManager.SOUND_RESOURCE_PLAYER_SHIELDS_HIT);
          ((PlayerEntity) entity1).decrementByAmount(Constants.AttributeType.ATTRIBUTE_SHIELD, 1);
        }
        // Otherwise reduce the player health by the damage amount 
        else
        {
          soundManager.playSound(SoundManager.SOUND_RESOURCE_PLAYER_HULL_HIT);
          ((PlayerEntity) entity1).decrementByAmount(Constants.AttributeType.ATTRIBUTE_HEALTH, 1);
        }

        // Only kill off the entity if it is an ENEMY_SHOT, hence why it is in this condition body
        entity2.kill();
      }

      // Kill off the player entity
      // NOTE: This call will only kill the player if the players health runs out
      entity1.kill();

      // NOTE: If the call entity2.kill() was called from here, it would kill off any entity2 regardless of type
    }

    /* 
     * Collision between player shot and enemy
     */
    if (entity1.getEntityType() == GameEngineConstants.EntityTypes.PLAYER_SHOT)
    {
      // Plasma shot collides with enemy, kill off both 
      // Plasma shot collides with enemy shot, do nothing
      // Super Shield collides with enemy kill off enemy
      // Super shield collides with enemy shot, kill off enemy shot

      if (entity2.getEntityType() == GameEngineConstants.EntityTypes.ENEMY)
      {
        // If the player shot is plasma
        if (((PlayerShotEntity) entity1).getShotType() == Constants.PlayerShotType.PLASMA)
        {
          //System.out.print("Player Shot of type " + ((PlayerShotEntity) entity1).getShotType() + " collided with a(n) ");
          entity1.kill();
        }

        // NOTE: All enemy entities point values will be added to the player's score inside of each entity's kill
        //       method since a call to the kill method here may not kill the enemy depending on what type of enemy
        //       it is (e.g., a UFO).
        entity2.kill();
        
        switch (((EnemyEntity) entity2).getEnemyType())
        {
          case ASTEROID_BIG:
          case ASTEROID_MEDIUM:
          case ASTEROID_SMALL:
          case ASTEROID_TINY:

            //System.out.println(" asteroid of type " + ((EnemyEntity) entity2).getEnemyType());
            // Add the point value of the asteroid to the player's score 
            breakAsteroid((EntityImage) entity2);
            break;

          case UFO_SHORTY:
          case UFO_STRONG:

            if (((UFOStrongEntity) entity2).getUfoStrength() > 0)
            {
              soundManager.playSound(SoundManager.SOUND_RESOURCE_UFO_SHIELDS_HIT);
            }
            else
            {
              // This is required to play the UFO sounds for shields failing  
              if (!((UFOStrongEntity) entity2).wasUfoHullHit())
              {
                soundManager.playSound(SoundManager.SOUND_RESOURCE_UFO_SHIELDS_FAIL);
                ((UFOStrongEntity) entity2).ufoHullWasHit();
              }
              // TODO: Possibly play a sound here
            }

          case UFO:
            //System.out.println(" UFO of type " + ((EnemyEntity) entity2).getEnemyType());

            if (!entity2.isAlive())
            {
              soundManager.quickPlay(SoundManager.SOUND_FILE_UFO_EXPLOSION, true);
            }
            break;

          default:
        }
      }

      // If the player shot is SUPER SHIELD
      if (((PlayerShotEntity) entity1).getShotType() == Constants.PlayerShotType.SUPER_SHIELD)
      {
        if (entity2.getEntityType() == GameEngineConstants.EntityTypes.ENEMY_SHOT)
        {
          entity2.kill();
        }
      }
    }

    // Handle player entity collision with powerup
    if (entity2.getEntityType() == GameEngineConstants.EntityTypes.POWER_UP)
    {
      // NOTE: If entity2 is a powerup then entity 1 is the player entity      
      switch (((PowerupEntity) entity2).getPowerupType())
      {
        case POWERUP_THRUST:
          Constants.AttributeType attributeTypeForThrust = ((PowerupEntity) entity2).getAttributeType();
          ((PlayerEntity) entity1).setValue(attributeTypeForThrust, ((PowerupEntity) entity2).getValue());
          break;

        case POWERUP_SHIELD:
        case POWERUP_HEALTH:
        case POWERUP_FIREPOWER:
        case POWERUP_SUPER_SHIELD:
        case POWERUP_THE_BOMB:
        case POWERUP_FULL_HEALTH:
        case POWERUP_FULL_SHIELD:

          switch (((PowerupEntity) entity2).getPowerupType())
          {
            case POWERUP_FULL_HEALTH:
              soundManager.playSound(SoundManager.SOUND_RESOURCE_POWERUP_FULL_HEALH);
              break;

            case POWERUP_FULL_SHIELD:
              soundManager.playSound(SoundManager.SOUND_RESOURCE_POWERUP_FULL_SHIELD);
              break;

            default:
              soundManager.playSound(SoundManager.SOUND_RESOURCE_POWERUP_ATTRIBUTE_PICKUP);
          }

          Constants.AttributeType attributeType = ((PowerupEntity) entity2).getAttributeType();
          ((PlayerEntity) entity1).incrementByAmount(attributeType, ((PowerupEntity) entity2).getValue());
          break;

        case POWERUP_INCREASE_HEALTH_CAPACITY:
        case POWERUP_INCREASE_SHIELD_CAPACITY:
          soundManager.playSound(SoundManager.SOUND_RESOURCE_POWERUP_ATTRIBUTE_PICKUP);
          Constants.AttributeType attributeTypeForIncreaseCapacityShield = ((PowerupEntity) entity2).getAttributeType();
          ((PlayerEntity) entity1).setLimit(attributeTypeForIncreaseCapacityShield, ((PowerupEntity) entity2).getValue());
          ((PlayerEntity) entity1).setValue(attributeTypeForIncreaseCapacityShield, ((PowerupEntity) entity2).getValue());
          break;

        case POWERUP_250:
        case POWERUP_500:
        case POWERUP_1000:
          soundManager.playSound(SoundManager.SOUND_RESOURCE_POWERUP_POINT_BONUS);
          break;

        default:
          break;
      }

      // NOTE: This is being called somewhere else for powerups.
      entity2.kill();
    }
  }

  @Override
  public void gameDetectCollisions()
  {
    LinkedList<Entity2D> playerShots = getPlayerShot();
    LinkedList<Entity2D> enemyShots = getEnemyShots();

    /*
     *  First, compare the player shot vector with the enemy shot vector to check for any collisions
     *  
     *  NOTE: This is put before the check if the player is dead so even after the player dies,
     *        any remaining player shots can still strike the enemy.
     */
    for (int playerShotIndex = 0; playerShotIndex < playerShots.size(); playerShotIndex++)
    {
      Entity2D currentPlayerShot = (Entity2D) playerShots.get(playerShotIndex);

      if (currentPlayerShot.isAlive())
      {
        // Second, compare the player shot vector with all enemies to check for any collisions
        for (int enemyIndex = 0; enemyIndex < enemyShots.size(); enemyIndex++)
        {
          Entity2D currentEnemy = (Entity2D) enemyShots.get(enemyIndex);

          if (currentEnemy.isAlive())
          {
            if (currentPlayerShot.collidesWith(currentEnemy.getBoundingRectangle()))
            {
              userHandleEntityCollision(currentPlayerShot, currentEnemy);
            }
          }
        }
      }
    }

    super.gameDetectCollisions();
  }

  @Override
  public void userProcessInput()
  {
    switch (this.state)
    {
      case LEVEL_NEXT:
      case PLAYING:
        if (playerMovement.keyLeft())
        {
          getPlayer().setRotationRate(-Constants.PLAYER_ROTATION_RATE);
        }
        else if (playerMovement.keyRight())
        {
          getPlayer().setRotationRate(Constants.PLAYER_ROTATION_RATE);
        }
        else
        {
          getPlayer().setRotationRate(0.0);
        }

        if (fireShot)
        {
          fireBullet();
          fireShot = false;
        }

        if (thrust)
        {
          applyThrust();
        }

        ((PlayerEntity) getPlayer()).applyPlayerControlsToDisplayRespectiveImages(keyShield, thrust);

        if (requestSuperShield)
        {
          requestSuperShield = false;

          if (((PlayerEntity) getPlayer()).isEquipped(Constants.AttributeType.ATTRIBUTE_SUPER_SHIELD))
          {
            soundManager.playSound(SoundManager.SOUND_RESOURCE_POWERUP_SUPER_SHIELDS);            
            stockSuperShield();
            ((PlayerEntity) getPlayer()).decrementByAmount(Constants.AttributeType.ATTRIBUTE_SUPER_SHIELD, 1);;
          }
          else
          {
            // TODO: Play sound here when no super shields are present and player presses super shield button
            soundManager.quickPlay(SoundManager.SOUND_FILE_PLAYER_WEAPON_ATTRIBUTE_EMPTY, true);
          }
        }

        if (requestTheBomb && !prepareTheBomb)
        {
          requestTheBomb = false;
          prepareTheBomb = true;
          playCountdownTimerSound = true;
          prepareBombTimerSecondCount = 3;
          prepareBombTimer = System.currentTimeMillis();
        }
      default:
    }
  }

  @Override
  public void userGamePreDraw(Graphics2D g)
  {
    // Draw the background. Note that the background image will be displayed for all game states
    g.drawImage(background.getImage(), 0, 0, screenWidth - 1, screenHeight - 1, this.imageObserver);

    switch (this.state)
    {
      case INTRODUCTION:

        displayIntroductionMainScreen(g);
        break;

      case GAME_START:

        displayGameStartScreen(g);
        break;

      case PLAYING:

        break;

      default:
    }
  }

  @Override
  public void userGamePostDraw(Graphics2D g)
  {
    // For debugging purposes
    int line = 300;

    switch (this.state)
    {
      case INTRODUCTION:

        break;

      case GAME_START:

        break;

      case PLAYING:

        // TODO: If is here where the different users
        // Draw player health bar
        int healthBarIndex = ((PlayerEntity) getPlayer()).getHealthContainerIndex();
        g.drawImage(barFrame[healthBarIndex].getImage(), screenWidth - barFrame[2].getWidth() - 20, 18, this.imageObserver);
        for (int n = 0; n < ((PlayerEntity) getPlayer()).getValue(Constants.AttributeType.ATTRIBUTE_HEALTH); n++)
        {
          int dx = (screenWidth - barFrame[2].getWidth() - 18) + n * 5;
          g.drawImage(barImage[0].getImage(), dx, 20, this.imageObserver);
        }

        // Draw the label for the health bar
        g.setFont(Constants.FONT_GAME_PLAYING_HUD_SMALL);
        boundsGamePlayingHUDMsgs = g.getFontMetrics().getStringBounds(Constants.MSG_GAME_PLAYING_HEALTH, g);
        g.setColor(Color.WHITE);
        g.drawString(Constants.MSG_GAME_PLAYING_HEALTH, (int) (screenWidth - barFrame[2].getWidth() - 20 - boundsGamePlayingHUDMsgs.getWidth()), 30);

        // Draw player shield bar
        int shieldBarIndex = ((PlayerEntity) getPlayer()).getShieldContainerIndex();
        g.drawImage(barFrame[shieldBarIndex].getImage(), screenWidth - barFrame[2].getWidth() - 20, 33, this.imageObserver);
        for (int n = 0; n < ((PlayerEntity) getPlayer()).getValue(Constants.AttributeType.ATTRIBUTE_SHIELD); n++)
        {
          int dx = (screenWidth - barFrame[2].getWidth() - 18) + n * 5;
          g.drawImage(barImage[1].getImage(), dx, 35, this.imageObserver);
        }

        // Draw the label for the shield bar
        g.setFont(Constants.FONT_GAME_PLAYING_HUD_SMALL);
        boundsGamePlayingHUDMsgs = g.getFontMetrics().getStringBounds(Constants.MSG_GAME_PLAYING_SHIELD, g);
        g.setColor(Color.WHITE);
        g.drawString(Constants.MSG_GAME_PLAYING_SHIELD, (int) (screenWidth - barFrame[2].getWidth() - 20 - boundsGamePlayingHUDMsgs.getWidth()), 47);

        // Draw label next to bullet upgrades
        g.setFont(Constants.FONT_GAME_PLAYING_HUD_MEDIUM);
        boundsGamePlayingHUDMsgs = g.getFontMetrics().getStringBounds(Constants.MSG_GAME_PLAYING_FIREPOWER, g);
        g.setColor(Color.WHITE);
        g.drawString(Constants.MSG_GAME_PLAYING_FIREPOWER, (int) 20, 40);

        //        // Draw the bullet upgrades
        //        for (int n = 0; n < ((PlayerEntity) getPlayer()).getValue(Constants.AttributeType.ATTRIBUTE_FIREPOWER); n++)
        //        {
        int n = 0;
        int dx = (int) (boundsGamePlayingHUDMsgs.getWidth() + 15 + n * 13);
        g.drawImage(powerupGun[((PlayerEntity) getPlayer()).getValue(AttributeType.ATTRIBUTE_FIREPOWER) - 1].getImage(), dx, 17, this.imageObserver);
        //        }

        // Draw the score
        g.setFont(Constants.FONT_GAME_PLAYING_HUD_MEDIUM);
        g.setColor(Color.WHITE);
        String scoreMsg = Constants.MSG_GAME_PLAYING_SCORE + GameUtility.lPadZero(ScoreManager.getScore(), 6);
        Rectangle2D boundsScore = g.getFontMetrics().getStringBounds(scoreMsg, g);
        g.drawString(scoreMsg, (int) (((screenWidth - boundsScore.getWidth()) / 2) - 40), 40);

        // Draw the current level
        g.setFont(Constants.FONT_GAME_PLAYING_HUD_MEDIUM);
        g.setColor(Color.WHITE);
        String waveMsg = Constants.MSG_GAME_PLAYING_WAVE + GameUtility.lPadZero(currentLevel, 2);
        g.drawString(waveMsg, 20, 65);

        // Draw the super shield icon along with amount
        g.drawImage(hudSuperShield.getImage(), 20, 85, this.imageObserver);
        g.setFont(Constants.FONT_GAME_PLAYING_HUD_MEDIUM);
        g.setColor(Color.WHITE);
        g.drawString(((PlayerEntity) getPlayer()).getValue(Constants.AttributeType.ATTRIBUTE_SUPER_SHIELD) + "", 50, 103);

        // Draw the bomb icon along with amount
        g.drawImage(hudTheBomb.getImage(), 20, 115, this.imageObserver);
        g.setFont(Constants.FONT_GAME_PLAYING_HUD_MEDIUM);
        g.setColor(Color.WHITE);
        g.drawString(((PlayerEntity) getPlayer()).getValue(Constants.AttributeType.ATTRIBUTE_THE_BOMB) + "", 50, 133);

        // Draw the auto shield icon along with amount
        //g.drawImage(powerupAutoShield.getImage(), 20, 145, this.imageObserver);

        if (((PlayerEntity) getPlayer()).getValue(AttributeType.ATTRIBUTE_THRUST) == Constants.SHIP_INCREASED_ACCELERATION_2)
        {
          g.drawImage(powerupThrust[0].getImage(), 20, 185, this.imageObserver);
        }
        else if (((PlayerEntity) getPlayer()).getValue(AttributeType.ATTRIBUTE_THRUST) == Constants.SHIP_INCREASED_ACCELERATION_3)
        {
          g.drawImage(powerupThrust[1].getImage(), 20, 185, this.imageObserver);
        }

        if (prepareTheBomb)
        {
          g.setFont(Constants.FONT_GAME_PLAYING_HUD_LARGE1);
          g.setColor(Color.WHITE);
          
          if (playCountdownTimerSound)
          {
            switch((int)prepareBombTimerSecondCount)
            {
              case 3:
              case 2:
              case 1:
                soundManager.playSound(SoundManager.SOUND_RESOURCE_PLAYER_BOMB_COUNTDOWN_3);
                this.playCountdownTimerSound = false;
                break;
              default:
                soundManager.playSound(SoundManager.SOUND_RESOURCE_PLAYER_BOMB_COUNTDOWN_2);
                this.playCountdownTimerSound = false;
            }
          }

          String countDownMsg = Constants.DOT_DOT_DOT + prepareBombTimerSecondCount + Constants.DOT_DOT_DOT;
          boundsGamePlayingHUDMsgs = g.getFontMetrics().getStringBounds(countDownMsg, g);
          g.drawString(countDownMsg, (int) ((screenWidth - boundsGamePlayingHUDMsgs.getWidth()) / 2), (int) ((screenHeight - boundsGamePlayingHUDMsgs.getHeight()) / 2));
        }

        if (displayDebugInfo)
        {
          g.setFont(Constants.FONT_DEBUG);
          g.setColor(Color.WHITE);

          g.drawString(Constants.DEBUG_MSG_THRUST_ON + thrust, 560, line);
          line += 16;

          g.drawString(Constants.DEBUG_MSG_SHIELD + keyShield, 560, line);
          line += 16;

          //          g.drawString(Constants.DEBUG_MSG_PREV_SHIELD + previousShieldState, 560, line);
          //          line += 16;
          //          
          //          g.drawString(Constants.DEBUG_MSG_PREV_SHIELD + previousShieldState, 560, line);
          //          line += 16;
          //          
          g.drawString(Constants.DEBUG_MSG_UFO_PROB + ufoManager.getProbability(), 560, line);
          line += 16;
          g.drawString(Constants.DEBUG_MSG_THRUST_VALUE + ((PlayerEntity) getPlayer()).getValue(AttributeType.ATTRIBUTE_THRUST), 560, line);
          line += 16;
          g.drawString(Constants.DEBUG_MSG_SPAWN_PROBABILITY + whichProbability, 560, line);
          line += 16;
        }

        break;

      case PLAYER_DEAD:

        displayPlayerDeadScreen(g);
        break;

      case GAMEOVER:

        displayGameOverScreen(g);
        break;

      case LEVEL_NEXT:

        displayNextLevelScreen(g);
        break;

      default:
    }
  }

  @Override
  public void userGameShutdown()
  {
    // TODO Auto-generated method stub
    soundManager.cleanup();
  }

  @Override
  public void gameKeyPressed(int keyCode)
  {
    switch (this.state)
    {
      case INTRODUCTION:
        
        if (keyCode == KeyEvent.VK_A)
        {
          //coin.play();
        }
        
        break;

      case GAME_START:
        break;

      // Capture players keystrokes during next level state to preserve any movements from the previous level 
      case LEVEL_NEXT:
      case PLAYING:

        playerMovement.pressed(keyCode);

        switch (keyCode)
        {
          case KeyEvent.VK_UP:
            thrust = true;
            break;

          case KeyEvent.VK_SHIFT:
            keyShield = true;
            break;

          default:
        }

        break;

      default:
    }
  }

  @Override
  public void gameKeyReleased(int keyCode)
  {
    switch (this.state)
    {
      case INTRODUCTION:

        if (keyCode == KeyEvent.VK_ENTER)
        {
          requestGameStart = true;
          //System.out.println("Attempting to stop music " + BACKGROUND_MUSIC); 
          //mySoundSystem.stop(BACKGROUND_MUSIC);
          //mySoundSystem.setVolume("OGG Music", 0.0f);
          //mySoundSystem.removeSource("OGG Music");
          //mySoundSystem.fadeOut(BACKGROUND_MUSIC, BACKGROUND_MUSIC_OGG_FILE, Constants.GAME_START_INTERVAL);
        }
          
        break;

      case GAME_START:

        break;

      // Capture players keystrokes during next level state to preserve any movements from the previous level 
      case LEVEL_NEXT:
      case PLAYING:

        playerMovement.released(keyCode);

        if (keyCode == KeyEvent.VK_A)
        {
          increaseGunRequest = true;
        }

        if (keyCode == KeyEvent.VK_B)
        {
          decreaseGunRequest = true;
        }
        
        if (keyCode == KeyEvent.VK_CONTROL)
        {
          fireShot = true;
        }

        if (keyCode == KeyEvent.VK_UP)
        {
          thrust = false;
        }

        if (keyCode == KeyEvent.VK_SHIFT)
        {
          keyShield = false;
        }

        if (keyCode == KeyEvent.VK_S)
        {
          requestSuperShield = true;
        }

        // Only accept The Bomb request if the player is equipped with at least one bomb and there is no previous bomb request pending
        //if (keyCode == KeyEvent.VK_D && !prepareTheBomb && ((PlayerEntity) getPlayer()).isEquipped(Constants.AttributeType.ATTRIBUTE_THE_BOMB))

        // If the player requests the bomb
        if (keyCode == KeyEvent.VK_D)
        {
          // If the player has at least one bomb left
          if (((PlayerEntity) getPlayer()).isEquipped(Constants.AttributeType.ATTRIBUTE_THE_BOMB))
          {
            // If there is no previous bomb launch pending
            if (!prepareTheBomb)
            {
              requestTheBomb = true;
            }
          }
          else
          {
            soundManager.quickPlay(SoundManager.SOUND_FILE_PLAYER_WEAPON_ATTRIBUTE_EMPTY, true);
          }
        }
        break;

      default:
    }
  }

  @Override
  public void gameKeyTyped(int keyCode)
  {}

  /*
   * Powerups
   */

  // Create a random powerup at the supplied sprite location
  //private void spawnPowerup(double xPos, double yPos)
  private void spawnPowerup(Position2D position)
  {
    // Only a few tiny sprites spit out a power-up
    int n = GameUtility.random.nextInt(Constants.POWERUP_TOTAL_EVENTS_TO_SPAWN);

    if (n > whichProbability)
    {
      return;
    }

    PowerupEntity powerup = null; //= new PowerupEntity(this.imageObserver);

    //int randomPowerupType = GameUtility.random.nextInt(Constants.PowerUpType.values().length - 1);

    Constants.PowerUpType randomPowerupType = Constants.PowerUpType.randomPowerupType();

    // TODO: Choose a random type

    switch (randomPowerupType)
    {
    // The cases in this switch statement will be the special cases where certian powerups are generate if certain conditions are true      

      case POWERUP_FULL_HEALTH:

        // Only spawn a health or full health if the player has less than full health.
        // TODO: Consider only spawning full health if the player has less than half health.
        if (((PlayerEntity) getPlayer()).getValue(Constants.AttributeType.ATTRIBUTE_HEALTH) < ((PlayerEntity) getPlayer()).getLimit(Constants.AttributeType.ATTRIBUTE_HEALTH))
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
        if (((PlayerEntity) getPlayer()).getValue(Constants.AttributeType.ATTRIBUTE_SHIELD) < ((PlayerEntity) getPlayer()).getLimit(Constants.AttributeType.ATTRIBUTE_SHIELD))
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
        if (!((PlayerEntity) getPlayer()).isEquipped(AttributeType.ATTRIBUTE_THRUST))
        {
          boolean spawnThrusterPowerup = false;
          int thrusterLevel = 1;
          int thrusterImageIndex = 1;
          // Only spawn thrust increase power-up if the player does not already have increased thrust.
          if (((PlayerEntity) getPlayer()).getValue(AttributeType.ATTRIBUTE_THRUST) == Constants.SHIP_DEFAULT_ACCELERATION)
          {
            // If the level is available for the thruster 2 increase, set flags and vars to spawn thruster 2 powerup
            if (currentLevel >= Constants.POWERUP_THRUST_2_MIN_LEVEL)
            {
              //System.out.println("Player is eligible for thruster 2 powerup.");
              spawnThrusterPowerup = true;
              thrusterLevel = Constants.SHIP_INCREASED_ACCELERATION_2;
              thrusterImageIndex = 0; //Constants.IMAGE_SPACESHIP_THRUST2_INDEX;
            }
          }
          else if (((PlayerEntity) getPlayer()).getValue(AttributeType.ATTRIBUTE_THRUST) == Constants.SHIP_INCREASED_ACCELERATION_2)
          {
            // If the level is available for the thruster 2 increase, set flags and vars to spawn thruster 2 powerup
            if (currentLevel >= Constants.POWERUP_THRUST_3_MIN_LEVEL)
            {
              //System.out.println("Player is eligible for thruster 3 powerup.");
              spawnThrusterPowerup = true;
              thrusterLevel = Constants.SHIP_INCREASED_ACCELERATION_3;
              thrusterImageIndex = 1; //Constants.IMAGE_SPACESHIP_THRUST3_INDEX;
            }
          }

          if (spawnThrusterPowerup)
          {
            powerup = createPowerup(Constants.PowerUpType.POWERUP_THRUST, powerupThrust[thrusterImageIndex].getImage(), position, thrusterLevel);
            break;
          }
        }
        //        else
        //        {
        //          System.out.println("Player has most powerful thruster.");
        //        }

        // Otherwise, spawn a different power-up
        powerup = createPowerup(Constants.PowerUpType.POWERUP_HEALTH, null, position, -1);
        break;

      case POWERUP_FIREPOWER:

        if (((PlayerEntity) getPlayer()).getValue(AttributeType.ATTRIBUTE_FIREPOWER) < Constants.SHIP_MAX_FIREPOWER)
        {
          powerup = createPowerup(randomPowerupType, null, position, -1);
          break;
        }

        // Otherwise, spawn a different power-up
        powerup = createPowerup(Constants.PowerUpType.POWERUP_SUPER_SHIELD, null, position, -1);
        break;

      case POWERUP_INCREASE_HEALTH_CAPACITY:

        switch (((PlayerEntity) getPlayer()).getHealthCapacity())
        {
          // If players current health capacity is the initial value, spawn the next level. 
          case Constants.SHIP_INITIAL_HEALTH:

            if (ScoreManager.getScore() > Constants.HEALTH_CAPACITY_INCREASE_TO_20_SCORE_LIMIT)
            {
              powerup = createPowerup(randomPowerupType, this.powerupIncreaseHealthCapacity20.getImage(), position, Constants.SHIP_HEALTH_CAPACITY_INCREASE_TO_20);
              break;
            }

          case Constants.SHIP_HEALTH_CAPACITY_INCREASE_TO_20:

            if (ScoreManager.getScore() > Constants.HEALTH_CAPACITY_INCREASE_TO_40_SCORE_LIMIT)
            {
              powerup = createPowerup(randomPowerupType, this.powerupIncreaseHealthCapacity40.getImage(), position, Constants.SHIP_HEALTH_CAPACITY_INCREASE_TO_40);
              break;
            }

          default:
            // Create another power-up since it is pointless to create an increase shield powerup.
            powerup = createPowerup(Constants.PowerUpType.POWERUP_SUPER_SHIELD, null, position, -1);
        }
        break;

      case POWERUP_INCREASE_SHIELD_CAPACITY:

        switch (((PlayerEntity) getPlayer()).getShieldCapacity())
        {
          // If players current shield capacity is the initial value, increase to the next level. 
          case Constants.SHIP_INITIAL_SHIELD:

            if (ScoreManager.getScore() > Constants.SHIELD_CAPACITY_INCREASE_TO_20_SCORE_LIMIT)
            {
              powerup = createPowerup(randomPowerupType, this.powerupIncreaseShieldCapacity20.getImage(), position, Constants.SHIP_HEALTH_CAPACITY_INCREASE_TO_20);
              break;
            }

            // If players current shield capacity is the 2nd level value, increase to the next level.
          case Constants.SHIP_SHIELD_CAPACITY_INCREASE_TO_20:

            // Only increase it if the respective score level is reached 
            if (ScoreManager.getScore() > Constants.SHIELD_CAPACITY_INCREASE_TO_40_SCORE_LIMIT)
            {
              powerup = createPowerup(randomPowerupType, this.powerupIncreaseShieldCapacity40.getImage(), position, Constants.SHIP_HEALTH_CAPACITY_INCREASE_TO_40);
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

    this.addPowerup(powerup);
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
        powerup = new PowerupShield(this.imageObserver);
        powerup.setImage(powerupShield.getImage());
        break;

      case POWERUP_FIREPOWER:
        powerup = new PowerupFirePower(this.imageObserver);
        powerup.setImage(powerupGun[0].getImage());
        break;

      case POWERUP_SUPER_SHIELD:
        powerup = new PowerupSuperShield(this.imageObserver);
        powerup.setImage(this.powerupSuperShield.getImage());
        break;

      case POWERUP_FULL_HEALTH:
        powerup = new PowerupFullHealth(this.imageObserver);
        powerup.setImage(this.powerupFullHealth.getImage());
        break;

      case POWERUP_FULL_SHIELD:
        powerup = new PowerupFullShield(this.imageObserver);
        powerup.setImage(this.powerupFullShield.getImage());
        break;

      case POWERUP_THE_BOMB:
        powerup = new PowerupTheBomb(this.imageObserver);
        powerup.setImage(this.powerupTheBomb.getImage());
        break;

      case POWERUP_THRUST:
        powerup = new PowerupThrust(this.imageObserver);
        powerup.setValue(value);
        if (image != null)
        {
          powerup.setImage(image);
        }
        break;

      case POWERUP_INCREASE_HEALTH_CAPACITY:
        powerup = new PowerupIncreasedHealthCapacity(this.imageObserver);
        powerup.setValue(value);
        if (image != null)
        {
          powerup.setImage(image);
        }
        break;

      case POWERUP_INCREASE_SHIELD_CAPACITY:
        powerup = new PowerupIncreasedShieldCapacity(this.imageObserver);
        powerup.setValue(value);
        if (image != null)
        {
          powerup.setImage(image);
        }
        break;

      case POWERUP_250:
        powerup = new Powerup250Points(this.imageObserver);
        powerup.setImage(powerup250.getImage());
        break;

      case POWERUP_500:
        powerup = new Powerup500Points(this.imageObserver);
        powerup.setImage(powerup500.getImage());
        break;

      case POWERUP_1000:
        powerup = new Powerup1000Points(this.imageObserver);
        powerup.setImage(powerup1000.getImage());
        break;

      // Spawning a health power-up is the default case
      default:

        powerup = new PowerupHealth(this.imageObserver);
        powerup.setImage(powerupHealth.getImage());
    }

    // Make the power-up have the same position as the entity
    powerup.setPosition(position);

    // Set rotation and direction angles of asteroid
    int faceAngle = GameUtility.random.nextInt((int) DEGREES_IN_A_CIRCLE);
    int moveAngle = GameUtility.random.nextInt((int) DEGREES_IN_A_CIRCLE);
    powerup.setFaceAngle(faceAngle);
    powerup.setMoveAngle(moveAngle);
    powerup.setRotationRate(Constants.POWERUP_ROTAITON_RATE);

    // Set velocity based on movement direction
    double angle = powerup.getMoveAngle() - 90;
    powerup.setVelocity(calcAngleMoveX(angle), calcAngleMoveY(angle));
    powerup.getVelocity().scaleThisVector(Constants.POWERUP_SPEED);

    // Set the lifespan
    powerup.setLifespan((int) (GameEngineConstants.DEFAULT_UPDATE_RATE * Constants.POWERUP_LIFE_SPAN_IN_SECS));

    return powerup;
  }

  /*
   * Create a random "big" asteroid
   */
  public void createNewAsteroid()
  {
    // Create a new asteroid sprite and set the type
    AsteroidEntity asteroid = new AsteroidEntity(this.imageObserver);
    asteroid.setEnemyType(Constants.EnemyTypes.ASTEROID_BIG);
    asteroid.setPointValue(Constants.SCORE_BIG_ASTEROIDS);

    // Pick one of the random asteroid images
    int randomAsteroidImageIndex = GameUtility.random.nextInt(NUMBER_OF_BIG_ASTEROID_IMAGES);
    asteroid.setImage(bigAsteroids[randomAsteroidImageIndex].getImage());

    // Set to a random position on the screen and prevent asteroids from
    // starting on the players position
    double x = GameUtility.random.nextInt(screenWidth - ASTEROID_DIMENSIONS);
    double y = GameUtility.random.nextInt(screenHeight - ASTEROID_DIMENSIONS);
    asteroid.setPosition(x, y);
    nudgeAsteroid(asteroid);

    // Set rotation and direction angles of asteroid
    int faceAngle = GameUtility.random.nextInt((int) DEGREES_IN_A_CIRCLE);
    int moveAngle = GameUtility.random.nextInt((int) DEGREES_IN_A_CIRCLE);
    asteroid.setFaceAngle(faceAngle);
    asteroid.setMoveAngle(moveAngle);

    // Assign a random rotation rate and determine the direction (CW or CCW)
    double rotationRate = GameUtility.random.nextInt(ASTEROID_MAX_ROTATION_RATE) + ASTEROID_MIN_ROTATION_RATE;
    rotationRate = (GameUtility.random.nextBoolean() ? -rotationRate : rotationRate);
    asteroid.setRotationRate(rotationRate);

    // Set velocity based on movement direction
    double angle = asteroid.getMoveAngle() - 90;
    double velx = calcAngleMoveX(angle);
    double vely = calcAngleMoveY(angle);
    asteroid.setVelocity(velx, vely);
    asteroid.getVelocity().scaleThisVector(BIG_ASTEROID_VELOCITY_SCALE[GameUtility.random.nextInt(BIG_ASTEROID_VELOCITY_SCALE.length)]);

    // Add the new asteroid to the sprite list
    getEnemies().add(asteroid);
  }

  // Fire a bullet from the ship's position and orientation
  public void fireBullet()
  {
    PlasmaShotEntity bullets[] = new PlasmaShotEntity[6];

    switch (((PlayerEntity) getPlayer()).getValue(Constants.AttributeType.ATTRIBUTE_FIREPOWER))
    {
      case 1:
        soundManager.playSound(SoundManager.SOUND_RESOURCE_PLAYER_1_SHOOTING);
        addPlayerShot(stockBullet());
        break;

      case 2:
        soundManager.playSound(SoundManager.SOUND_RESOURCE_PLAYER_2_SHOOTING);
        bullets[0] = stockBullet();
        bullets[1] = stockBullet();

        adjustDirection(bullets[0], -4);
        adjustDirection(bullets[1], 4);

        addPlayerShot(bullets[0]);
        addPlayerShot(bullets[1]);
        break;

      case 3:
        soundManager.playSound(SoundManager.SOUND_RESOURCE_PLAYER_3_SHOOTING);
        bullets[0] = stockBullet();
        bullets[1] = stockBullet();
        bullets[2] = stockBullet();

        adjustDirection(bullets[0], -4);
        adjustDirection(bullets[2], 4);

        addPlayerShot(bullets[0]);
        addPlayerShot(bullets[1]);
        addPlayerShot(bullets[2]);

        break;

      case 4:
        soundManager.playSound(SoundManager.SOUND_RESOURCE_PLAYER_4_SHOOTING);
        bullets[0] = stockBullet();
        bullets[1] = stockBullet();
        bullets[2] = stockBullet();
        bullets[3] = stockBullet();

        adjustDirection(bullets[0], -5);
        adjustDirection(bullets[1], 5);
        adjustDirection(bullets[2], -10);
        adjustDirection(bullets[3], 10);

        addPlayerShot(bullets[0]);
        addPlayerShot(bullets[1]);
        addPlayerShot(bullets[2]);
        addPlayerShot(bullets[3]);
        break;

      case 5:
        soundManager.playSound(SoundManager.SOUND_RESOURCE_PLAYER_5_SHOOTING);
        bullets[0] = stockBullet();
        bullets[1] = stockBullet();
        bullets[2] = stockBullet();
        bullets[3] = stockBullet();
        bullets[4] = stockBullet();
        bullets[5] = stockBullet();

        adjustDirection(bullets[0], -6);
        adjustDirection(bullets[1], 6);
        adjustDirection(bullets[2], -15);
        adjustDirection(bullets[3], 15);
        adjustDirection(bullets[4], -60);
        adjustDirection(bullets[5], 60);

        addPlayerShot(bullets[0]);
        addPlayerShot(bullets[1]);
        addPlayerShot(bullets[2]);
        addPlayerShot(bullets[3]);
        addPlayerShot(bullets[4]);
        addPlayerShot(bullets[5]);
        break;
    }
  }

  private void adjustDirection(EntityImage entity, double angle)
  {
    angle = entity.getFaceAngle() + angle;
    if (angle < 0)
      angle += 360;
    else if (angle > 360)
      angle -= 360;
    entity.setFaceAngle(angle);
    entity.setMoveAngle(entity.getFaceAngle() - 90);
    angle = entity.getMoveAngle();
    double svx = calcAngleMoveX(angle) * Constants.PLAYER_BULLET_SPEED + getPlayer().getVelocityX();
    double svy = calcAngleMoveY(angle) * Constants.PLAYER_BULLET_SPEED + getPlayer().getVelocityY();
    entity.setVelocity(svx, svy);
  }

  /*
   * When spawning a new asteroid, this method ensures that a new asteroid is not spawned on top of the player.
   */
  private void nudgeAsteroid(EntityImage entity)
  {
    double x = entity.getPositionX();
    double y = entity.getPositionY();

    // Compute the buffer around the players starting position, note that
    // this space is dependent on the height and width of the entity.
    // This is to avoid an enemy entity from being placed directly on top of
    // the player entity when the player entity begins at its home position.
    double entityWidth = entity.getWidth();
    double entityHeight = entity.getHeight();
    double horizontalCenter = screenWidth / 2;
    double verticalCenter = screenHeight / 2;
    double lowerHorizontalBufferFactor = (horizontalCenter - entityWidth) / screenWidth;
    double upperHorizontalBufferFactor = (horizontalCenter + entityWidth) / screenWidth;
    double lowerVerticalBufferFactor = (verticalCenter - entityHeight) / screenHeight;
    double upperVerticalBufferFactor = (verticalCenter + entityHeight) / screenHeight;

    // System.out.println("lowerHorizontalBufferFactor: " +
    // lowerHorizontalBufferFactor);
    // System.out.println("upperHorizontalBufferFactor: " +
    // upperHorizontalBufferFactor);
    // System.out.println("lowerVerticalBufferFactor: " +
    // lowerVerticalBufferFactor);
    // System.out.println("upperVerticalBufferFactor: " +
    // upperVerticalBufferFactor);

    // If the asteroid is near the center of the screen, nudge it away from
    // the players ship
    if ((x >= screenWidth * lowerHorizontalBufferFactor) && (x <= screenWidth * 0.5))
    {
      x -= entityWidth;
    }
    else if ((x <= screenWidth * upperHorizontalBufferFactor) && (x >= screenWidth * 0.5))
    {
      x += entityWidth;
    }

    if ((y >= screenHeight * lowerVerticalBufferFactor) && (y <= screenHeight * 0.5))
    {
      y -= entityHeight;
    }
    else if ((y <= screenHeight * upperVerticalBufferFactor) && (y >= screenHeight * 0.5))
    {
      y += entityHeight;
    }

    entity.setPosition(x, y);
  }

  private void executeTheBomb()
  {
    LinkedList<Entity2D> enemies = getEnemies();

    // Since enemies are being iterated through, killed and new ones added due to asteroids  
    // breaking into smaller ones, this list will store newly spawned asteroids to avoid a
    // potentially problem of iterating through the original list while adding new items.
    LinkedList<Entity2D> newEnemies = new LinkedList<Entity2D>();

    soundManager.playSound(SoundManager.SOUND_RESOURCE_PLAYER_THE_BOMB_EXPLOSION);

    Iterator<Entity2D> iterator = enemies.iterator();
    while (iterator.hasNext())
    {
      EnemyEntity enemy = (EnemyEntity) iterator.next();
      if (enemy instanceof AsteroidEntity)
      {
        breakAsteroid((AsteroidEntity) enemy, newEnemies);
      }
      enemy.kill();
    }

    // Add the newly created asteroids to the original enemy list

    // NOTE: We cannot use this method addAll() since it does not clear the references from 
    //       the list before adding them to the regular enemy list. Instead we must explicitly
    //       remove each element and add it to the regular enemy list.
    //getEnemies().addAll(newEnemies);
    while (!newEnemies.isEmpty())
    {
      getEnemies().add(newEnemies.remove());
    }
    newEnemies.clear();
  }

  private void stockSuperShield()
  {
    double faceAngle = getPlayer().getFaceAngle();

    for (int i = 0; i < Constants.PLAYER_NUMBER_SUPER_SHIELD_BALLS; i++)
    {
      SuperShieldEntity ss = new SuperShieldEntity(this.imageObserver);
      ss.setImage(superShield.getImage());
      ss.setFaceAngle(faceAngle);
      ss.setMoveAngle(faceAngle - 90);

      // Set the bullet's velocity
      double angle = ss.getMoveAngle();
      double svx = calcAngleMoveX(angle) * Constants.PLAYER_SUPER_SHIELD_SPEED + getPlayer().getVelocityX();
      double svy = calcAngleMoveY(angle) * Constants.PLAYER_SUPER_SHIELD_SPEED + getPlayer().getVelocityY();

      ss.setVelocity(svx, svy);

      // Set the bullet's starting position
      double x = getPlayer().getCenterX() - ss.getWidth() / 2;
      double y = getPlayer().getCenterY() - ss.getHeight() / 2;
      ss.setPosition(x, y);

      // Initialize the life span and life age
      ss.setLifespan((int) (GameEngineConstants.DEFAULT_UPDATE_RATE * Constants.PLAYER_SUPER_SHIELD_LIFESPAN));

      addPlayerShot(ss);

      faceAngle += 20;
    }
  }

  private PlasmaShotEntity stockBullet()
  {
    double faceAngle = getPlayer().getFaceAngle();

    PlasmaShotEntity bullet = new PlasmaShotEntity(this.imageObserver);

    bullet.setImage(bulletImage.getImage());
    bullet.setFaceAngle(faceAngle);
    bullet.setMoveAngle(faceAngle - 90);

    // Set the bullet's velocity
    double angle = bullet.getMoveAngle();
    double svx = calcAngleMoveX(angle) * Constants.PLAYER_BULLET_SPEED + getPlayer().getVelocityX();
    double svy = calcAngleMoveY(angle) * Constants.PLAYER_BULLET_SPEED + getPlayer().getVelocityY();

    bullet.setVelocity(svx, svy);

    // Set the bullet's starting position
    double x = getPlayer().getCenterX() - bullet.getWidth() / 2;
    double y = getPlayer().getCenterY() - bullet.getHeight() / 2;
    bullet.setPosition(x, y);

    // Initialize the life span and life age
    bullet.setLifespan((int) (GameEngineConstants.DEFAULT_UPDATE_RATE * Constants.PLAYER_BULLET_LIFE_SPAN_IN_SECS));
    return bullet;
  }

  // Fire a shot at the player ship
  // NOTE: Since the bullets are round, we do not need to work with any angles like the plasma shots.
  private EntityImage stockUFOBullet(Position2D ufoPos, Position2D playerPos)
  {
    EntityImage bullet = new EntityImage(this.imageObserver, GameEngineConstants.EntityTypes.ENEMY_SHOT);
    bullet.setImage(ufoShot.getImage());

    // Compute bullet heading given current position of player and this ufo entity     
    bullet.setVelocity(GameUtility.computeUnitVectorBetweenTwoPositions(ufoPos, playerPos).createScaledVector(Constants.UFO_SHOT_SPEED));

    // Set the bullet's starting position
    double x = ufoPos.x - bullet.getWidth() / 2;
    double y = ufoPos.y - bullet.getHeight() / 2;
    bullet.setPosition(x, y);

    // Initialize the life span and life age
    bullet.setLifespan((int) (GameEngineConstants.DEFAULT_UPDATE_RATE * Constants.UFO_BULLET_LIFE_SPAN_IN_SECS));
    return bullet;
  }

  //Fire a shot at the player ship
  // NOTE: Since the bullets are round, we do not need to work with any angles like the plasma shots.
  private EntityImage stockUFOShortyBullet(Position2D ufoPos, Position2D playerPos)
  {
    EntityImage bullet = new EntityImage(this.imageObserver, GameEngineConstants.EntityTypes.ENEMY_SHOT);
    bullet.setImage(ufoShortyShot.getImage());

    // Compute bullet heading given current position of player and this ufo entity     
    bullet.setVelocity(GameUtility.computeUnitVectorBetweenTwoPositions(ufoPos, playerPos).createScaledVector(Constants.UFO_SHORTY_SHOT_SPEED));

    // Set the bullet's starting position
    double x = ufoPos.x - bullet.getWidth() / 2;
    double y = ufoPos.y - bullet.getHeight() / 2;
    bullet.setPosition(x, y);

    // Initialize the life span and life age
    bullet.setLifespan((int) (GameEngineConstants.DEFAULT_UPDATE_RATE * Constants.UFO_SHORTY_BULLET_LIFE_SPAN_IN_SECS));
    return bullet;
  }

  /*
   * Break up an asteroid into smaller pieces
   */
  private void breakAsteroid(EntityImage entity)
  {
    switch (((EnemyEntity) entity).getEnemyType())
    {
      case ASTEROID_BIG:
        soundManager.playSound(SoundManager.SOUND_RESOURCE_ASTEROID_INITIAL_EXPLOSION);
        // Spawn medium asteroids over the old one
        getEnemies().add(spawnSmallerAsteroid(entity));
        getEnemies().add(spawnSmallerAsteroid(entity));
        getEnemies().add(spawnSmallerAsteroid(entity));

        // TODO: Draw big explosion
        break;

      case ASTEROID_MEDIUM:
        soundManager.playSound(SoundManager.SOUND_RESOURCE_ASTEROID_SMALLER_EXPLOSION);
        // Spawn small asteroids over the old one
        getEnemies().add(spawnSmallerAsteroid(entity));
        getEnemies().add(spawnSmallerAsteroid(entity));
        getEnemies().add(spawnSmallerAsteroid(entity));

        // TODO: Draw small explosion
        break;

      case ASTEROID_SMALL:
        soundManager.playSound(SoundManager.SOUND_RESOURCE_ASTEROID_SMALLER_EXPLOSION);
        // Spawn tiny asteroids over the old one
        getEnemies().add(spawnSmallerAsteroid(entity));
        getEnemies().add(spawnSmallerAsteroid(entity));
        getEnemies().add(spawnSmallerAsteroid(entity));

        // TODO: Draw small explosion
        break;

      case ASTEROID_TINY:
        soundManager.playSound(SoundManager.SOUND_RESOURCE_ASTEROID_TINY_EXPLOSION);
        // Spawn a random powerup
        spawnPowerup(entity.getPosition());

        // TODO: Draw small explosion
        break;

      default:
    }
  }

  private void breakAsteroid(EntityImage entity, LinkedList<Entity2D> list)
  {
    switch (((EnemyEntity) entity).getEnemyType())
    {
      case ASTEROID_BIG:
        // Spawn medium asteroids over the old one
        list.add(spawnSmallerAsteroid(entity));
        list.add(spawnSmallerAsteroid(entity));
        list.add(spawnSmallerAsteroid(entity));

        // TODO: Draw big explosion
        break;

      case ASTEROID_MEDIUM:
        // Spawn small asteroids over the old one
        list.add(spawnSmallerAsteroid(entity));
        list.add(spawnSmallerAsteroid(entity));
        list.add(spawnSmallerAsteroid(entity));

        // TODO: Draw small explosion
        break;

      case ASTEROID_SMALL:
        // Spawn tiny asteroids over the old one
        list.add(spawnSmallerAsteroid(entity));
        list.add(spawnSmallerAsteroid(entity));
        list.add(spawnSmallerAsteroid(entity));

        // TODO: Draw small explosion
        break;

      case ASTEROID_TINY:
        // Spawn a random powerup
        //spawnPowerup(entity.getPositionX(), entity.getPositionY());
        spawnPowerup(entity.getPosition());

        // TODO: Draw small explosion
        break;

      default:
    }
  }

  /*
   * Spawn a smaller asteroid based on passed sprite
   */
  private AsteroidEntity spawnSmallerAsteroid(EntityImage entity)
  {
    // Create a new asteroid Entity
    AsteroidEntity asteroid = new AsteroidEntity(this.imageObserver);

    // Set rotation and direction angles
    asteroid.setFaceAngle(GameUtility.random.nextInt((int) DEGREES_IN_A_CIRCLE));
    asteroid.setMoveAngle(GameUtility.random.nextInt((int) DEGREES_IN_A_CIRCLE));
    double rotationRate = GameUtility.random.nextInt(ASTEROID_MAX_ROTATION_RATE) + ASTEROID_MIN_ROTATION_RATE;
    rotationRate = (GameUtility.random.nextBoolean() ? -rotationRate : rotationRate);
    asteroid.setRotationRate(rotationRate);

    // Set velocity based on movement direction
    double angle = asteroid.getMoveAngle() - 90;
    double velx = calcAngleMoveX(angle);
    double vely = calcAngleMoveY(angle);
    asteroid.setVelocity(velx, vely);

    // Set some size-specific properties
    switch (((EnemyEntity) entity).getEnemyType())
    {
      case ASTEROID_BIG:
        asteroid.setEnemyType(Constants.EnemyTypes.ASTEROID_MEDIUM);

        // Pick one of the random asteroid images
        int i = GameUtility.random.nextInt(NUMBER_OF_MEDIUM_ASTEROID_IMAGES);
        asteroid.setImage(mediumAsteroids[i].getImage());
        asteroid.getVelocity().scaleThisVector(MEDIUM_ASTEROID_VELOCITY_SCALE[GameUtility.random.nextInt(MEDIUM_ASTEROID_VELOCITY_SCALE.length)]);
        asteroid.setPointValue(Constants.SCORE_MEDIUM_ASTEROIDS);
        break;

      case ASTEROID_MEDIUM:
        asteroid.setEnemyType(Constants.EnemyTypes.ASTEROID_SMALL);

        // Pick one of the random asteroid images
        i = GameUtility.random.nextInt(NUMBER_OF_SMALL_ASTEROID_IMAGES);
        asteroid.setImage(smallAsteroids[i].getImage());
        asteroid.getVelocity().scaleThisVector(SMALL_ASTEROID_VELOCITY_SCALE[GameUtility.random.nextInt(SMALL_ASTEROID_VELOCITY_SCALE.length)]);
        asteroid.setPointValue(Constants.SCORE_SMALL_ASTEROIDS);
        break;

      case ASTEROID_SMALL:
        asteroid.setEnemyType(Constants.EnemyTypes.ASTEROID_TINY);

        // Pick one of the random asteroid images
        i = GameUtility.random.nextInt(NUMBER_OF_TINY_ASTEROID_IMAGES);
        asteroid.setImage(tinyAsteroids[i].getImage());
        asteroid.getVelocity().scaleThisVector(TINY_ASTEROID_VELOCITY_SCALE[GameUtility.random.nextInt(TINY_ASTEROID_VELOCITY_SCALE.length)]);
        asteroid.setPointValue(Constants.SCORE_TINY_ASTEROIDS);
        break;

      default:
    }

    // Set pseudo-random position around source sprite
    int w = entity.getWidth();
    int h = entity.getHeight();
    double x = entity.getPositionX() + w / 2 + GameUtility.random.nextInt(20) - 40;
    double y = entity.getPositionY() + h / 2 + GameUtility.random.nextInt(20) - 40;
    asteroid.setPosition(x, y);

    // Add the new asteroid to the sprite list
    //getEnemies().add(asteroid);
    return asteroid;
  }

  // NOTE: This method could be moved to the GameUtility class
  protected double calcAngleMoveX(double angle)
  {
    return (double) (Math.cos(angle * Math.PI / 180));
  }

  // NOTE: This method could be moved to the GameUtility class
  protected double calcAngleMoveY(double angle)
  {
    return (double) (Math.sin(angle * Math.PI / 180));
  }

  /*
   * Ship movement methods
   */

  // Increase the thrust of the ship based on facing angle
  public void applyThrust()
  {
    PlayerEntity ship = (PlayerEntity) getPlayer();

    ship.setMoveAngle(ship.getFaceAngle() - 90);

    // Calculate the X and Y velocity based on angle
    double velx = ship.getVelocityX();
    velx += calcAngleMoveX(ship.getMoveAngle()) * ((PlayerEntity) getPlayer()).getValue(AttributeType.ATTRIBUTE_THRUST);
    if (velx < Constants.SHIP_MIN_VELOCITY)
      velx = Constants.SHIP_MIN_VELOCITY;
    else if (velx > Constants.SHIP_MAX_VELOCITY)
      velx = Constants.SHIP_MAX_VELOCITY;
    double vely = ship.getVelocityY();
    vely += calcAngleMoveY(ship.getMoveAngle()) * ((PlayerEntity) getPlayer()).getValue(AttributeType.ATTRIBUTE_THRUST);
    if (vely < Constants.SHIP_MIN_VELOCITY)
      vely = Constants.SHIP_MIN_VELOCITY;
    else if (vely > Constants.SHIP_MAX_VELOCITY)
      vely = Constants.SHIP_MAX_VELOCITY;
    ship.setVelocity(velx, vely);
  }

  /*
   * Game Reset and Next Level methods
   */

  public void resetGame()
  {
    // Reset player variables

    // TODO: Consider creating a player manager to encapsulate these actions into a reset method
    //invokePlayerDeadState = false;
    //gamePaused = false;
    //unpauseGame();
    //requestToQuitPlayingGame = false;
    //quitPlayingGame = false;
    //doNotQuitPlayingGame = false;
    //currentShotType = SIConstants.ShotTypes.NORMAL;

    // This should make the player sprite visible, alive and center the player image on the screen
    getPlayer().reset();

    currentLevel = Constants.GAME_STARTING_LEVEL;

    // Reset the managers
    ScoreManager.reset();

    nextLevel();
  }

  //
  public void nextLevel()
  {
    currentLevel++;

    whichProbability = ((currentLevel >= Constants.LEVEL_TO_USE_SPAWN_PROBABILITY_FOR_HIGHER_LEVELS) ? Constants.POWERUP_SPAWN_PROBABILITY_FOR_HIGHER_LEVELS : Constants.POWERUP_SPAWN_PROBABILITY_FOR_LOWER_LEVELS);

    // Clear the player shots when moving to the next level. This will clear the screen before the intro screen for the next level is displayed
    // This is more or less for eye candy to make a clean transition to the next level.
    clearPlayerShot();
    clearPowerups();
    clearEnemyShot();

    // Clear the player input control flags
    playerMovement.reset();
    //keyShield = false;
    fireShot = false;
    thrust = false;
    requestSuperShield = false;
    requestTheBomb = false;
    prepareTheBomb = false;
    executeTheBomb = false;
    playCountdownTimerSound = false;
    //launchStrongUFO = false;

    // TODO: Determine if the order in which the asteroids and player are placed causes the bug where the player ship hitting the asteroid when a new level begins.  
    ((PlayerEntity) getPlayer()).moveToHomePosition();
    getPlayer().setVelocity(0.0, 0.0);  // Possibly move this call into the method above.
    getPlayer().setVisible(true);

    initializeAsteroidsForCurrentLevel(currentLevel);
    ufoManager.reset(); // Possibly needed for the UFO that will appear in this game
  }

  private void initializeAsteroidsForCurrentLevel(int currentLevel)
  {
    int numberOfAsteroids = currentLevel + Constants.GAME_STARTING_NUMBER_OF_ASTEROIDS;

    if (numberOfAsteroids > Constants.GAME_MAX_NUMBER_ASTEROIDS_ON_SCREEN)
    {
      numberOfAsteroids = Constants.GAME_MAX_NUMBER_ASTEROIDS_ON_SCREEN;
    }

    for (int i = 0; i < numberOfAsteroids; i++)
    {
      createNewAsteroid();
    }
  }

  /*
   * Display methods for different screens
   */

  // Display for the introduction screen
  private void displayIntroductionMainScreen(Graphics2D g)
  {
    g.setFont(Constants.FONT_INTRO_SCREEN_MAIN_TITLE);
    boundsIntroductionTitleMsg = g.getFontMetrics().getStringBounds(Constants.INTRO_SCREEN_MAIN_TITLE_MSG, g);
    g.setColor(Color.BLACK);
    g.drawString(Constants.INTRO_SCREEN_MAIN_TITLE_MSG, (int) (((screenWidth - boundsIntroductionTitleMsg.getWidth()) / 2) + 2), 202);

    g.setColor(new Color(200, 30, 30));
    g.drawString(Constants.INTRO_SCREEN_MAIN_TITLE_MSG, (int) ((screenWidth - boundsIntroductionTitleMsg.getWidth()) / 2), 200);

    int x = 270, y = 15;
    g.setFont(Constants.FONT_INTRO_SCREEN_MAIN_CONTROLS);
    g.setColor(Color.YELLOW);
    g.drawString(Constants.INTRO_SCREEN_MAIN_CONTROLS_MSG, x, ++y * 20);
    g.drawString(Constants.INTRO_SCREEN_MAIN_ROTATE_MSG, x + 20, ++y * 20);
    g.drawString(Constants.INTRO_SCREEN_MAIN_THRUST_MSG, x + 20, ++y * 20);
    g.drawString(Constants.INTRO_SCREEN_MAIN_SHIELD_MSG, x + 20, ++y * 20);
    g.drawString(Constants.INTRO_SCREEN_MAIN_FIRE_MSG, x + 20, ++y * 20);

    g.setColor(Color.WHITE);
    boundsIntroductionPowerupsMsg = g.getFontMetrics().getStringBounds(Constants.INTRO_SCREEN_MAIN_POWERUPS_MSG, g);
    g.drawString(Constants.INTRO_SCREEN_MAIN_POWERUPS_MSG, (int) ((screenWidth - boundsIntroductionPowerupsMsg.getWidth()) / 2), 480);

    g.setFont(Constants.FONT_INTRO_SCREEN_MAIN_START);
    boundsIntroductionStateTitle = g.getFontMetrics().getStringBounds(Constants.INTRO_SCREEN_MAIN_START_MSG, g);
    g.setColor(Color.ORANGE);
    g.drawString(Constants.INTRO_SCREEN_MAIN_START_MSG, (int) ((screenWidth - boundsIntroductionStateTitle.getWidth()) / 2), 570);
  }

  private void displayGameStartScreen(Graphics2D g)
  {
    msgGameStartScreen.draw(g);

    //    g.setFont(Constants.FONT_GAME_START_SCREEN);
    //    boundsGameStartMsg = g.getFontMetrics().getStringBounds(Constants.MSG_GAME_START, g);
    //    g.setColor(Color.YELLOW);
    //    g.drawString(Constants.MSG_GAME_START, (int) ((screenWidth - boundsGameStartMsg.getWidth()) / 2), (int) ((screenHeight - boundsGameStartMsg.getHeight()) / 2));
  }

  private void displayNextLevelScreen(Graphics2D g)
  {
    g.setFont(Constants.FONT_NEXT_LEVEL_SCREEN);
    String msg = Constants.MSG_NEXT_LEVEL + GameUtility.lPadZero((currentLevel + 1), 2);
    boundsNextLevelMsg = g.getFontMetrics().getStringBounds(msg, g);
    g.setColor(Color.YELLOW);
    g.drawString(msg, (int) ((screenWidth - boundsNextLevelMsg.getWidth()) / 2), (int) ((screenHeight - boundsNextLevelMsg.getHeight()) / 2));
  }

  private void displayPlayerDeadScreen(Graphics2D g)
  {
    g.setFont(Constants.FONT_PLAYER_DEAD_SCREEN);
    boundsPlayerDeadMsg = g.getFontMetrics().getStringBounds(Constants.MSG_PLAYER_DEAD, g);
    g.setColor(Color.WHITE);
    g.drawString(Constants.MSG_PLAYER_DEAD, (int) ((screenWidth - boundsPlayerDeadMsg.getWidth()) / 2), (int) ((screenHeight - boundsPlayerDeadMsg.getHeight()) / 2));
  }

  private void displayGameOverScreen(Graphics2D g)
  {
    g.setFont(Constants.FONT_GAME_OVER_SCREEN);
    boundsGameOverMsg = g.getFontMetrics().getStringBounds(Constants.MSG_GAMEOVER_SCREEN_GAMEOVER, g);
    g.setColor(Color.WHITE);
    g.drawString(Constants.MSG_GAMEOVER_SCREEN_GAMEOVER, (int) ((screenWidth - boundsGameOverMsg.getWidth()) / 2), (int) ((screenHeight - boundsGameOverMsg.getHeight()) / 2));
  }
}
