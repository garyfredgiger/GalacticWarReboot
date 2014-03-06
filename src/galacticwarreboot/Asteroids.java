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
import galacticwarreboot.entities.UFOStrongEntity;
import game.framework.GameEngine;
import game.framework.entities.Entity2D;
import game.framework.entities.EntityImage;
import game.framework.entities.StaticImage;
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

  private GameInputMovement   playerMovement;
  private boolean             fireShot;
  private boolean             keyShield;
  private boolean             thrust;
  //private boolean             previousShieldState;
  private long                lastUFOCollisionTime;

  private boolean             gamePaused;

  // When paused is activated, stores the previous state from which the paused state was entered so the previous state can be restored after paused is exited. 
  private GameEngineConstants.GameState previousState;
  
  private boolean             requestSuperShield;
  private boolean             requestTheBomb;
  private boolean             prepareTheBomb;
  private boolean             executeTheBomb;

  private long                prepareBombTimer;
  private long                prepareBombTimerSecondCount;

  ImageObserver               imageObserver;

  // Variables used for game
  private int                 currentLevel;

  // DEBUG/CHEAT vars
  private boolean             increaseGunRequest;
  private boolean             decreaseGunRequest;

  // Other vars. Not sure how to classify them.
  boolean                     playCountdownTimerSound;

  Constants.IntroductionScreens currentIntroductionScreen;
  
  /*
   *  Game state management variables
   */

  // Flags that keep track of the user requests for game state transitions
  private boolean             requestGameStart;

  // Variables used to keep track of game event state transitions

  // Timers used to control when state transitions are to occur 
  private long                timerPlayerDeadState           = 0;
  private long                timerGameStart                 = 0;
  private long                timerGameOver                  = 0;
  private long                timerNextLevel                 = 0;

  /*
   * Bounding boxes used for messages displayed to the screen
   */
  private Rectangle2D         boundsIntroductionTitleMsg;
  private Rectangle2D         boundsIntroductionStateTitle;
  private Rectangle2D         boundsIntroductionPowerupsMsg;

  private StaticText          msgGameStartScreen;
  private StaticText          msgGameOverScreen;
  private StaticText          msgNextLevelScreen;
  private StaticText          msgPlayerDeadScreen;
  private StaticText          msgHUDHealthBar;
  private StaticText          msgHUDShieldBar;
  private StaticText          msgHUDFirepower;
  private StaticText          msgHUDCurrentLevel;
  private StaticText          msgHUDScore;
  private StaticText          msgHUDNumberOfSuperShields;
  private StaticText          msgHUDNumberOfTheBombs;
  private StaticText          msgHUDTheBombCoundown;
  private StaticText          msgPaused;
  private StaticText          msgGameTitle;
  private StaticText          msgPowerupScreenTitle;
  private StaticText          msgInstructionsScreenTitle;
  private StaticText          msgInstructionsPlayerControls;
  private StaticText          msgPowerupDetailsScreen;
  private StaticText          msgEnemiesTitle;
  private StaticText          msgCreditsTitle;
  
  private StaticText          msgEnemiesAsteroidLabel;
  
  /*
   * Static Image Variables
   */
