package galacticwarreboot;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
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
import galacticwarreboot.entities.UFOStrongEntity;
import game.framework.GameEngine;
import game.framework.entities.Entity2D;
import game.framework.entities.EntityImage;
import game.framework.entities.text.StaticText;
import game.framework.interfaces.IRender;
import game.framework.utilities.GameEngineConstants;
import game.framework.utilities.GameUtility;
import game.framework.utilities.input.GameInputMovement;

public class Asteroids extends GameEngine
{
  /*
   * Class member variables
   */

  // Constants used for enemy entities

  /*
   * Class instance variables
   */

  // Variables used to control player entity

  private GameInputMovement             playerMovement;
  private boolean                       fireShot;
  private boolean                       keyShield;
  private boolean                       thrust;
  //private boolean             previousShieldState;
  private long                          lastUFOCollisionTime;

  private boolean                       gamePaused;

  // When paused is activated, stores the previous state from which the paused state was entered so the previous state can be restored after paused is exited. 
  private GameEngineConstants.GameState previousState;

  private boolean                       requestSuperShield;
  private boolean                       requestTheBomb;
  private boolean                       prepareTheBomb;
  private boolean                       executeTheBomb;

  private long                          prepareBombTimer;
  private long                          prepareBombTimerSecondCount;

  ImageObserver                         imageObserver;

  // Variables used for game
  private int                           currentLevel;

  // DEBUG/CHEAT vars
  private boolean                       increaseGunRequest;
  private boolean                       decreaseGunRequest;

  // Other vars. Not sure how to classify them.
  boolean                               playCountdownTimerSound;

  private StaticText                    msgHUDTheBombCoundown;

  Constants.IntroductionScreens         currentIntroductionScreen;

  /*
   *  Game state management variables
   */

  // Flags that keep track of the user requests for game state transitions
  private boolean                       requestGameStart;

  // Variables used to keep track of game event state transitions

  // Timers used to control when state transitions are to occur 
  private long                          timerPlayerDeadState = 0;
  private long                          timerGameStart       = 0;
  private long                          timerGameOver        = 0;
  private long                          timerNextLevel       = 0;

  /*
   * Managers
   */
  SoundManager                          soundManager;
  UFOEntityManager                      ufoManager;
  PowerupManager                        powerupManager;
  ScreenManager                         screenManager;

  /*
   * The code
   */
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
    // Debugging vars to be removed when the final game is released
    increaseGunRequest = false;
    decreaseGunRequest = false;

    // Initialize all manager. Note that the image manager is static so that it can be referenced from different classes in the game.
    soundManager = new SoundManager();
    powerupManager = new PowerupManager(imageObserver);
    ImageManager.loadAllImages(imageObserver);
    ufoManager = new UFOEntityManager();

    playerMovement = new GameInputMovement();

    fireShot = false;

    // Add the player entity
    PlayerEntity ship = new PlayerEntity(this.imageObserver);
    ship.setImage(ImageManager.getImage(Constants.FILENAME_SPACESHIP));
    ship.setRotationRate(0.0);
    ship.setFaceAngle(0);
    ship.setMoveAngle(ship.getFaceAngle() - 90);
    ship.defineHomePosition((screenWidth - ship.getWidth()) / 2, (screenHeight - ship.getHeight()) / 2);
    ship.setPosition((screenWidth - ship.getWidth()) / 2, (screenHeight - ship.getHeight()) / 2);
    ship.setVisible(false); // Only make ship visible when game is being played
    this.setNewPlayerEntity(ship);

    // NOTE: The screen manager should be instantiated after the player is created
    screenManager = new ScreenManager(imageObserver, screenWidth, screenHeight, (PlayerEntity) getPlayer());

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

    msgHUDTheBombCoundown = new StaticText("", Color.WHITE, Constants.FONT_GAME_PLAYING_HUD_LARGE1, screenWidth, screenHeight);