//  StaticImage imagePowerup
  StaticImage imgAsteroidLargeIntroductionScreen;
  StaticImage imgAsteroidMediumIntroductionScreen;
  StaticImage imgAsteroidSmallIntroductionScreen;
  StaticImage imgAsteroidTinyIntroductionScreen;

  /*
   * Managers
   */
  SoundManager                soundManager; 
  UFOEntityManager            ufoManager;
  PowerupManager              powerupManager;

  // Variables used for creating the enemies screen in the game introduction
  int startingPositionXOfAsteroidListing;
  int bigAsteroidWidth;
  int bufferBetweenAsteroid = 64; // Predefined number

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

    lastUFOCollisionTime = System.currentTimeMillis();

    msgGameStartScreen = new StaticText(Constants.MSG_GAME_START, Color.YELLOW, Constants.FONT_GAME_START_SCREEN, screenWidth, screenHeight);
    msgGameOverScreen = new StaticText(Constants.MSG_GAMEOVER_SCREEN_GAMEOVER, Color.YELLOW, Constants.FONT_GAME_OVER_SCREEN, screenWidth, screenHeight);
    msgNextLevelScreen = new StaticText("", Color.YELLOW, Constants.FONT_GAME_OVER_SCREEN, screenWidth, screenHeight);
    msgPlayerDeadScreen = new StaticText(Constants.MSG_PLAYER_DEAD, Color.WHITE, Constants.FONT_PLAYER_DEAD_SCREEN, screenWidth, screenHeight);
    msgHUDHealthBar = new StaticText(Constants.MSG_GAME_PLAYING_HEALTH, (screenWidth - 284), 30, Color.WHITE, Constants.FONT_GAME_PLAYING_HUD_SMALL, screenWidth, screenHeight);
    msgHUDShieldBar = new StaticText(Constants.MSG_GAME_PLAYING_SHIELD, (screenWidth - 282), 47, Color.WHITE, Constants.FONT_GAME_PLAYING_HUD_SMALL, screenWidth, screenHeight);
    msgHUDFirepower = new StaticText(Constants.MSG_GAME_PLAYING_FIREPOWER, 20, 40, Color.WHITE, Constants.FONT_GAME_PLAYING_HUD_MEDIUM, screenWidth, screenHeight);
    msgHUDCurrentLevel = new StaticText("", 20, 65, Color.WHITE, Constants.FONT_GAME_PLAYING_HUD_MEDIUM, screenWidth, screenHeight);
    msgHUDNumberOfSuperShields = new StaticText("", 50, 103, Color.WHITE, Constants.FONT_GAME_PLAYING_HUD_MEDIUM, screenWidth, screenHeight);
    msgHUDNumberOfTheBombs = new StaticText("", 50, 133, Color.WHITE, Constants.FONT_GAME_PLAYING_HUD_MEDIUM, screenWidth, screenHeight);
    msgHUDTheBombCoundown = new StaticText("", Color.WHITE, Constants.FONT_GAME_PLAYING_HUD_LARGE1, screenWidth, screenHeight);
    msgPaused = new StaticText(Constants.MSG_GAME_PAUSED, Color.WHITE, Constants.FONT_PAUSED_SCREEN, screenWidth, screenHeight);
    
    msgGameTitle = new StaticText(Constants.INTRO_SCREEN_MAIN_TITLE_MSG, -1, 64, Color.RED, Constants.FONT_INTRO_SCREEN_MAIN_TITLE, screenWidth, screenHeight);
    msgGameTitle.centerHorizontally();

    // NOTE: We will center the score horizontally, this is why there is a -1 for the x value
    msgHUDScore = new StaticText(Constants.MSG_GAME_PLAYING_SCORE, -1, 40, Color.WHITE, Constants.FONT_GAME_PLAYING_HUD_MEDIUM, screenWidth, screenHeight);
    msgHUDScore.setAdditionalOffsetHorizontal(-40);
    msgHUDScore.centerHorizontally();

    msgPowerupScreenTitle = new StaticText(Constants.MSG_POWERUPS_POWERUP_TITLE, -1, 150, Color.WHITE, Constants.FONT_INTRO_POWERUPS_SCREEN_LARGE, screenWidth, screenHeight);
    msgPowerupScreenTitle.centerHorizontally();

    msgInstructionsScreenTitle = new StaticText(Constants.MSG_INSTRUCTIONS_TITLE, -1, 150, Color.WHITE, Constants.FONT_INTRO_INSTRUCTIONS_SCREEN_LARGE, screenWidth, screenHeight);
    msgInstructionsScreenTitle.centerHorizontally();

    msgInstructionsPlayerControls = new StaticText(Constants.MSG_INSTRUCTIONS_CONTROLS, -1, 208, Color.WHITE, Constants.FONT_INTRO_INSTRUCTIONS_SCREEN_MEDIUM, screenWidth, screenHeight);
    msgInstructionsPlayerControls.centerHorizontally();

    msgPowerupDetailsScreen = new StaticText(Constants.MSG_POWERUP_DETAILS_TITLE, -1, 150, Color.WHITE, Constants.FONT_INTRO_POWERUPS_SCREEN_LARGE, screenWidth, screenHeight);
    msgPowerupDetailsScreen.centerHorizontally();

    msgEnemiesTitle = new StaticText(Constants.MSG_ENEMIES_TITLE, -1, 150, Color.WHITE, Constants.FONT_INTRO_ENEMIES_SCREEN_LARGE, screenWidth, screenHeight);
    msgEnemiesTitle.centerHorizontally();

    msgCreditsTitle = new StaticText(Constants.MSG_CREDITS_TITLE, -1, 150, Color.WHITE, Constants.FONT_INTRO_CREDITS_SCREEN_LARGE, screenWidth, screenHeight);
    msgCreditsTitle.centerHorizontally();

    currentIntroductionScreen = Constants.IntroductionScreens.MAIN;
    
    msgEnemiesAsteroidLabel = new StaticText("The Asteroids", -1, 192, Color.WHITE, Constants.FONT_INTRO_ENEMIES_SCREEN_REGULAR, screenWidth, screenHeight);
    msgEnemiesAsteroidLabel.centerHorizontally();
    
    setupIntroductionEnemiesScreen();
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
      if (((EnemyEntity) entity).shouldFireShot() && getPlayer().isAlive())
      {
        switch(((EnemyEntity) entity).getEnemyType())
        {
          case UFO:
          case UFO_STRONG:
            soundManager.playSound(SoundManager.SOUND_RESOURCE_UFO_SHOOTING);
            this.addEnemyShot(ufoManager.stockUFOBullet(entity.getCenter(), getPlayer().getCenter(), Constants.EnemyTypes.UFO, this.imageObserver));
            return;           // We do not want to warp the UFO so return
            
          case UFO_SHORTY:
            soundManager.playSound(SoundManager.SOUND_RESOURCE_UFO_SHOOTING);
            this.addEnemyShot(ufoManager.stockUFOBullet(entity.getCenter(), getPlayer().getCenter(), Constants.EnemyTypes.UFO_SHORTY, this.imageObserver));
            return;           // We do not want to warp the UFO so return

          default:
        }
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
              switch(((EnemyEntity) entity2).getEnemyType())
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
            switch(((EnemyEntity) entity2).getEnemyType())
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
    switch (this.state)
    {
      case INTRODUCTION:
        // Regardless of which screen the the active screen while in the Introduction state, display the game title at the top center of the screen.
        msgGameTitle.draw(g);
        
        switch(currentIntroductionScreen)
        {
          case INSTRUCTIONS:
            displayInstructions(g);
            displayIntroductionFooter(g);
            break;
            
          case POWERUPS:
            displayPowerups(g);
            displayIntroductionFooter(g);
            break;

          case POWERUP_DETAILS:
            displayPowerupDetailsScreen(g);
            displayIntroductionFooter(g);
            break;
          
          case CREDITS:
            displayCreditsScreen(g);
            break;
            
          case ENEMIES:
            displayEnemiesScreen(g);
            displayIntroductionFooter(g);
            break;
            
          default:
            displayIntroductionMainScreen(g);
        }
        break;

      case GAME_START:
        break;

      case PAUSED:
        msgPaused.draw(g);
        
      case PLAYING:
        displayHUD(g);
        displayDebugInfo(g);
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

        menuFlowTwoForIntroductionScreen(keyCode);

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
        addPowerup(powerupManager.spawnPowerup(entity.getPosition(), ((PlayerEntity)getPlayer()), currentLevel));

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
        addPowerup(powerupManager.spawnPowerup(entity.getPosition(), ((PlayerEntity)getPlayer()), currentLevel));

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

  // Display for the introduction screen
  private void displayIntroductionMainScreen(Graphics2D g)
  {
    g.setColor(Color.WHITE);
    g.setFont(Constants.FONT_INTRO_SCREEN_MAIN_START);

    boundsIntroductionStateTitle = g.getFontMetrics().getStringBounds("Press 'Space' for Game Instructions", g);
    g.drawString("Press 'Space' for Game Instructions", (int) ((screenWidth - boundsIntroductionStateTitle.getWidth()) / 2), 320);

    boundsIntroductionPowerupsMsg = g.getFontMetrics().getStringBounds("Press 'C' for Game Credits", g);
    g.drawString("Press 'C' for Game Credits", (int) ((screenWidth - boundsIntroductionPowerupsMsg.getWidth()) / 2), 352);

    boundsIntroductionStateTitle = g.getFontMetrics().getStringBounds("Press 'Enter' to begin game", g);
    g.drawString("Press 'Enter' to begin game", (int) ((screenWidth - boundsIntroductionStateTitle.getWidth()) / 2), 384);
  }

  private void displayInstructions(Graphics2D g)
  {
    msgInstructionsScreenTitle.draw(g);

    // Vars used for thruster power-up
    int controlUpperLeftCornerPositionX = 192;
    int controlUpperLeftCornerPositionY = 240;

    int commandUpperLeftCornerPositionX = 448;
    int commandUpperLeftCornerPositionY = 240;

    msgInstructionsPlayerControls.draw(g);

    // Display the ship controls
    g.setFont(Constants.FONT_INTRO_INSTRUCTIONS_SCREEN_REGULAR);
    //g.setFont(Constants.FONT_INTRO_INSTRUCTIONS_SCREEN_MEDIUM);
    g.setColor(Color.ORANGE);

    g.drawString(Constants.MSG_INSTRUCTIONS_ROTATE_CONTROL, controlUpperLeftCornerPositionX, controlUpperLeftCornerPositionY);
    controlUpperLeftCornerPositionY += 24;
    
    g.drawString(Constants.MSG_INSTRUCTIONS_THRUST_CONTROL, controlUpperLeftCornerPositionX, controlUpperLeftCornerPositionY);
    controlUpperLeftCornerPositionY += 24;
    
    g.drawString(Constants.MSG_INSTRUCTIONS_SHIELDS_CONTROL, controlUpperLeftCornerPositionX, controlUpperLeftCornerPositionY);
    controlUpperLeftCornerPositionY += 24;
    
    g.drawString(Constants.MSG_INSTRUCTIONS_FIRE_CONTROL, controlUpperLeftCornerPositionX, controlUpperLeftCornerPositionY);
    controlUpperLeftCornerPositionY += 24;
        
    g.drawString(Constants.MSG_INSTRUCTIONS_SUPER_SHIELDS_CONTROL, controlUpperLeftCornerPositionX, controlUpperLeftCornerPositionY);
    controlUpperLeftCornerPositionY += 24;
  
    g.drawString(Constants.MSG_INSTRUCTIONS_THE_BOMB_CONTROL, controlUpperLeftCornerPositionX, controlUpperLeftCornerPositionY);

    // Display the corresponding commands
    g.drawString(Constants.MSG_INSTRUCTIONS_ROTATE_COMMAND, commandUpperLeftCornerPositionX, commandUpperLeftCornerPositionY);
    commandUpperLeftCornerPositionY += 24;
    
    g.drawString(Constants.MSG_INSTRUCTIONS_THRUST_COMMAND, commandUpperLeftCornerPositionX, commandUpperLeftCornerPositionY);
    commandUpperLeftCornerPositionY += 24;
    
    g.drawString(Constants.MSG_INSTRUCTIONS_SHIELDS_COMMAND, commandUpperLeftCornerPositionX, commandUpperLeftCornerPositionY);
    commandUpperLeftCornerPositionY += 24;
    
    g.drawString(Constants.MSG_INSTRUCTIONS_FIRE_COMMAND, commandUpperLeftCornerPositionX, commandUpperLeftCornerPositionY);
    commandUpperLeftCornerPositionY += 24;
        
    g.drawString(Constants.MSG_INSTRUCTIONS_SUPER_SHIELDS_COMMAND, commandUpperLeftCornerPositionX, commandUpperLeftCornerPositionY);
    commandUpperLeftCornerPositionY += 24;
  
    g.drawString(Constants.MSG_INSTRUCTIONS_THE_BOMB_COMMAND, commandUpperLeftCornerPositionX, commandUpperLeftCornerPositionY);    
  }

  private void displayPowerups(Graphics2D g)
  {
    msgPowerupScreenTitle.draw(g);

    int regularBufferBetweenImages = 16;
    int bufferVerticalBetweenText = 48;
    int imageOffset = -24;

    // Vars used for thruster power-up
    int thrusterPowerupsUpperLeftCornerPositionX = 64;
    int thrusterPowerupsUpperLeftCornerPositionY = 192; // Was 208
    int thrusterImageWidth = ImageManager.getImage(Constants.FILENAME_SPACESHIP_THRUST1).getWidth(imageObserver);

    // Vars used in health and shield power-ups
    int healthShieldPowerupsUpperLeftCornerPositionX = 336;
    int healthShieldPowerupsUpperLeftCornerPositionY = 192; // Was 208

    // Vars used in health and shield power-ups
    int weaponryPowerupsUpperLeftCornerPositionX = 556;
    int weaponryPowerupsUpperLeftCornerPositionY = 192; // Was 208

    // Vars for the point bonuses
    int pointBonusesPositionY = 496;
    int pointBonusesPositionX;
    int powerupImageWidth = ImageManager.getImage(Constants.FILENAME_POWERUP_250).getWidth(imageObserver);
    int powerupImageHeight = ImageManager.getImage(Constants.FILENAME_POWERUP_250).getHeight(imageObserver);
    int pointBonusTextLength, pointBonusTextHeight;

    /*
     * Display the ship thruster power-ups
     */
    g.setFont(Constants.FONT_INTRO_POWERUPS_SCREEN_REGULAR);
    g.setColor(Color.WHITE);

    //g.drawString(Constants.MSG_POWERUPS_THRUSTERS, thrusterPowerupsUpperLeftCornerPositionX, (int)(thrusterPowerupsUpperLeftCornerPositionY - bounds.getHeight()/2));
    g.drawString(Constants.MSG_POWERUPS_THRUSTERS, thrusterPowerupsUpperLeftCornerPositionX, thrusterPowerupsUpperLeftCornerPositionY);
    thrusterPowerupsUpperLeftCornerPositionY += bufferVerticalBetweenText;

    g.setFont(Constants.FONT_INTRO_POWERUPS_SCREEN_SMALL);
    g.setColor(Color.ORANGE);

    g.drawImage(ImageManager.getImage(Constants.FILENAME_POWERUP_ENGINE_1), thrusterPowerupsUpperLeftCornerPositionX, thrusterPowerupsUpperLeftCornerPositionY + imageOffset, this.imageObserver);
    g.drawString(Constants.MSG_POWERUPS_THURST1, thrusterPowerupsUpperLeftCornerPositionX + thrusterImageWidth, thrusterPowerupsUpperLeftCornerPositionY);
    thrusterPowerupsUpperLeftCornerPositionY += bufferVerticalBetweenText;

    g.drawImage(ImageManager.getImage(Constants.FILENAME_POWERUP_ENGINE_2), thrusterPowerupsUpperLeftCornerPositionX, thrusterPowerupsUpperLeftCornerPositionY + imageOffset, this.imageObserver);
    g.drawString(Constants.MSG_POWERUPS_THURST2, thrusterPowerupsUpperLeftCornerPositionX + thrusterImageWidth, thrusterPowerupsUpperLeftCornerPositionY);
    thrusterPowerupsUpperLeftCornerPositionY += bufferVerticalBetweenText;

    g.drawImage(ImageManager.getImage(Constants.FILENAME_POWERUP_ENGINE_3), thrusterPowerupsUpperLeftCornerPositionX, thrusterPowerupsUpperLeftCornerPositionY + imageOffset, this.imageObserver);
    g.drawString(Constants.MSG_POWERUPS_THURST3, thrusterPowerupsUpperLeftCornerPositionX + thrusterImageWidth, thrusterPowerupsUpperLeftCornerPositionY);

    /*
     * Display health and shield power-ups
     */
    g.setFont(Constants.FONT_INTRO_POWERUPS_SCREEN_REGULAR);
    g.setColor(Color.WHITE);
    g.drawString(Constants.MSG_POWERUPS_HEALTH_SHIELD, healthShieldPowerupsUpperLeftCornerPositionX - 32, healthShieldPowerupsUpperLeftCornerPositionY);
    healthShieldPowerupsUpperLeftCornerPositionY += bufferVerticalBetweenText;
    
    g.setFont(Constants.FONT_INTRO_POWERUPS_SCREEN_SMALL);
    g.setColor(Color.ORANGE);
    g.drawImage(ImageManager.getImage(Constants.FILENAME_POWERUP_HEALTH), healthShieldPowerupsUpperLeftCornerPositionX, healthShieldPowerupsUpperLeftCornerPositionY + imageOffset, this.imageObserver);
    g.drawString(Constants.MSG_POWERUPS_HEALTH, healthShieldPowerupsUpperLeftCornerPositionX + thrusterImageWidth, healthShieldPowerupsUpperLeftCornerPositionY);
    healthShieldPowerupsUpperLeftCornerPositionY += bufferVerticalBetweenText;

    g.drawImage(ImageManager.getImage(Constants.FILENAME_POWERUP_SHIELD), healthShieldPowerupsUpperLeftCornerPositionX, healthShieldPowerupsUpperLeftCornerPositionY + imageOffset, this.imageObserver);
    g.drawString(Constants.MSG_POWERUPS_SHIELDS, healthShieldPowerupsUpperLeftCornerPositionX + thrusterImageWidth, healthShieldPowerupsUpperLeftCornerPositionY);
    healthShieldPowerupsUpperLeftCornerPositionY += bufferVerticalBetweenText;

    g.drawImage(ImageManager.getImage(Constants.FILENAME_POWERUP_FULL_HEALTH), healthShieldPowerupsUpperLeftCornerPositionX, healthShieldPowerupsUpperLeftCornerPositionY + imageOffset, this.imageObserver);
    g.drawString(Constants.MSG_POWERUPS_FULL_HEALTH, healthShieldPowerupsUpperLeftCornerPositionX + thrusterImageWidth, healthShieldPowerupsUpperLeftCornerPositionY);
    healthShieldPowerupsUpperLeftCornerPositionY += bufferVerticalBetweenText;

    g.drawImage(ImageManager.getImage(Constants.FILENAME_POWERUP_SHIELD_FULL_RESTORE), healthShieldPowerupsUpperLeftCornerPositionX, healthShieldPowerupsUpperLeftCornerPositionY + imageOffset, this.imageObserver);
    g.drawString(Constants.MSG_POWERUPS_FULL_SHIELD, healthShieldPowerupsUpperLeftCornerPositionX + thrusterImageWidth, healthShieldPowerupsUpperLeftCornerPositionY);
    healthShieldPowerupsUpperLeftCornerPositionY += bufferVerticalBetweenText;

    g.drawImage(ImageManager.getImage(Constants.FILENAME_POWERUP_SUPER_SHIELD), healthShieldPowerupsUpperLeftCornerPositionX, healthShieldPowerupsUpperLeftCornerPositionY + imageOffset, this.imageObserver);
    g.drawString(Constants.MSG_POWERUPS_SUPER_SHIELD, healthShieldPowerupsUpperLeftCornerPositionX + thrusterImageWidth, healthShieldPowerupsUpperLeftCornerPositionY);
    
    /*
     * Display weapons
     */
    g.setFont(Constants.FONT_INTRO_POWERUPS_SCREEN_REGULAR);
    g.setColor(Color.WHITE);
    g.drawString(Constants.MSG_POWERUPS_WEAPONS, weaponryPowerupsUpperLeftCornerPositionX + 32, weaponryPowerupsUpperLeftCornerPositionY);
    weaponryPowerupsUpperLeftCornerPositionY += bufferVerticalBetweenText;
    
    g.setFont(Constants.FONT_INTRO_POWERUPS_SCREEN_SMALL);
    g.setColor(Color.ORANGE);
    g.drawImage(ImageManager.getImage(Constants.FILENAME_POWERUP_GUN), weaponryPowerupsUpperLeftCornerPositionX, weaponryPowerupsUpperLeftCornerPositionY + imageOffset, this.imageObserver);
    g.drawString(Constants.MSG_POWERUPS_FIREPOWER, weaponryPowerupsUpperLeftCornerPositionX + thrusterImageWidth, weaponryPowerupsUpperLeftCornerPositionY);
    weaponryPowerupsUpperLeftCornerPositionY += bufferVerticalBetweenText;

    g.drawImage(ImageManager.getImage(Constants.FILENAME_POWERUP_THE_BOMB), weaponryPowerupsUpperLeftCornerPositionX, weaponryPowerupsUpperLeftCornerPositionY + imageOffset, this.imageObserver);
    g.drawString(Constants.MSG_POWERUPS_THE_BOMB, weaponryPowerupsUpperLeftCornerPositionX + thrusterImageWidth, weaponryPowerupsUpperLeftCornerPositionY);

    /*
     * Display point bonus power-ups at the bottom of the screen        
     */
    g.setFont(Constants.FONT_INTRO_POWERUPS_SCREEN_MEDIUM);
    Rectangle2D bounds = g.getFontMetrics().getStringBounds(Constants.MSG_POWERUPS_SCREEN_POINT_BONUSES, g);
    pointBonusTextLength = (int)bounds.getWidth();
    pointBonusTextHeight = (int)bounds.getHeight();

    // Compute the starting x position of the text 
    pointBonusesPositionX = (screenWidth - pointBonusTextLength - 3*regularBufferBetweenImages - 3*powerupImageWidth)/2;
    g.drawString(Constants.MSG_POWERUPS_SCREEN_POINT_BONUSES, pointBonusesPositionX, (int)(pointBonusesPositionY + pointBonusTextHeight/4));

    // Draw point bonus icons after the point bonus text
    pointBonusesPositionX += pointBonusTextLength + regularBufferBetweenImages;  // Compute the x position of the first power image
    g.drawImage(ImageManager.getImage(Constants.FILENAME_POWERUP_250), pointBonusesPositionX, pointBonusesPositionY-powerupImageHeight/2, this.imageObserver);

    pointBonusesPositionX += powerupImageWidth + regularBufferBetweenImages;     // Compute the x position of the second power image
    g.drawImage(ImageManager.getImage(Constants.FILENAME_POWERUP_500), pointBonusesPositionX, pointBonusesPositionY-powerupImageHeight/2, this.imageObserver);

    pointBonusesPositionX += powerupImageWidth + regularBufferBetweenImages;     // Compute the x position of the third power image
    g.drawImage(ImageManager.getImage(Constants.FILENAME_POWERUP_1000), pointBonusesPositionX, pointBonusesPositionY-powerupImageHeight/2, this.imageObserver);
  }

  /**
   * Displays the details of specific power-ups to better inform the player.
   *  
   * @param g - The graphics object passed from the draw method.
   */
  public void displayPowerupDetailsScreen(Graphics2D g)
  {
    msgPowerupDetailsScreen.draw(g);
    
    int upgradeMsgUpperLeftPositionX = 112;
    int upgradeMsgUpperLeftPositionY = 320;

    int spacingBetweenLines = 16;
    int spacingBetweenSections = 40;

    int usageUpperLeftCornerX = 320;
    int usageUpperLeftCornerY = 320;
    
    // Display some more details about player controls and power-ups
    int imageStartingPositionX = 64;  // Was 32
    int imageStartingPositionY = 300;



    g.drawImage(ImageManager.getImage(Constants.FILENAME_POWERUP_GUN), imageStartingPositionX, imageStartingPositionY, this.imageObserver);
    imageStartingPositionY += 40 + 16;

    g.drawImage(ImageManager.getImage(Constants.FILENAME_POWERUP_SUPER_SHIELD), imageStartingPositionX, imageStartingPositionY, this.imageObserver);
    imageStartingPositionY += 40;
 
    g.drawImage(ImageManager.getImage(Constants.FILENAME_POWERUP_THE_BOMB), imageStartingPositionX, imageStartingPositionY, this.imageObserver);
    imageStartingPositionY += 40;
    
    g.drawImage(ImageManager.getImage(Constants.FILENAME_POWERUP_ENGINE_1), imageStartingPositionX, imageStartingPositionY, this.imageObserver);
    imageStartingPositionY += 40;
    
    g.setColor(Color.ORANGE);
    g.setFont(Constants.FONT_INTRO_INSTRUCTIONS_SCREEN_SMALL);

    g.drawString(Constants.MSG_POWERUPS_FIREPOWER, upgradeMsgUpperLeftPositionX, upgradeMsgUpperLeftPositionY);
    upgradeMsgUpperLeftPositionY += spacingBetweenSections + spacingBetweenLines;
  
    g.drawString(Constants.MSG_POWERUPS_SUPER_SHIELD, upgradeMsgUpperLeftPositionX, upgradeMsgUpperLeftPositionY);
    upgradeMsgUpperLeftPositionY += spacingBetweenSections;
  
    g.drawString(Constants.MSG_POWERUPS_THE_BOMB, upgradeMsgUpperLeftPositionX, upgradeMsgUpperLeftPositionY);
    upgradeMsgUpperLeftPositionY += spacingBetweenSections;
    
    g.drawString(Constants.MSG_POWERUPS_THURST1, upgradeMsgUpperLeftPositionX, upgradeMsgUpperLeftPositionY);
    upgradeMsgUpperLeftPositionY += spacingBetweenSections;

    
    
    g.drawString(Constants.MSG_POWERUP_DETAILS_FIREPOWER_UPGRADE_USE, usageUpperLeftCornerX, usageUpperLeftCornerY);
    usageUpperLeftCornerY += spacingBetweenLines;
  
    g.drawString(Constants.MSG_POWERUP_DETAILS_MAX_FIREPOWER, usageUpperLeftCornerX, usageUpperLeftCornerY);
    usageUpperLeftCornerY += spacingBetweenSections;
    
    g.drawString(Constants.MSG_POWERUP_DETAILS_SUPER_SHIELDS_USE, usageUpperLeftCornerX, usageUpperLeftCornerY);
    usageUpperLeftCornerY += spacingBetweenSections;
  
    g.drawString(Constants.MSG_POWERUP_DETAILS_THE_BOMB_USE, usageUpperLeftCornerX, usageUpperLeftCornerY);
    usageUpperLeftCornerY += spacingBetweenSections;
    
    g.drawString(Constants.MSG_POWERUP_DETAILS_ION_THRUSTER, usageUpperLeftCornerX, usageUpperLeftCornerY);
    usageUpperLeftCornerY += spacingBetweenLines;
    
    g.drawString(Constants.MSG_POWERUP_DETAILS_THRUSTERS, usageUpperLeftCornerX, usageUpperLeftCornerY);
    usageUpperLeftCornerY += spacingBetweenSections;
  }
  
  private void displayCreditsScreen(Graphics2D g)
  {
    msgCreditsTitle.draw(g);
  }

  // NOTE: Possibly place all methods responsible for drawing the screen content into some type of screen/display manager
  /**
   * Initialize the images used in the enemies screen from the introduction. Since all of this can be computed
   * before hand, it is computed here once and the content is used to create instances of the static image class
   * one for each asteroid type.
   */
  private void setupIntroductionEnemiesScreen()
  {
    int startingPositionYOfAsteroidListing = 208;

    int bufferBetweenAsteroid = 64;
    bigAsteroidWidth = ImageManager.getWidth(Constants.FILENAME_BIG_ASTEROID_1);  // Non local variable since it is needed in other methods for the enemies screen in the introduction
    int mediumAsteroidWidth = ImageManager.getWidth(Constants.FILENAME_MEDIUM_ASTEROID_1);
    int smallAsteroidWidth = ImageManager.getWidth(Constants.FILENAME_SMALL_ASTEROID_1);
    int tinyAsteroidWidth = ImageManager.getWidth(Constants.FILENAME_TINY_ASTEROID_1);

    int bigAsteroidHeight = ImageManager.getHeight(Constants.FILENAME_BIG_ASTEROID_1);
    int mediumAsteroidHeight = ImageManager.getHeight(Constants.FILENAME_MEDIUM_ASTEROID_1);
    int smallAsteroidHeight = ImageManager.getHeight(Constants.FILENAME_SMALL_ASTEROID_1);
    int tinyAsteroidHeight = ImageManager.getHeight(Constants.FILENAME_TINY_ASTEROID_1);

    // (Number of Asteroid Types) x (Width of Largest Asteroids) + ((Number of Asteroid Types) - 1) x (Buffer Between Each Asteroid)
    int widthOfAsteroidListing = 4 * bigAsteroidWidth + (3) * bufferBetweenAsteroid;
    startingPositionXOfAsteroidListing = (screenWidth - widthOfAsteroidListing)/2;

    int largeAsteroidStartingPositionX = startingPositionXOfAsteroidListing;
    int mediumAsteroidStartingPositionX = startingPositionXOfAsteroidListing + (bigAsteroidWidth + bufferBetweenAsteroid) + (bigAsteroidWidth - mediumAsteroidWidth)/2;
    int smallAsteroidStartingPositionX = startingPositionXOfAsteroidListing + 2*(bigAsteroidWidth + bufferBetweenAsteroid) + (bigAsteroidWidth - smallAsteroidWidth)/2;
    int tinyAsteroidStartingPositionX = startingPositionXOfAsteroidListing + 3*(bigAsteroidWidth + bufferBetweenAsteroid) + (bigAsteroidWidth - tinyAsteroidWidth)/2;

    int largeAsteroidStartingPositionY = startingPositionYOfAsteroidListing;
    int mediumAsteroidStartingPositionY = startingPositionYOfAsteroidListing + (bigAsteroidHeight - mediumAsteroidHeight)/2;
    int smallAsteroidStartingPositionY = startingPositionYOfAsteroidListing + (bigAsteroidHeight - smallAsteroidHeight)/2;
    int tinyAsteroidStartingPositionY = startingPositionYOfAsteroidListing + (bigAsteroidHeight - tinyAsteroidHeight)/2;
    
    imgAsteroidLargeIntroductionScreen = new StaticImage(ImageManager.getImage(Constants.FILENAME_BIG_ASTEROID_1), largeAsteroidStartingPositionX, largeAsteroidStartingPositionY, false, imageObserver);
    imgAsteroidMediumIntroductionScreen = new StaticImage(ImageManager.getImage(Constants.FILENAME_MEDIUM_ASTEROID_1), mediumAsteroidStartingPositionX, mediumAsteroidStartingPositionY, false, imageObserver);
    imgAsteroidSmallIntroductionScreen = new StaticImage(ImageManager.getImage(Constants.FILENAME_SMALL_ASTEROID_1), smallAsteroidStartingPositionX, smallAsteroidStartingPositionY, false, imageObserver);
    imgAsteroidTinyIntroductionScreen = new StaticImage(ImageManager.getImage(Constants.FILENAME_TINY_ASTEROID_1), tinyAsteroidStartingPositionX, tinyAsteroidStartingPositionY, false, imageObserver);
  }

  private void displayEnemiesScreen(Graphics2D g)
  {
    // Draw screen titles
    msgEnemiesTitle.draw(g);
    msgEnemiesAsteroidLabel.draw(g);

    // Draw asteroid images
    imgAsteroidLargeIntroductionScreen.draw(g);
    imgAsteroidMediumIntroductionScreen.draw(g);
    imgAsteroidSmallIntroductionScreen.draw(g);
    imgAsteroidTinyIntroductionScreen.draw(g);

    // Draw the labels below each asteroid, both point value and 
    
    g.setColor(Color.WHITE);

    // NOTE: These variables can be computed once and moved outside of this method after testing to reduce unnecessary computation
    Rectangle2D bounds;

    // Display the respective asteroid labels
    g.setFont(Constants.FONT_INTRO_ENEMIES_SCREEN_SMALL);
    bounds = g.getFontMetrics().getStringBounds(Constants.MSG_ENEMIES_ASTEROID_LARGE_LABEL, g);
    int largeAsteroidTextPosition = startingPositionXOfAsteroidListing + (bigAsteroidWidth - (int)bounds.getWidth())/2;
    g.drawString(Constants.MSG_ENEMIES_ASTEROID_LARGE_LABEL, largeAsteroidTextPosition, 352);

    bounds = g.getFontMetrics().getStringBounds(Constants.MSG_ENEMIES_ASTEROID_MEDIUM_LABEL, g);
    int mediumAsteroidTextPosition = startingPositionXOfAsteroidListing + (bigAsteroidWidth + bufferBetweenAsteroid) + (bigAsteroidWidth - (int)bounds.getWidth())/2;
    g.drawString(Constants.MSG_ENEMIES_ASTEROID_MEDIUM_LABEL, mediumAsteroidTextPosition, 352);

    bounds = g.getFontMetrics().getStringBounds(Constants.MSG_ENEMIES_ASTEROID_SMALL_LABEL, g);
    int smallAsteroidTextPosition = startingPositionXOfAsteroidListing + 2*(bigAsteroidWidth + bufferBetweenAsteroid) + (bigAsteroidWidth - (int)bounds.getWidth())/2;
    g.drawString(Constants.MSG_ENEMIES_ASTEROID_SMALL_LABEL, smallAsteroidTextPosition, 352);

    bounds = g.getFontMetrics().getStringBounds(Constants.MSG_ENEMIES_ASTEROID_TINY_LABEL, g);
    int tinyAsteroidTextPosition = startingPositionXOfAsteroidListing + 3*(bigAsteroidWidth + bufferBetweenAsteroid) + (bigAsteroidWidth - (int)bounds.getWidth())/2;
    g.drawString(Constants.MSG_ENEMIES_ASTEROID_TINY_LABEL, tinyAsteroidTextPosition, 352);

    // Display the respective asteroid point values
    g.setFont(Constants.FONT_INTRO_ENEMIES_SCREEN_TINY);
    bounds = g.getFontMetrics().getStringBounds("(" + Constants.SCORE_BIG_ASTEROIDS + Constants.MSG_ENEMIES_POINTS + ")", g);
    int largeAsteroidTextScorePosition = startingPositionXOfAsteroidListing + (bigAsteroidWidth - (int)bounds.getWidth())/2;
    g.drawString("(" + Constants.SCORE_BIG_ASTEROIDS + Constants.MSG_ENEMIES_POINTS + ")", largeAsteroidTextScorePosition, 368);

    bounds = g.getFontMetrics().getStringBounds("(" + Constants.SCORE_MEDIUM_ASTEROIDS + Constants.MSG_ENEMIES_POINTS + ")", g);
    int mediumAsteroidTextScorePosition = startingPositionXOfAsteroidListing + (bigAsteroidWidth + bufferBetweenAsteroid) + (bigAsteroidWidth - (int)bounds.getWidth())/2;
    g.drawString("(" + Constants.SCORE_MEDIUM_ASTEROIDS + Constants.MSG_ENEMIES_POINTS + ")", mediumAsteroidTextScorePosition, 368);

    bounds = g.getFontMetrics().getStringBounds("(" + Constants.SCORE_SMALL_ASTEROIDS + Constants.MSG_ENEMIES_POINTS + ")", g);
    int smallAsteroidTextScorePosition = startingPositionXOfAsteroidListing + 2*(bigAsteroidWidth + bufferBetweenAsteroid) + (bigAsteroidWidth - (int)bounds.getWidth())/2;
    g.drawString("(" + Constants.SCORE_SMALL_ASTEROIDS + Constants.MSG_ENEMIES_POINTS + ")", smallAsteroidTextScorePosition, 368);

    bounds = g.getFontMetrics().getStringBounds("(" + Constants.SCORE_TINY_ASTEROIDS + Constants.MSG_ENEMIES_POINTS + ")", g);
    int tinyAsteroidTextScorePosition = startingPositionXOfAsteroidListing + 3*(bigAsteroidWidth + bufferBetweenAsteroid) + (bigAsteroidWidth - (int)bounds.getWidth())/2;
    g.drawString("(" + Constants.SCORE_TINY_ASTEROIDS + Constants.MSG_ENEMIES_POINTS + ")", tinyAsteroidTextScorePosition, 368);
  }

  private void displayIntroductionFooter(Graphics2D g)
  {
    // NOTE: These variables can be computed once and moved outside of this method after testing to reduce unnecessary computation
    int backMsgPositionX = 32;
    int backMsgPositionY = 576;
    int nextMsgPositionX = 592;
    int nextMsgPositionY = 576;
    
    g.setFont(Constants.FONT_INTRO_SCREEN_SMALL);
    g.setColor(Color.WHITE);
    g.drawString(Constants.MSG_INTRODUCTION_MSG_NEXT, nextMsgPositionX, nextMsgPositionY);
    g.drawString(Constants.MSG_INTRODUCTION_MSG_BACK, backMsgPositionX, backMsgPositionY);
  }

  /**
   * 
   * 
   * @param g
   */
  private void displayHUD(Graphics2D g)
  {
    // Draw player health bar
    String barFrameImageName = "";
    switch(((PlayerEntity) getPlayer()).getLimit(Constants.AttributeType.ATTRIBUTE_HEALTH))
    {
      case Constants.SHIP_HEALTH_CAPACITY_INCREASE_TO_20:
        barFrameImageName = Constants.FILENAME_BAR_FRAME_20;
        break;
      case Constants.SHIP_HEALTH_CAPACITY_INCREASE_TO_40:
        barFrameImageName = Constants.FILENAME_BAR_FRAME_40;
        break;
      default:
        barFrameImageName = Constants.FILENAME_BAR_FRAME_10;
    }
    g.drawImage(ImageManager.getImage(barFrameImageName), screenWidth - ImageManager.getWidth(Constants.FILENAME_BAR_FRAME_40) - 20, 18, this.imageObserver);
    
    // Draw player shield bar
    switch(((PlayerEntity) getPlayer()).getLimit(Constants.AttributeType.ATTRIBUTE_SHIELD))
    {
      case Constants.SHIP_SHIELD_CAPACITY_INCREASE_TO_20:
        barFrameImageName = Constants.FILENAME_BAR_FRAME_20;
        break;
      case Constants.SHIP_SHIELD_CAPACITY_INCREASE_TO_40:
        barFrameImageName = Constants.FILENAME_BAR_FRAME_40;
        break;
      default:
        barFrameImageName = Constants.FILENAME_BAR_FRAME_10;
    }
    g.drawImage(ImageManager.getImage(barFrameImageName), screenWidth - ImageManager.getWidth(Constants.FILENAME_BAR_FRAME_40) - 20, 33, this.imageObserver);

    // Draw the health level
    for (int n = 0; n < ((PlayerEntity) getPlayer()).getValue(Constants.AttributeType.ATTRIBUTE_HEALTH); n++)
    {
      int dx = (screenWidth - ImageManager.getWidth(Constants.FILENAME_BAR_FRAME_40) - 18) + n * 5;
      g.drawImage(ImageManager.getImage(Constants.FILENAME_BAR_HEALTH), dx, 20, this.imageObserver);
    }

    // Draw the shield level
    for (int n = 0; n < ((PlayerEntity) getPlayer()).getValue(Constants.AttributeType.ATTRIBUTE_SHIELD); n++)
    {
      int dx = (screenWidth - ImageManager.getWidth(Constants.FILENAME_BAR_FRAME_40) - 18) + n * 5;
      g.drawImage(ImageManager.getImage(Constants.FILENAME_BAR_SHIELD), dx, 35, this.imageObserver);
    }

    msgHUDHealthBar.draw(g);  // Draw the label for the health bar    
    msgHUDShieldBar.draw(g);  // Draw the label for the shield bar
    msgHUDFirepower.draw(g);  // Draw label next to bullet upgrades

    // Draw the current level
    msgHUDCurrentLevel.setText(Constants.MSG_GAME_PLAYING_WAVE + GameUtility.lPadZero(currentLevel, 2));
    msgHUDCurrentLevel.draw(g);

    // Draw the score
    msgHUDScore.setText(Constants.MSG_GAME_PLAYING_SCORE + GameUtility.lPadZero(ScoreManager.getScore(), 6));
    msgHUDScore.draw(g);

    // Draw the amounts for Super Shields and for The Bomb 
    msgHUDNumberOfSuperShields.setText(((PlayerEntity) getPlayer()).getValue(Constants.AttributeType.ATTRIBUTE_SUPER_SHIELD) + "");
    msgHUDNumberOfTheBombs.setText(((PlayerEntity)getPlayer()).getValue(Constants.AttributeType.ATTRIBUTE_THE_BOMB) + "");
    msgHUDNumberOfSuperShields.draw(g);
    msgHUDNumberOfTheBombs.draw(g);

    // Draw the bullet upgrades
    String playerFirepowerImageName = "";
    switch (((PlayerEntity) getPlayer()).getValue(AttributeType.ATTRIBUTE_FIREPOWER))
    {
      case 2:
        playerFirepowerImageName = Constants.FILENAME_POWERUP_GUN_2;
        break;

      case 3:
        playerFirepowerImageName = Constants.FILENAME_POWERUP_GUN_3;
        break;

      case 4:
        playerFirepowerImageName = Constants.FILENAME_POWERUP_GUN_4;
        break;

      case 5:
        playerFirepowerImageName = Constants.FILENAME_POWERUP_GUN_5;
        break;

      default:
        playerFirepowerImageName = Constants.FILENAME_POWERUP_GUN;
    }
    g.drawImage(ImageManager.getImage(playerFirepowerImageName), 130, 17, this.imageObserver);

    // Draw the super shield icon
    g.drawImage(ImageManager.getImage(Constants.FILENAME_HUD_SUPERSHIELD_ICON), 20, 85, this.imageObserver);
    
    // Draw the bomb icon
    g.drawImage(ImageManager.getImage(Constants.FILENAME_HUD_THE_BOMB_ICON), 20, 115, this.imageObserver);
    
    // Draw the auto shield icon along with amount
    //g.drawImage(powerupAutoShield.getImage(), 20, 145, this.imageObserver);
    
    // Draw the respective thrust icon given the players current thrust level
    switch(((PlayerEntity) getPlayer()).getValue(AttributeType.ATTRIBUTE_THRUST))
    {
      case 2:
        g.drawImage(ImageManager.getImage(Constants.FILENAME_POWERUP_ENGINE_2), 20, 185, this.imageObserver);
        break;
        
      case 3:
        g.drawImage(ImageManager.getImage(Constants.FILENAME_POWERUP_ENGINE_3), 20, 185, this.imageObserver);
        break;
        
      default:
    }

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
  }

  private void displayGameStartScreen(Graphics2D g)
  {
    msgGameStartScreen.draw(g);
  }

  private void displayNextLevelScreen(Graphics2D g)
  {
    msgNextLevelScreen.setText(Constants.MSG_NEXT_LEVEL + GameUtility.lPadZero((currentLevel + 1), 2));
    msgNextLevelScreen.draw(g);
  }

  private void displayPlayerDeadScreen(Graphics2D g)
  {
    msgPlayerDeadScreen.draw(g);
  }

  private void displayGameOverScreen(Graphics2D g)
  {
    msgGameOverScreen.draw(g);
  }

  // TODO: Add a method to display the instructions screen
  
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
      g.drawString(Constants.DEBUG_MSG_PLAYER_HEALTH_CAPACITY + ((PlayerEntity) getPlayer()).getLimit(Constants.AttributeType.ATTRIBUTE_HEALTH) , 560, line);
      line += 16;
      g.drawString(Constants.DEBUG_MSG_PLAYER_SHIELD_CAPACITY + ((PlayerEntity) getPlayer()).getLimit(Constants.AttributeType.ATTRIBUTE_SHIELD), 560, line);
      line += 16;
    }
  }
  
//  private void menuFlowOneForIntroductionScreen(int keyCode)
//  {
//    switch(currentIntroductionScreen)
//    {
//      // From the power-ups screen, the player can view the details screen or return to the previous screen, which is the instructions screen.
//      case POWERUPS:
//
//        if (keyCode == KeyEvent.VK_ESCAPE)
//        {
//          currentIntroductionScreen = Constants.IntroductionScreens.INSTRUCTIONS;
//        }
//
//        if (keyCode == KeyEvent.VK_D)
//        {
//          currentIntroductionScreen = Constants.IntroductionScreens.POWERUP_DETAILS;
//        }
//        break;
//      
//      // From the power-ups detail screen, the player can return to the power-ups screen.
//      case POWERUP_DETAILS:
//
//        if (keyCode == KeyEvent.VK_ESCAPE)
//        {
//          currentIntroductionScreen = Constants.IntroductionScreens.POWERUPS;
//        }
//        break;
//        
//      // From the instructions screen, the player can view the power-ups screen, the enemies screen or return to the main introduction screen.
//      case INSTRUCTIONS:
//
//        if (keyCode == KeyEvent.VK_P)
//        {
//          currentIntroductionScreen = Constants.IntroductionScreens.POWERUPS;
//        }
//
//        if (keyCode == KeyEvent.VK_E)
//        {
//          currentIntroductionScreen = Constants.IntroductionScreens.ENEMIES;
//        }
//
//        if (keyCode == KeyEvent.VK_ESCAPE)
//        {
//          currentIntroductionScreen = Constants.IntroductionScreens.MAIN;
//        }
//        break;
//        
//      // From the main introduction screen, the player can view the credits or view the instruction screens.
//      case MAIN:
//        
//        if (keyCode == KeyEvent.VK_I)
//        {
//          currentIntroductionScreen = Constants.IntroductionScreens.INSTRUCTIONS;
//        }
//        
//        if (keyCode == KeyEvent.VK_C)
//        {
//          currentIntroductionScreen = Constants.IntroductionScreens.CREDITS;
//        }
//
//        if (keyCode == KeyEvent.VK_ESCAPE)
//        {
//          // TODO: Add request here to exit game.
//        }
//
//      case CREDITS:
//        
//        if (keyCode == KeyEvent.VK_ESCAPE)
//        {
//          currentIntroductionScreen = Constants.IntroductionScreens.MAIN;
//        }
//        break;
//
//      case ENEMIES:
//        if (keyCode == KeyEvent.VK_ESCAPE)
//        {
//          currentIntroductionScreen = Constants.IntroductionScreens.INSTRUCTIONS;
//        }
//        break;
//        
//      default:
//    }
//  }
  
  private void menuFlowTwoForIntroductionScreen(int keyCode)
  {
    switch(currentIntroductionScreen)
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
    
    System.out.println("Current Introduction Screen:" + currentIntroductionScreen.toString());
  }
}