    lastUFOCollisionTime = System.currentTimeMillis();
    currentIntroductionScreen = Constants.IntroductionScreens.MAIN;
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
          ((PlayerEntity) getPlayer()).incrementByAmount(AttributeType.ATTRIBUTE_FIREPOWER, 1);
        }

        // For debugging purposes
        if (decreaseGunRequest)
        {
          decreaseGunRequest = false;
          ((PlayerEntity) getPlayer()).decrementByAmount(AttributeType.ATTRIBUTE_FIREPOWER, 1);
        }

        // If there is no more health remaining for the player ship, the player needs to be marked dead. 
        if (!((PlayerEntity) getPlayer()).isEquipped(Constants.AttributeType.ATTRIBUTE_HEALTH))
        {
          soundManager.playSound(SoundManager.SOUND_RESOURCE_PLAYER_SHIP_EXPLOSION);
          state = GameEngineConstants.GameState.PLAYER_DEAD;
          timerPlayerDeadState = System.currentTimeMillis();

          // TODO: Add pieces of players ship and
          double playerFaceAngle = getPlayer().getFaceAngle();
          double playerX = getPlayer().getPositionX();
          double playerY = getPlayer().getPositionY();

          //          double centerX = getPlayer().getCenterX();
          //          double centerY = getPlayer().getCenterY();

          int cockpitOffsetX = 12;
          int cockpitOffsetY = 2;
          int leftSideOffsetX = 6;
          int leftSideOffsetY = 9;
          int rightSideOffsetX = 32;
          int rightSideOffsetY = 9;
          int leftHullOffsetX = 9;
          int leftHullOffsetY = 16;
          int rightHullOffsetX = 26;
          int rightHullOffsetY = 16;
          int centerHullOffsetX = 16;
          int centerHullOffsetY = 16;
          int leftEngineOffsetX = 11;
          int leftEngineOffsetY = 30;
          int rightEngineOffsetX = 21;
          int rightEngineOffsetY = 30;
          this.addPowerup(createPlayerShipDebris(ImageManager.getImage(Constants.FILENAME_SPACESHIP_PART_COCKPIT), playerFaceAngle, playerX + cockpitOffsetX, playerY + cockpitOffsetY));
          this.addPowerup(createPlayerShipDebris(ImageManager.getImage(Constants.FILENAME_SPACESHIP_PART_LEFT_SIDE), playerFaceAngle, playerX + leftSideOffsetX, playerY + leftSideOffsetY));
          this.addPowerup(createPlayerShipDebris(ImageManager.getImage(Constants.FILENAME_SPACESHIP_PART_RIGHT_SIDE), playerFaceAngle, playerX + rightSideOffsetX, playerY + rightSideOffsetY));
          this.addPowerup(createPlayerShipDebris(ImageManager.getImage(Constants.FILENAME_SPACESHIP_PART_LEFT_HULL), playerFaceAngle, playerX + leftHullOffsetX, playerY + leftHullOffsetY));
          this.addPowerup(createPlayerShipDebris(ImageManager.getImage(Constants.FILENAME_SPACESHIP_PART_RIGHT_HULL), playerFaceAngle, playerX + rightHullOffsetX, playerY + rightHullOffsetY));
          this.addPowerup(createPlayerShipDebris(ImageManager.getImage(Constants.FILENAME_SPACESHIP_PART_CENTER_HULL), playerFaceAngle, playerX + centerHullOffsetX, playerY + centerHullOffsetY));
          this.addPowerup(createPlayerShipDebris(ImageManager.getImage(Constants.FILENAME_SPACESHIP_PART_LEFT_ENGINE), playerFaceAngle, playerX + leftEngineOffsetX, playerY + leftEngineOffsetY));
          this.addPowerup(createPlayerShipDebris(ImageManager.getImage(Constants.FILENAME_SPACESHIP_PART_RIGHT_ENGINE), playerFaceAngle, playerX + rightEngineOffsetX, playerY + rightEngineOffsetY));
        }

        if (getEnemies().isEmpty())
        {
          soundManager.playSound(SoundManager.SOUND_RESOURCE_NEXT_WAVE);
          state = GameEngineConstants.GameState.LEVEL_NEXT;
          timerNextLevel = System.currentTimeMillis();
        }

        UFOEntity ufoEntity = ufoManager.ufoShouldBeLaunched(currentLevel, imageObserver, screenWidth, screenHeight);
        if (ufoEntity != null)
        {
          addEnemy(ufoEntity);
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
          currentIntroductionScreen = Constants.IntroductionScreens.MAIN;
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

  //public EntityImage createPlayerShipDebris(Image image, double playerFaceAngle, double playerX, double playerY, double centerX, double centerY)
  public EntityImage createPlayerShipDebris(Image image, double playerFaceAngle, double playerX, double playerY)
  {
    EntityImage piece = new EntityImage(this.imageObserver, GameEngineConstants.EntityTypes.POWER_UP);

    //    double px = centerX;
    //    double py = centerY;
    //    double x1 = playerX;
    //    double y1 = playerY;
    //    
    //    double x2 = px + (x1-px) * Math.cos(playerFaceAngle*Math.PI/180.0)-(y1-py) * Math.sin(playerFaceAngle*Math.PI/180.0);
    //    double y2 = py + (x1-px) * Math.sin(playerFaceAngle*Math.PI/180.0)+(y1-py) * Math.cos(playerFaceAngle*Math.PI/180.0);

    piece.setImage(image);
    piece.setPosition(playerX, playerY);
    //    piece.setPosition(x2, y2);
    piece.setFaceAngle(playerFaceAngle);

    // Set rotation and direction angles of asteroid

    int moveAngle = GameUtility.random.nextInt((int) GameEngineConstants.DEGREES_IN_A_CIRCLE);
    piece.setMoveAngle(moveAngle);

    int rotationRate = GameUtility.random.nextInt(100) + 25;
    rotationRate = (GameUtility.random.nextBoolean() ? -rotationRate : rotationRate);
    piece.setRotationRate(rotationRate);

    // Set velocity based on movement direction
    double angle = piece.getMoveAngle() - 90;
    piece.setVelocity(GameUtility.calcAngleMoveX(angle), GameUtility.calcAngleMoveY(angle));
    piece.getVelocity().scaleThisVector((GameUtility.random.nextInt(50) + 25));

    // Set the lifespan
    piece.setLifespan((int) (GameEngineConstants.DEFAULT_UPDATE_RATE * (GameUtility.random.nextInt(10) + 10)));

    return piece;
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
      switch (((EnemyEntity) entity).getEnemyType())
      {
        case UFO:
        case UFO_STRONG:

          if (((EnemyEntity) entity).shouldFireShot() && getPlayer().isAlive())
          {
            soundManager.playSound(SoundManager.SOUND_RESOURCE_UFO_SHOOTING);
            this.addEnemyShot(ufoManager.stockUFOBullet(entity.getCenter(), getPlayer().getCenter(), Constants.EnemyTypes.UFO, this.imageObserver));
          }
          return;           // We do not want to warp the UFO so return

        case UFO_SHORTY:

          if (((EnemyEntity) entity).shouldFireShot() && getPlayer().isAlive())
          {
            soundManager.playSound(SoundManager.SOUND_RESOURCE_UFO_SHOOTING);
            this.addEnemyShot(ufoManager.stockUFOBullet(entity.getCenter(), getPlayer().getCenter(), Constants.EnemyTypes.UFO_SHORTY, this.imageObserver));
          }
          return;           // We do not want to warp the UFO so return

        default:
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

      // If the player is pressing the key to activate the shield and there is shield remaining, set the shields engaged flag
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
                // Cause damage to the player's shields
                ((PlayerEntity) entity1).decrementByAmount(Constants.AttributeType.ATTRIBUTE_SHIELD, damageAmount);

                // If the player had shields and after the damage, the value is zero, play the sound for loosing shields
                if (((PlayerEntity) getPlayer()).getValue(AttributeType.ATTRIBUTE_SHIELD) == 0)
                {
                  soundManager.playSound(SoundManager.SOUND_RESOURCE_PLAYER_SHIELDS_DEPLETED);
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
              // Cause damage to the player's shields
              ((PlayerEntity) entity1).decrementByAmount(Constants.AttributeType.ATTRIBUTE_SHIELD, damageAmount);

              // If the player had shields and after the damage, the value is zero, play the sound for loosing shields
              if (((PlayerEntity) getPlayer()).getValue(AttributeType.ATTRIBUTE_SHIELD) == 0)
              {
                soundManager.playSound(SoundManager.SOUND_RESOURCE_PLAYER_SHIELDS_DEPLETED);
              }
              // Otherwise reduce the player health by the damage amount, stop the player and reduce its fire power 
              else
              {
                soundManager.playSound(SoundManager.SOUND_RESOURCE_PLAYER_SHIELDS_HIT);
              }
            }
            else
            {
              // Shields are not engaged, play the sound for the respective asteroid to indicate the asteroid hitting the hull
              switch (((EnemyEntity) entity2).getEnemyType())
              {
                case ASTEROID_BIG:
                  soundManager.playSound(SoundManager.SOUND_RESOURCE_ASTEROID_INITIAL_EXPLOSION);
                  break;
                case ASTEROID_MEDIUM:
                case ASTEROID_SMALL:
                  soundManager.playSound(SoundManager.SOUND_RESOURCE_ASTEROID_SMALLER_EXPLOSION);
                  break;
                case ASTEROID_TINY:
                  soundManager.playSound(SoundManager.SOUND_RESOURCE_ASTEROID_TINY_EXPLOSION);
                  break;
                default:
              }

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
          //System.out.println("Player Shot of type " + ((PlayerShotEntity) entity1).getShotType() + " ID(" + ((PlayerShotEntity) entity1).getId() + ")" + " collided with an enemy.");
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

            // Play the respective sound of the asteroid breaking apart
            switch (((EnemyEntity) entity2).getEnemyType())
            {
              case ASTEROID_BIG:
                soundManager.playSound(SoundManager.SOUND_RESOURCE_ASTEROID_INITIAL_EXPLOSION);
                break;
              case ASTEROID_MEDIUM:
              case ASTEROID_SMALL:
                soundManager.playSound(SoundManager.SOUND_RESOURCE_ASTEROID_SMALLER_EXPLOSION);
                break;
              case ASTEROID_TINY:
                soundManager.playSound(SoundManager.SOUND_RESOURCE_ASTEROID_TINY_EXPLOSION);
                break;
              default:
            }

            //System.out.println(" asteroid of type " + ((EnemyEntity) entity2).getEnemyType());
            // Add the point value of the asteroid to the player's score 
            breakAsteroid((EntityImage) entity2);
            break;

          case UFO_STRONG:

            if (((UFOStrongEntity) entity2).getUfoHealth() > 0)
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

          case UFO_SHORTY:
          case UFO:
            //System.out.println(" UFO of type " + ((EnemyEntity) entity2).getEnemyType());

            if (!entity2.isAlive())
            {
              //soundManager.quickPlay(SoundManager.SOUND_FILE_UFO_EXPLOSION, true);
              soundManager.playSound(SoundManager.SOUND_RESOURCE_UFO_EXPLOSION);
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
            //soundManager.quickPlay(SoundManager.SOUND_FILE_PLAYER_WEAPON_ATTRIBUTE_EMPTY, true);
            soundManager.playSound(SoundManager.SOUND_RESOURCE_PLAYER_WEAPON_ATTRIBUTE_EMPTY);
          }
        }

        // This not only processes the request to launch The Bomb, but the prepareTheBomb flag only allows one bomb to go off at a given time
        if (requestTheBomb && !prepareTheBomb)
        {
          requestTheBomb = false;
          prepareTheBomb = true;
          playCountdownTimerSound = true;
          prepareBombTimerSecondCount = 3;
          prepareBombTimer = System.currentTimeMillis();
        }

        if (gamePaused)
        {
          previousState = this.state;
          state = GameEngineConstants.GameState.PAUSED;
          this.pauseGame();
        }

        break;

      case PAUSED:

        if (!gamePaused)
        {
          //state = GameEngineConstants.GameState.PLAYING;
          state = previousState;
          unpauseGame();
        }

        break;

      default:
    }
  }

  @Override
  public void userGamePreDraw(Graphics2D g)
  {
    // Draw the background. Note that the background image will be displayed for all game states
    g.drawImage(ImageManager.getImage(Constants.FILENAME_BACKGROUND), 0, 0, screenWidth - 1, screenHeight - 1, this.imageObserver);

    switch (this.state)
    {
      case INTRODUCTION:
        break;

      case GAME_START:

        screenManager.displayGameStartScreen(g);
        break;

      case PLAYING:

        break;

      default:
    }
  }

  @Override
  public void userGamePostDraw(Graphics2D g)
  {

    switch (this.state)
    {
      case INTRODUCTION:

        // Regardless of which screen the the active screen while in the Introduction state, display the game title at the top center of the screen.
        screenManager.drawGameTitle(g);

        switch (currentIntroductionScreen)
        {
          case INSTRUCTIONS:
            screenManager.displayInstructions(g);
            screenManager.displayIntroductionFooter(g);
            break;

          case POWERUPS:
            screenManager.displayIntroductionPowerupsScreen(g);
            screenManager.displayIntroductionFooter(g);
            break;

          case POWERUP_DETAILS:
            screenManager.displayIntroductionGameDetailsScreen(g);
            screenManager.displayIntroductionFooter(g);
            break;

          case CREDITS:
            screenManager.displayCreditsScreen(g);
            break;

          case ENEMIES:
            screenManager.displayIntroductionEnemiesScreen(g);
            screenManager.displayIntroductionFooter(g);
            break;

          default:
            screenManager.displayIntroductionMainScreen(g);
        }
        break;

      case GAME_START:
        break;

      case PAUSED:
        screenManager.displayPausedScreen(g);

      case PLAYING:
        screenManager.displayHUD(g, currentLevel);

        // TODO: This might go into a separate method since it might not be part of the HUD 
        if (prepareTheBomb)
        {
          if (playCountdownTimerSound)
          {
            switch ((int) prepareBombTimerSecondCount)
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

          msgHUDTheBombCoundown.setText(Constants.DOT_DOT_DOT + prepareBombTimerSecondCount + Constants.DOT_DOT_DOT);
          msgHUDTheBombCoundown.draw(g);
        }

        displayDebugInfo(g);
        break;

      case PLAYER_DEAD:
        screenManager.displayPlayerDeadScreen(g);
        break;

      case GAMEOVER:
        screenManager.displayGameOverScreen(g);
        break;

      case LEVEL_NEXT:
        screenManager.displayNextLevelScreen(g, currentLevel);
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

        menuFlowForIntroductionScreen(keyCode);

        // Start the game
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

        // For debugging purposes
        if (keyCode == KeyEvent.VK_A)
        {
          increaseGunRequest = true;
        }

        // For debugging purposes
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

        if (keyCode == KeyEvent.VK_Z)
        {
          requestSuperShield = true;
        }

        // Only accept The Bomb request if the player is equipped with at least one bomb and there is no previous bomb request pending
        if (keyCode == KeyEvent.VK_X)
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
            soundManager.playSound(SoundManager.SOUND_RESOURCE_PLAYER_WEAPON_ATTRIBUTE_EMPTY);
          }
        }

        if (keyCode == KeyEvent.VK_P)
        {
          gamePaused = true;
        }

        break;

      case PAUSED:

        if (keyCode == KeyEvent.VK_P)
        {
          gamePaused = false;
        }

        break;

      default:
    }
  }

  @Override
  public void gameKeyTyped(int keyCode)
  {}

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
    asteroid.setImage(ImageManager.getRandomBigAsteroidImage());

    // Set to a random position on the screen and prevent asteroids from
    // starting on the players position
    double x = GameUtility.random.nextInt(screenWidth - Constants.ASTEROID_DIMENSIONS);
    double y = GameUtility.random.nextInt(screenHeight - Constants.ASTEROID_DIMENSIONS);
    asteroid.setPosition(x, y);
    nudgeAsteroid(asteroid);

    // Set rotation and direction angles of asteroid
    int faceAngle = GameUtility.random.nextInt((int) GameEngineConstants.DEGREES_IN_A_CIRCLE);
    int moveAngle = GameUtility.random.nextInt((int) GameEngineConstants.DEGREES_IN_A_CIRCLE);
    asteroid.setFaceAngle(faceAngle);
    asteroid.setMoveAngle(moveAngle);

    // Assign a random rotation rate and determine the direction (CW or CCW)
    double rotationRate = GameUtility.random.nextInt(Constants.ASTEROID_MAX_ROTATION_RATE) + Constants.ASTEROID_MIN_ROTATION_RATE;
    rotationRate = (GameUtility.random.nextBoolean() ? -rotationRate : rotationRate);
    asteroid.setRotationRate(rotationRate);

    // Set velocity based on movement direction
    double angle = asteroid.getMoveAngle() - 90;
    double velx = GameUtility.calcAngleMoveX(angle);
    double vely = GameUtility.calcAngleMoveY(angle);
    asteroid.setVelocity(velx, vely);
    asteroid.getVelocity().scaleThisVector(Constants.BIG_ASTEROID_VELOCITY_SCALE[GameUtility.random.nextInt(Constants.BIG_ASTEROID_VELOCITY_SCALE.length)]);

    // Add the new asteroid to the sprite list

    if (asteroid.getImage() != null)
    {
      getEnemies().add(asteroid);
    }
    else
    {
      System.out.println("createNewAsteroid::Asteroid Image is null:");
      System.out.println(asteroid);
    }
  }

  // Fire a bullet from the ship's position and orientation
  // TODO: Should this be moved to the player entity?
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

      // TODO: This type of shot might be removed so the player does not have too much of an advantage 
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
    double svx = GameUtility.calcAngleMoveX(angle) * Constants.PLAYER_BULLET_SPEED + getPlayer().getVelocityX();
    double svy = GameUtility.calcAngleMoveY(angle) * Constants.PLAYER_BULLET_SPEED + getPlayer().getVelocityY();
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
      EnemyEntity enemy = (EnemyEntity) newEnemies.remove();

      if (enemy.getImage() != null)
      {
        //getEnemies().add(newEnemies.remove());
        getEnemies().add(enemy);
      }
      else
      {
        System.out.println("executeTheBomb::Enemy Image was Null:");
        System.out.println(enemy);
      }
    }
    newEnemies.clear();
  }

  private void stockSuperShield()
  {
    double faceAngle = getPlayer().getFaceAngle();

    for (int i = 0; i < Constants.PLAYER_NUMBER_SUPER_SHIELD_BALLS; i++)
    {
      SuperShieldEntity ss = new SuperShieldEntity(this.imageObserver);
      ss.setImage(ImageManager.getImage(Constants.FILENAME_SUPER_SHIELD));
      ss.setFaceAngle(faceAngle);
      ss.setMoveAngle(faceAngle - 90);

      // Set the bullet's velocity
      double angle = ss.getMoveAngle();
      double svx = GameUtility.calcAngleMoveX(angle) * Constants.PLAYER_SUPER_SHIELD_SPEED + getPlayer().getVelocityX();
      double svy = GameUtility.calcAngleMoveY(angle) * Constants.PLAYER_SUPER_SHIELD_SPEED + getPlayer().getVelocityY();

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

    bullet.setImage(ImageManager.getImage(Constants.FILENAME_PLASMA_SHOT));
    bullet.setFaceAngle(faceAngle);
    bullet.setMoveAngle(faceAngle - 90);

    // Set the bullet's velocity
    double angle = bullet.getMoveAngle();
    double svx = GameUtility.calcAngleMoveX(angle) * Constants.PLAYER_BULLET_SPEED + getPlayer().getVelocityX();
    double svy = GameUtility.calcAngleMoveY(angle) * Constants.PLAYER_BULLET_SPEED + getPlayer().getVelocityY();

    bullet.setVelocity(svx, svy);

    // Set the bullet's starting position
    double x = getPlayer().getCenterX() - bullet.getWidth() / 2;
    double y = getPlayer().getCenterY() - bullet.getHeight() / 2;
    bullet.setPosition(x, y);

    // Initialize the life span and life age
    bullet.setLifespan((int) (GameEngineConstants.DEFAULT_UPDATE_RATE * Constants.PLAYER_BULLET_LIFE_SPAN_IN_SECS));
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
        // TODO: Move these sounds to the collision method
        //soundManager.playSound(SoundManager.SOUND_RESOURCE_ASTEROID_INITIAL_EXPLOSION);
        // Spawn medium asteroids over the old one
        getEnemies().add(spawnSmallerAsteroid(entity));
        getEnemies().add(spawnSmallerAsteroid(entity));
        getEnemies().add(spawnSmallerAsteroid(entity));

        // TODO: Draw big explosion
        break;

      case ASTEROID_MEDIUM:
        // TODO: Move these sounds to the collision method
        //soundManager.playSound(SoundManager.SOUND_RESOURCE_ASTEROID_SMALLER_EXPLOSION);
        // Spawn small asteroids over the old one
        getEnemies().add(spawnSmallerAsteroid(entity));
        getEnemies().add(spawnSmallerAsteroid(entity));
        getEnemies().add(spawnSmallerAsteroid(entity));

        // TODO: Draw small explosion
        break;

      case ASTEROID_SMALL:
        // TODO: Move these sounds to the collision method
        //soundManager.playSound(SoundManager.SOUND_RESOURCE_ASTEROID_SMALLER_EXPLOSION);
        // Spawn tiny asteroids over the old one
        getEnemies().add(spawnSmallerAsteroid(entity));
        getEnemies().add(spawnSmallerAsteroid(entity));
        getEnemies().add(spawnSmallerAsteroid(entity));
        //addPowerup(powerupManager.spawnPowerup(entity.getPosition(), ((PlayerEntity)getPlayer()), currentLevel));

        // TODO: Draw small explosion
        break;

      case ASTEROID_TINY:
        // TODO: Move these sounds to the collision method
        //soundManager.playSound(SoundManager.SOUND_RESOURCE_ASTEROID_TINY_EXPLOSION);
        // Spawn a random powerup
        addPowerup(powerupManager.spawnPowerup(entity.getPosition(), ((PlayerEntity) getPlayer()), currentLevel));

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
        //addPowerup(powerupManager.spawnPowerup(entity.getPosition(), ((PlayerEntity)getPlayer()), currentLevel));

        // TODO: Draw small explosion
        break;

      case ASTEROID_TINY:

        // Spawn a random powerup
        addPowerup(powerupManager.spawnPowerup(entity.getPosition(), ((PlayerEntity) getPlayer()), currentLevel));

        // TODO: Draw small explosion
        break;

      default:
    }
  }

  /*
   * Spawn a smaller asteroid based on passed sprite
   */
  // TODO: Should this go into an AsteroidsManager?
  // TODO: Should I create a class for each Asteroid LARGE, MEDIUM, SMALL and TINY to make better use of OO design practices?
  private AsteroidEntity spawnSmallerAsteroid(EntityImage entity)
  {
    // Create a new asteroid Entity
    AsteroidEntity asteroid = new AsteroidEntity(this.imageObserver);

    // Set rotation and direction angles
    asteroid.setFaceAngle(GameUtility.random.nextInt((int) GameEngineConstants.DEGREES_IN_A_CIRCLE));
    asteroid.setMoveAngle(GameUtility.random.nextInt((int) GameEngineConstants.DEGREES_IN_A_CIRCLE));
    double rotationRate = GameUtility.random.nextInt(Constants.ASTEROID_MAX_ROTATION_RATE) + Constants.ASTEROID_MIN_ROTATION_RATE;
    rotationRate = (GameUtility.random.nextBoolean() ? -rotationRate : rotationRate);
    asteroid.setRotationRate(rotationRate);

    // Set velocity based on movement direction
    double angle = asteroid.getMoveAngle() - 90;
    double velx = GameUtility.calcAngleMoveX(angle);
    double vely = GameUtility.calcAngleMoveY(angle);
    asteroid.setVelocity(velx, vely);

    // Set some size-specific properties
    switch (((EnemyEntity) entity).getEnemyType())
    {
      case ASTEROID_BIG:
        asteroid.setEnemyType(Constants.EnemyTypes.ASTEROID_MEDIUM);

        // Pick one of the random asteroid images
        asteroid.setImage(ImageManager.getRandomMediumAsteroidImage());

        asteroid.getVelocity().scaleThisVector(Constants.MEDIUM_ASTEROID_VELOCITY_SCALE[GameUtility.random.nextInt(Constants.MEDIUM_ASTEROID_VELOCITY_SCALE.length)]);
        asteroid.setPointValue(Constants.SCORE_MEDIUM_ASTEROIDS);
        break;

      case ASTEROID_MEDIUM:
        asteroid.setEnemyType(Constants.EnemyTypes.ASTEROID_SMALL);

        // Pick one of the random asteroid images
        asteroid.setImage(ImageManager.getRandomSmallAsteroidImage());

        asteroid.getVelocity().scaleThisVector(Constants.SMALL_ASTEROID_VELOCITY_SCALE[GameUtility.random.nextInt(Constants.SMALL_ASTEROID_VELOCITY_SCALE.length)]);
        asteroid.setPointValue(Constants.SCORE_SMALL_ASTEROIDS);
        break;

      case ASTEROID_SMALL:
        asteroid.setEnemyType(Constants.EnemyTypes.ASTEROID_TINY);

        // Pick one of the random asteroid images
        asteroid.setImage(ImageManager.getRandomTinyAsteroidImage());

        asteroid.getVelocity().scaleThisVector(Constants.TINY_ASTEROID_VELOCITY_SCALE[GameUtility.random.nextInt(Constants.TINY_ASTEROID_VELOCITY_SCALE.length)]);
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

    if (asteroid.getImage() == null)
    {
      System.out.println("spawnSmallerAsteroid:: smaller asteroid's image is null:");
      System.out.println(asteroid);
    }

    // Add the new asteroid to the sprite list
    //getEnemies().add(asteroid);
    return asteroid;
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
    velx += GameUtility.calcAngleMoveX(ship.getMoveAngle()) * ((PlayerEntity) getPlayer()).getValue(AttributeType.ATTRIBUTE_THRUST);
    if (velx < Constants.SHIP_MIN_VELOCITY)
      velx = Constants.SHIP_MIN_VELOCITY;
    else if (velx > Constants.SHIP_MAX_VELOCITY)
      velx = Constants.SHIP_MAX_VELOCITY;
    double vely = ship.getVelocityY();
    vely += GameUtility.calcAngleMoveY(ship.getMoveAngle()) * ((PlayerEntity) getPlayer()).getValue(AttributeType.ATTRIBUTE_THRUST);
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
    gamePaused = false;
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
    powerupManager.updateSpawnProbability(currentLevel);

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

    // TODO: Determine if the order in which the asteroids and player are placed causes the bug where the player ship hitting the asteroid when a new level begins.  
    ((PlayerEntity) getPlayer()).moveToHomePosition();
    getPlayer().setVelocity(0.0, 0.0);  // Possibly move this call into the method above.
    getPlayer().setVisible(true);

    initializeAsteroidsForCurrentLevel(currentLevel);
    ufoManager.reset(); // Possibly needed for the UFO that will appear in this game
  }

  // TODO: Should this go into an AsteroidsManager?
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

  private void menuFlowForIntroductionScreen(int keyCode)
  {
    switch (currentIntroductionScreen)
    {
      case MAIN:

        if (keyCode == KeyEvent.VK_SPACE)
        {
          currentIntroductionScreen = Constants.IntroductionScreens.INSTRUCTIONS;
        }

        if (keyCode == KeyEvent.VK_C)
        {
          currentIntroductionScreen = Constants.IntroductionScreens.CREDITS;
        }

        if (keyCode == KeyEvent.VK_ESCAPE)
        {
          // TODO: Add request here to exit game.
        }
        break;

      case INSTRUCTIONS:

        if (keyCode == KeyEvent.VK_SPACE)
        {
          currentIntroductionScreen = Constants.IntroductionScreens.POWERUPS;
        }

        if (keyCode == KeyEvent.VK_ESCAPE)
        {
          currentIntroductionScreen = Constants.IntroductionScreens.MAIN;
        }
        break;

      case POWERUPS:

        if (keyCode == KeyEvent.VK_SPACE)
        {
          currentIntroductionScreen = Constants.IntroductionScreens.ENEMIES;
        }

        if (keyCode == KeyEvent.VK_ESCAPE)
        {
          currentIntroductionScreen = Constants.IntroductionScreens.INSTRUCTIONS;
        }
        break;

      case ENEMIES:

        if (keyCode == KeyEvent.VK_SPACE)
        {
          currentIntroductionScreen = Constants.IntroductionScreens.POWERUP_DETAILS;
        }

        if (keyCode == KeyEvent.VK_ESCAPE)
        {
          currentIntroductionScreen = Constants.IntroductionScreens.POWERUPS;
        }
        break;

      case POWERUP_DETAILS:

        if (keyCode == KeyEvent.VK_SPACE)
        {
          currentIntroductionScreen = Constants.IntroductionScreens.MAIN;
        }

        if (keyCode == KeyEvent.VK_ESCAPE)
        {
          currentIntroductionScreen = Constants.IntroductionScreens.ENEMIES;
        }
        break;

      case CREDITS:

        if (keyCode == KeyEvent.VK_ESCAPE)
        {
          currentIntroductionScreen = Constants.IntroductionScreens.MAIN;
        }
        break;

      default:
    }

    //System.out.println("Current Introduction Screen:" + currentIntroductionScreen.toString());
  }

  public void displayDebugInfo(Graphics2D g)
  {
    // For debugging purposes
    int line = 300;

    if (displayDebugInfo)
    {
      g.setFont(Constants.FONT_DEBUG);
      g.setColor(Color.WHITE);

      g.drawString(Constants.DEBUG_MSG_THRUST_ON + thrust, 560, line);
      line += 16;
      g.drawString(Constants.DEBUG_MSG_SHIELD + keyShield, 560, line);
      line += 16;
      g.drawString(Constants.DEBUG_MSG_UFO_PROB + ufoManager.getProbability(), 560, line);
      line += 16;
      g.drawString(Constants.DEBUG_MSG_THRUST_VALUE + ((PlayerEntity) getPlayer()).getValue(AttributeType.ATTRIBUTE_THRUST), 560, line);
      line += 16;
      g.drawString(Constants.DEBUG_MSG_SPAWN_PROBABILITY + powerupManager.getProbability(), 560, line);
      line += 16;
      g.drawString(Constants.DEBUG_MSG_PLAYER_HEALTH_CAPACITY + ((PlayerEntity) getPlayer()).getLimit(Constants.AttributeType.ATTRIBUTE_HEALTH), 560, line);
      line += 16;
      g.drawString(Constants.DEBUG_MSG_PLAYER_SHIELD_CAPACITY + ((PlayerEntity) getPlayer()).getLimit(Constants.AttributeType.ATTRIBUTE_SHIELD), 560, line);
      line += 16;
    }
  }

}
